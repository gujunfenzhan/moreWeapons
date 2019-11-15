package ink.mhxk.sword.init;

import ink.mhxk.sword.ModBigSwordMain;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public interface ModArmorMaterialLoader {
    ItemArmor.ArmorMaterial SLIME = EnumHelper.addArmorMaterial("slime", ModBigSwordMain.MODID+":"+"slime",70,new int[]{2,4,6,2},10,SoundEvents.BLOCK_SLIME_HIT,1);
}
