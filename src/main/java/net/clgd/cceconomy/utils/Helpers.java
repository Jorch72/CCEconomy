package net.clgd.cceconomy.utils;

import com.google.common.base.CaseFormat;
import net.clgd.cceconomy.CCEconomy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Helpers {
	/**
	 * @author SquidDev
	 */
	@SuppressWarnings("deprecation")
	public static boolean canTranslate(String key) {
		return net.minecraft.util.text.translation.I18n.canTranslate(key);
	}
	
	/**
	 * @author SquidDev
	 */
	@SuppressWarnings("deprecation")
	public static String translateAny(String... strings) {
		return translateOrDefault(strings[strings.length - 1], strings);
	}
	
	/**
	 * @author SquidDev
	 */
	@SuppressWarnings("deprecation")
	public static String translateOrDefault(String def, String... strings) {
		for (String string : strings) {
			if (net.minecraft.util.text.translation.I18n.canTranslate(string)) {
				return net.minecraft.util.text.translation.I18n.translateToLocal(string);
			}
		}
		
		return def;
	}
	
	/**
	 * @author SquidDev
	 */
	@SuppressWarnings("deprecation")
	public static String translateToLocal(String key) {
		return net.minecraft.util.text.translation.I18n.translateToLocal(key);
	}
	
	/**
	 * @author SquidDev
	 */
	@SuppressWarnings("deprecation")
	public static String translateToLocalFormatted(String format, Object... args) {
		return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(format, args);
	}
	
	/**
	 * @author SquidDev
	 */
	public static String snakeCase(String name) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
	}
	
	/**
	 * @author SquidDev
	 */
	@SideOnly(Side.CLIENT)
	public static void setupModel(Item item, int damage, String name) {
		name = CCEconomy.RESOURCE_DOMAIN + ":" + snakeCase(name);
		
		net.minecraft.client.renderer.block.model.ModelResourceLocation res = new ModelResourceLocation(name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, damage, res);
	}
}
