package net.clgd.cceconomy;

import lombok.Getter;
import net.clgd.cceconomy.registry.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static net.clgd.cceconomy.CCEconomy.*;

@Mod(modid = ID, name = NAME, version = VERSION,
	 dependencies = DEPENDENCIES,
	 acceptedMinecraftVersions = "[1.10.2]")
public class CCEconomy {
	public static final String ID 				= "cceconomy";
	public static final String NAME 			= "CCEconomy";
	public static final String VERSION 			= "${modVersion}";
	public static final String RESOURCE_DOMAIN 	= "cceconomy";
	public static final String DEPENDENCIES 	= "required-after:ComputerCraft@[${ccVersion},);";
	
	@Mod.Instance(ID) public static CCEconomy instance;
	public static Logger logger = LogManager.getLogger(NAME);
	
	public static SimpleNetworkWrapper network;
	
	@Getter private static CCEconomyCreativeTab creativeTab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(ID);
		
		creativeTab = new CCEconomyCreativeTab();
		
		Registry.setup();
		Registry.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Registry.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Registry.postInit();
	}
	
	private static class CCEconomyCreativeTab extends CreativeTabs {
		public CCEconomyCreativeTab() {
			super(RESOURCE_DOMAIN);
		}
		
		@Nonnull
		@Override
		public Item getTabIconItem() {
			return Registry.itemCheque;
		}
	}
}
