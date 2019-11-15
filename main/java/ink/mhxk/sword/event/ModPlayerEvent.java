package ink.mhxk.sword.event;

import ink.mhxk.sword.ModBigSwordMain;
import ink.mhxk.sword.client.render.RenderBigSword;
import ink.mhxk.sword.init.ModItemLoader;
import ink.mhxk.sword.utils.obj.WavefrontObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ModPlayerEvent {
    /*
    模型源地址:https://www.cgmodel.com/model-121184.html
     */
    public static WavefrontObject obj =  new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/rotate_shield.obj"));;
    public static ResourceLocation TEXTURE = new ResourceLocation(ModBigSwordMain.MODID, "textures/entity/rotate_shield.png");
    @SubscribeEvent
    public void onAttack(TickEvent.PlayerTickEvent event){
        EntityPlayer entityPlayer = event.player;
        InventoryPlayer inventory = entityPlayer.inventory;
        ItemStack boots = inventory.getStackInSlot(36);
        if(boots.getItem()== ModItemLoader.ARMOR_FEET){
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)&&entityPlayer.motionY<0.1){
                entityPlayer.motionY=0.3F;
            }
        }
        if(event.side==Side.SERVER) {
            /*
                39:头盔
                38:衣服
                37:裤衩
                36:靴子
             */

            if (entityPlayer.isServerWorld() && entityPlayer.isHandActive()) {
                ItemStack stack = entityPlayer.getActiveItemStack();
                if (stack.getItem() != Items.SHIELD) return;
                AxisAlignedBB AABB = entityPlayer.getEntityBoundingBox().grow(3.0F);
                List<Entity> entityList = entityPlayer.world.getEntitiesWithinAABB(Entity.class, AABB);
                for (Entity entityLiving : entityList) {
                    if (entityLiving instanceof EntityLiving || entityLiving instanceof EntityLivingBase) {
                        if (entityLiving instanceof EntityPlayer) {
                            if (entityLiving == entityPlayer) continue;
                        }
                        entityPlayer.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        entityLiving.attackEntityFrom(DamageSource.MAGIC, 4);
                        entityLiving.motionY = 0.4;
                        double x = (entityPlayer.posX - entityLiving.posX) / 3;
                        double z = (entityPlayer.posZ - entityLiving.posZ) / 3;
                        entityLiving.motionX = -x;
                        entityLiving.motionZ = -z;
                    } else {
                        entityLiving.setDead();
                    }
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
                ItemStack stack = entityPlayer.getActiveItemStack();
                if(stack.getItem()!= Items.SHIELD)return;
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
    /*
    预加载
    解决第一次渲染卡顿问题
     */
    @SideOnly(Side.CLIENT)
    public static void initTexture(){
            TextureManager manager = Minecraft.getMinecraft().getTextureManager();
            manager.loadTexture(TEXTURE,new SimpleTexture(TEXTURE));
            manager.loadTexture(RenderBigSword.TEXTURE,new SimpleTexture(RenderBigSword.TEXTURE));
    }
}
