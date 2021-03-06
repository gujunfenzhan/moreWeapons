package ink.mhxk.sword.client;
import ink.mhxk.sword.common.CommonProxy;
import ink.mhxk.sword.init.ModItemLoader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy
extends CommonProxy {
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event){
        registerItemModel(ModItemLoader.BIG_SWORD);
        //registerItemModel(ModItemLoader.GOLD_SHIELD);
        registerItemModel(ModItemLoader.ARMOR_HEAD);
        registerItemModel(ModItemLoader.ARMOR_CHEST);
        registerItemModel(ModItemLoader.ARMOR_LEGS);
        registerItemModel(ModItemLoader.ARMOR_FEET);

    }
    public void registerBlockModel(Block block){
        registerItemModel(Item.getItemFromBlock(block));
    }
    public void registerItemModel(Item item){

        ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
    }

}
