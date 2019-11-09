package ink.mhxk.sword.event;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPlayerEvent {
    @SubscribeEvent
    public void onAttack(PlayerInteractEvent event){
        System.out.println("23333");
    }
}
