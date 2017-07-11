package net.clgd.cceconomy.sponge;

import net.clgd.cceconomy.CCEconomy;
import net.clgd.cceconomy.network.MessageCurrency;
import net.clgd.cceconomy.registry.IModule;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.service.economy.EconomyService;

public class SpongeHandler implements IModule {
	private EconomyService economyService;
	
	@Override
	public void init() {
		Sponge.getEventManager().registerListeners(CCEconomy.instance, this);
		CCEconomy.logger.info("Registered Sponge event handler");
		
		Sponge.getServiceManager().getRegistration(EconomyService.class).ifPresent(p -> economyService = p.getProvider());
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
		if (event.getService().equals(EconomyService.class)) {
			economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		SimpleNetworkWrapper network = CCEconomy.network;
		EntityPlayerMP player = (EntityPlayerMP) event.player;
		
		CCEconomy.logger.info("Sending currencies to client {}", player.getDisplayNameString());
		
		economyService.getCurrencies().forEach(c -> network.sendTo(new MessageCurrency(c), player));
		network.sendTo(new MessageCurrency(economyService.getDefaultCurrency()), player);	// some plugins dont
																							// implement getCurrencies()
	}
}
