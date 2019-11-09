package ink.mhxk.sword.init;

import ink.mhxk.sword.item.ItemBigSword;
import net.minecraft.item.Item;

public interface ModItemLoader {
    Item BIG_SWORD = new ItemBigSword().setRegistryName("big_sword").setUnlocalizedName("bigSword");
}
