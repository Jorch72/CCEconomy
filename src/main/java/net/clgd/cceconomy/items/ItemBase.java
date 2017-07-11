package net.clgd.cceconomy.items;

import net.clgd.cceconomy.CCEconomy;
import net.clgd.cceconomy.registry.IClientModule;
import net.clgd.cceconomy.utils.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBase extends Item implements IClientModule {
	private final String name;
	
	public ItemBase(String itemName, int stackSize) {
		name = itemName;
		
		setUnlocalizedName(CCEconomy.RESOURCE_DOMAIN + "." + name);
		
		setCreativeTab(CCEconomy.getCreativeTab());
		setMaxStackSize(stackSize);
	}
	
	public ItemBase(String itemName) {
		this(itemName, 64);
	}
	
	public static NBTTagCompound getTag(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) stack.setTagCompound(tag = new NBTTagCompound());
		return tag;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		
		String descKey = getUnlocalizedName(stack) + ".desc";
		
		if (Helpers.canTranslate(descKey)) {
			tooltip.add(Helpers.translateToLocal(descKey));
		}
	}
	
	@Override
	public void preInit() {
		GameRegistry.register(this, new ResourceLocation(CCEconomy.RESOURCE_DOMAIN, name));
	}
	
	@Override
	public void clientPreInit() {
		Helpers.setupModel(this, 0, name);
	}
}
