package ink.mhxk.sword.init;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfigLoader {
    private static Configuration configuration;
    public static int fpSpeed;
    public ModConfigLoader(FMLPreInitializationEvent event){
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
        load();
    }
    public static void load(){
        fpSpeed = configuration.get(Configuration.CATEGORY_GENERAL,"fpSpeed",40, I18n.format("config.bigsword.fpSpeed")).getInt();
        configuration.save();
    }
}
