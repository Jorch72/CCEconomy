package net.clgd.cceconomy.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IClientModule extends IModule {
	@SideOnly(Side.CLIENT)
	default void clientInit() {}
	
	@SideOnly(Side.CLIENT)
	default void clientPreInit() {}
}
