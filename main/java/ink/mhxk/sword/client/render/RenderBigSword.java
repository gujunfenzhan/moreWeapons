package ink.mhxk.sword.client.render;

import ink.mhxk.sword.ModBigSwordMain;
import ink.mhxk.sword.utils.obj.WavefrontObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBigSword
extends TileEntityItemStackRenderer {
    /*
    模型源地址:https://www.cgmodel.com/model-123939.html
     */
    public static WavefrontObject obj = new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/big_sword.obj"));
    public static ResourceLocation TEXTURE = new ResourceLocation(ModBigSwordMain.MODID, "textures/entity/big_sword.png");
    public static WavefrontObject obj2 = new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/big_sword.obj"));
    public int state = 0;
    @Override
    public void renderByItem(ItemStack itemStackIn) {

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.translate(0.5, 2.6, 0.7);
        //第一个参数以史蒂夫为参考，相当于左右
        //第二个参数是前后
        //第三个参数是上下
        //GlStateManager.scale(0.01F, 0.01F, 0.01F);
        GlStateManager.scale(0.03F, 0.03F, 0.03F);
        //GlStateManager.rotate(0,0,angle,0);
        //GL11.glRotated(-20D, 0, 1, 0);
        //GL11.glRotated(100D, 1, 0, 0);
        //GL11.glRotated(100D, 0, 0, 1);
        // System.out.println(angle);
        //第一个，前后旋转
        //第二个，左右旋转
        //第三个中心旋转
        obj.renderAll();
        GlStateManager.popMatrix();

        //attack(itemStackIn);
        super.renderByItem(itemStackIn);
    }
    public void attack(ItemStack stack){
        NBTTagCompound nbt = stack.getTagCompound();

        if(nbt!=null){
            if(nbt.getBoolean("state")){
            }
            int pointX = 0;
            int pointY = 3;
            double d = Math.toRadians(state);
            double x = pointX*Math.cos(d)-pointY*Math.sin(d);
            double y = pointX*Math.sin(d)+pointY*Math.cos(d);
                state+= 1;
                GlStateManager.pushMatrix();
                Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
                GlStateManager.translate(x,0,y);
                GlStateManager.scale(0.03F, 0.03F, 0.03F);
                GL11.glRotated(-20, 1, 0, 0);
                //GL11.glRotated(state, 0, 0, 1);
                obj2.renderAll();
                GlStateManager.popMatrix();
            }
        }
    }

