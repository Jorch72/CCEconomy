package net.clgd.cceconomy.economy;

import net.clgd.cceconomy.CCEconomy;
import net.clgd.cceconomy.network.MessageCurrency;
import net.clgd.cceconomy.network.MessageCurrencyHandler;
import net.clgd.cceconomy.registry.IClientModule;
import net.clgd.cceconomy.registry.Packets;
import net.minecraftforge.fml.relauncher.Side;

public class EconomyModule implements IClientModule {
	@Override
	public void clientPreInit() {
		CCEconomy.network.registerMessage(
			MessageCurrencyHandler.class, MessageCurrency.class, Packets.CURRENCY, Side.CLIENT
		);
	}
}
