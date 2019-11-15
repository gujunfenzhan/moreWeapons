package ink.mhxk.sword.init;

import ink.mhxk.sword.item.ArmorSlime;
import ink.mhxk.sword.item.ItemBigSword;
import ink.mhxk.sword.item.ItemGoldShield;
import net.minecraft.item.Item;

public interface ModItemLoader {
    Item BIG_SWORD = new ItemBigSword().setRegistryName("big_sword").setUnlocalizedName("bigSword");
    Item GOLD_SHIELD = new ItemGoldShield().setRegistryName("gold_shield").setUnlocalizedName("goldShield");
    Item ARMOR_HEAD = new ArmorSlime.Head().setRegistryName("slime_helmet").setUnlocalizedName("slimeHelmet");
    Item ARMOR_CHEST = new ArmorSlime.Chest().setRegistryName("slime_chestplate").setUnlocalizedName("climeChestplate");
    Item ARMOR_LEGS = new ArmorSlime.Legs().setRegistryName("slime_leggings").setUnlocalizedName("slimeLeggings");
    Item ARMOR_FEET = new ArmorSlime.Feet().setRegistryName("slime_boots").setUnlocalizedName("slimeBoots");

}
