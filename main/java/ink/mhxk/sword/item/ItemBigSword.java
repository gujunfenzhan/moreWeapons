package ink.mhxk.sword.item;

import ink.mhxk.sword.client.render.RenderBigSword;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBigSword
extends ItemSword {
    public ItemBigSword() {
        super(ToolMaterial.DIAMOND);
        this.setTileEntityItemStackRenderer(new RenderBigSword());
        this.setCreativeTab(CreativeTabs.TOOLS);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt==null){
            nbt = new NBTTagCompound();
            nbt.setBoolean("state",true);
            stack.setTagCompound(nbt);
        }
        nbt.setBoolean("state",true);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
