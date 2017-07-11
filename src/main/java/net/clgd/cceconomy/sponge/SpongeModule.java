package net.clgd.cceconomy.sponge;

import net.clgd.cceconomy.registry.IModule;
import net.minecraftforge.fml.common.Loader;

public class SpongeModule implements IModule {
	public static SpongeHandler handler;
	
	@Override
	public void init() {
		if (!Loader.isModLoaded("sponge") && !Loader.isModLoaded("spongeapi")) {
			return;
		}
		
		handler = new SpongeHandler();
		handler.init();
	}
}
