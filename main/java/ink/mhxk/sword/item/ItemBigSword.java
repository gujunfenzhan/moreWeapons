package ink.mhxk.sword.item;

import ink.mhxk.sword.client.render.RenderBigSword;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemBigSword
extends ItemSword {
    public ItemBigSword() {
        super(ToolMaterial.DIAMOND);
        this.setTileEntityItemStackRenderer(new RenderBigSword());
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    /*
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
    }*/
}
