package ink.mhxk.sword.item;

import ink.mhxk.sword.init.ModArmorMaterialLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorSlime
extends ItemArmor {
    public ArmorSlime( int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(ModArmorMaterialLoader.SLIME, renderIndexIn, equipmentSlotIn);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }
    public static class Head
    extends ArmorSlime{
        public Head() {
            super(1, EntityEquipmentSlot.HEAD);
        }
    }
    public static class Chest
    extends ArmorSlime{
        public Chest() {
            super(1, EntityEquipmentSlot.CHEST);
        }
    }
    public static class Legs
    extends ArmorSlime{
        public Legs() {
            super(2, EntityEquipmentSlot.LEGS);
        }
    }
    public static class Feet
    extends ArmorSlime{
        public Feet() {
            super(1, EntityEquipmentSlot.FEET);
        }
    }
}
