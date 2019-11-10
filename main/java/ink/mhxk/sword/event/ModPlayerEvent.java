package ink.mhxk.sword.event;

import ink.mhxk.sword.ModBigSwordMain;
import ink.mhxk.sword.utils.obj.WavefrontObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ModPlayerEvent {
    private WavefrontObject obj = new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/rotate_shield.obj"));
    private ResourceLocation TEXTURE = new ResourceLocation(ModBigSwordMain.MODID, "textures/entity/rotate_shield.png");
    @SubscribeEvent
    public void onAttack(TickEvent.PlayerTickEvent event){
        EntityPlayer entityPlayer = event.player;
        if(entityPlayer.isServerWorld()&&entityPlayer.isHandActive()){
            AxisAlignedBB AABB = entityPlayer.getEntityBoundingBox().grow(3.0F);
            List<Entity> entityList = entityPlayer.world.getEntitiesWithinAABB(Entity.class,AABB);
            for (Entity entityLiving : entityList) {
                if(entityLiving instanceof EntityLiving||entityLiving instanceof EntityLivingBase) {
                    if(entityLiving instanceof EntityPlayer){
                        if(entityLiving==entityPlayer)continue;
                    }
                    entityLiving.attackEntityFrom(DamageSource.MAGIC, 4);
                    entityLiving.motionY = 0.4;
                    double x = (entityPlayer.posX - entityLiving.posX) / 3;
                    double z = (entityPlayer.posZ - entityLiving.posZ) / 3;
                    entityLiving.motionX = -x;
                    entityLiving.motionZ = -z;
                }else{
                    entityLiving.setDead();
                }
            }
        }
    }
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Post event){
        Entity entity = event.getEntity();
        if (entity.world!=null&&entity.world.isRemote&&entity instanceof EntityPlayer){
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            if(entityPlayer.isHandActive()){
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
                GlStateManager.scale(0.0025F, 0.0025F, 0.0025F);
                GL11.glTranslated(0,500,0);
                GL11.glRotated(System.currentTimeMillis()%60*6, 0, 1, 0);
                obj.renderAll();
                GlStateManager.popMatrix();
            }
            /*
            System.out.println("user:"+entityPlayer.isUser());
            System.out.println("swing:"+entityPlayer.isSwingInProgress);
            System.out.println("isHandActive:"+entityPlayer.isHandActive());
            */

        }
    }
}
