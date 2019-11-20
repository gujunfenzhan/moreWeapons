package ink.mhxk.sword;

import ink.mhxk.sword.common.CommonProxy;
import ink.mhxk.sword.event.ModPlayerEvent;
import ink.mhxk.sword.init.ModConfigLoader;
import ink.mhxk.sword.init.ModKeyLoader;
import ink.mhxk.sword.init.ModSentenceLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = ModBigSwordMain.MODNAME,modid = ModBigSwordMain.MODID,version = ModBigSwordMain.MODID)
public class ModBigSwordMain {
    @SidedProxy(serverSide = "ink.mhxk.sword.common.CommonProxy",clientSide = "ink.mhxk.sword.client.ClientProxy")
    public static CommonProxy proxy;
    public static final String MODNAME = "BigSword";
    public static final String MODID = "bigsword";
    public static final String VERSION = "1.0.0";
    @Mod.Instance
    public static ModBigSwordMain INSTANCE;
    @Mod.EventHandler
    public void pre(FMLPreInitializationEvent event){
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(proxy);
        MinecraftForge.EVENT_BUS.register(new ModPlayerEvent());
        new ModConfigLoader(event);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        new ModKeyLoader();
    }
    @Mod.EventHandler
    public void post(FMLPostInitializationEvent event){
        ModPlayerEvent.initTexture();
        new ModSentenceLoader();
    }
}
