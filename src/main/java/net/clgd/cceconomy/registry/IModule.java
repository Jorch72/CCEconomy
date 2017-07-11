package net.clgd.cceconomy.registry;

public interface IModule {
	default boolean canLoad() {
		return true;
	}
	
	default void preInit() {}
	default void init() {}
	default void postInit() {}
}
