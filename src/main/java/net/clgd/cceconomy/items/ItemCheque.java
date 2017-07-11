package net.clgd.cceconomy.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemCheque extends ItemBase {
	public ItemCheque() {
		super("cheque", 1);
		
		addPropertyOverrides();
	}
	
	private void addPropertyOverrides() {
		addPropertyOverride(new ResourceLocation("filled"), new ItemChequeFilledProperty());
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn,
													World worldIn,
													EntityPlayer playerIn,
													EnumHand hand) {
		NBTTagCompound nbt = getNBT(itemStackIn);
		
		if (nbt.getBoolean("filled")) return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
		
		nbt.setBoolean("filled", true);
		
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	private NBTTagCompound getNBT(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound();
		} else {
			NBTTagCompound nbt = new NBTTagCompound();
			
			nbt.setBoolean("filled", false);
			
			stack.setTagCompound(nbt);
			return nbt;
		}
	}
	
	private String getUnlocalizedSuffix(ItemStack stack) {
		return (getNBT(stack).getBoolean("filled") ? "filled" : "blank");
	}
	
	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName() + ".blank";
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + getUnlocalizedSuffix(stack);
	}
	
	public class ItemChequeFilledProperty implements IItemPropertyGetter {
		@Override
		@SideOnly(Side.CLIENT)
		public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
			return getNBT(stack).getBoolean("filled") ? 1f : 0f;
		}
	}
}
