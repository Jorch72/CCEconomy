package net.clgd.cceconomy.registry;

import net.clgd.cceconomy.economy.EconomyModule;
import net.clgd.cceconomy.items.ItemCheque;
import net.clgd.cceconomy.sponge.SpongeModule;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

public final class Registry {
	private static final Set<IModule> modules = new HashSet<>();
	
	private static boolean setup, preInit, init, postInit;
	
	public static ItemCheque itemCheque;
	
	public static SpongeModule spongeModule;
	public static EconomyModule economyModule;
	
	public static void setup() {
		if (setup) throw new IllegalStateException("Attempting to setup twice");
		setup = true;
		
		addModule(itemCheque = new ItemCheque());
		
		addModule(spongeModule = new SpongeModule());
		addModule(economyModule = new EconomyModule());
	}
	
	public static void preInit() {
		if (!setup) throw new IllegalStateException("Cannot preInit before setup");
		if (preInit) throw new IllegalStateException("Attempting to preInit twice");
		preInit = true;
		modules.stream().filter(IModule::canLoad).forEach(IModule::preInit);
	}
	
	public static void init()  {
		if (!preInit) throw new IllegalStateException("Cannot init before preInit");
		if (init) throw new IllegalStateException("Attempting to init twice");
		init = true;
		modules.stream().filter(IModule::canLoad).forEach(IModule::init);
	}
	
	public static void postInit() {
		if (!preInit) throw new IllegalStateException("Cannot init before preInit");
		if (!init) throw new IllegalStateException("Cannot postInit before init");
		if (postInit) throw new IllegalStateException("Attempting to postInit twice");
		postInit = true;
		modules.stream().filter(IModule::canLoad).forEach(IModule::postInit);
	}
	
	private static void addModule(IModule module) {
		if (module instanceof IClientModule) {
			module = new RegisterWrapperClient((IClientModule) module);
		}
		
		modules.add(module);
		
		if (preInit && module.canLoad()) {
			module.preInit();
			
			if (init) {
				module.init();
				
				if (postInit) module.postInit();
			}
		}
	}
	
	private static class RegisterWrapper implements IModule {
		protected final IClientModule base;
		
		private RegisterWrapper(IClientModule base) {
			this.base = base;
		}
		
		@Override
		public boolean canLoad() {
			return base.canLoad();
		}
		
		@Override
		public void preInit() {
			base.preInit();
		}
		
		@Override
		public void init() {
			base.init();
		}
		
		@Override
		public void postInit() {
			base.postInit();
		}
		
		@Override
		public String toString() {
			return base.toString();
		}
	}
	
	private static class RegisterWrapperClient extends RegisterWrapper {
		private RegisterWrapperClient(IClientModule base) {
			super(base);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void init() {
			super.init();
			base.clientInit();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void preInit() {
			super.preInit();
			base.clientPreInit();
		}
	}
}
