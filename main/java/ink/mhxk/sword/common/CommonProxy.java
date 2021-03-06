package ink.mhxk.sword.common;

import ink.mhxk.sword.init.ModItemLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy
{
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().register(ModItemLoader.BIG_SWORD);
        //event.getRegistry().register(ModItemLoader.GOLD_SHIELD);
        event.getRegistry().register(ModItemLoader.ARMOR_HEAD);
        event.getRegistry().register(ModItemLoader.ARMOR_CHEST);
        event.getRegistry().register(ModItemLoader.ARMOR_LEGS);
        event.getRegistry().register(ModItemLoader.ARMOR_FEET);
    }
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){

    }
    public void registerItemBlock(RegistryEvent.Register<Item> event,Block block){
        event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
}
