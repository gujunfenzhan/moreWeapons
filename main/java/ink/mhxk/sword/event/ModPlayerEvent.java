package ink.mhxk.sword.event;
import ink.mhxk.sword.ModBigSwordMain;
import ink.mhxk.sword.client.render.RenderBigSword;
import ink.mhxk.sword.init.ModItemLoader;
import ink.mhxk.sword.init.ModKeyLoader;
import ink.mhxk.sword.init.ModSentenceLoader;
import ink.mhxk.sword.utils.obj.WavefrontObject;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import java.util.List;
import java.util.Random;
public class ModPlayerEvent {
    /*
    模型源地址:https://www.cgmodel.com/model-121184.html
     */
    public static WavefrontObject obj =  new WavefrontObject(new ResourceLocation(ModBigSwordMain.MODID, "models/entity/rotate_shield.obj"));;
    public static ResourceLocation TEXTURE = new ResourceLocation(ModBigSwordMain.MODID, "textures/entity/rotate_shield.png");
    public static int chatTime = 0;
    public static String chatLore;
    public static Random rand = new Random();
    public static boolean FP = false;
    @SubscribeEvent
    public void onAttack(TickEvent.PlayerTickEvent event){

        EntityPlayer entityPlayer = event.player;

        InventoryPlayer inventory = entityPlayer.inventory;
        ItemStack boots = inventory.getStackInSlot(36);
        fly(entityPlayer);
        flash(entityPlayer);
        superRun(entityPlayer);
        longHand(entityPlayer);
        FP(entityPlayer);
        findOre(entityPlayer);
        if(chatTime>0&&System.currentTimeMillis()%(chatTime+1)==chatTime&&entityPlayer.world.isRemote){
            FMLClientHandler clientHandler = FMLClientHandler.instance();
            if(clientHandler!=null){
                EntityPlayerSP playerSP= clientHandler.getClientPlayerEntity();
                if(playerSP!=null)playerSP.sendChatMessage(chatLore);
            }
        }
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

                dBlock(entityPlayer);
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
    /*
    自动叫卖
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        String str = event.getMessage();
        if(str.substring(0,1).toCharArray()[0]=='#'&&str.length()>1){

            str = str.substring(1,str.length());
            String[] strs = str.split(" ");
            if(strs.length==1&&strs[0].equals("close")){
                chatTime = 0;
            }
            else if(strs.length==2){
                chatLore = strs[0];
                chatTime = Integer.parseInt(strs[1]);
            }
            event.setCanceled(true);
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
    public void dBlock(EntityPlayer player){
        World world = player.world;
        if(world==null)return;
        BlockPos pos = new BlockPos(player);
        world.destroyBlock(pos.add(-2,0,-1),true);
        world.destroyBlock(pos.add(-2,0,0),true);
        world.destroyBlock(pos.add(-2,0,1),true);
        world.destroyBlock(pos.add(-2,1,-1),true);
        world.destroyBlock(pos.add(-2,1,0),true);
        world.destroyBlock(pos.add(-2,1,1),true);

        world.destroyBlock(pos.add(2,0,-1),true);
        world.destroyBlock(pos.add(2,0,0),true);
        world.destroyBlock(pos.add(2,0,1),true);
        world.destroyBlock(pos.add(2,1,-1),true);
        world.destroyBlock(pos.add(2,1,0),true);
        world.destroyBlock(pos.add(2,1,1),true);

        world.destroyBlock(pos.add(-1,0,-2),true);
        world.destroyBlock(pos.add(0,0,-2),true);
        world.destroyBlock(pos.add(1,0,-2),true);
        world.destroyBlock(pos.add(-1,1,-2),true);
        world.destroyBlock(pos.add(0,1,-2),true);
        world.destroyBlock(pos.add(1,1,-2),true);

        world.destroyBlock(pos.add(-1,0,2),true);
        world.destroyBlock(pos.add(0,0,2),true);
        world.destroyBlock(pos.add(1,0,2),true);
        world.destroyBlock(pos.add(-1,1,2),true);
        world.destroyBlock(pos.add(0,1,2),true);
        world.destroyBlock(pos.add(1,1,2),true);

        world.destroyBlock(pos.add(-1,0,-1),true);
        world.destroyBlock(pos.add(-1,0,1),true);
        world.destroyBlock(pos.add(1,0,-1),true);
        world.destroyBlock(pos.add(1,1,1),true);
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
    /*
    恶搞+彩蛋
    大量乱码写入书与笔
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void BookOpen(GuiScreenEvent event){
        if(event.getGui() instanceof GuiScreenBook){
            GuiScreenBook bookGui = (GuiScreenBook)event.getGui();
            ItemStack stack = FMLClientHandler.instance().getClientPlayerEntity().getHeldItemMainhand();
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if(nbttagcompound==null){
                nbttagcompound = new NBTTagCompound();
                stack.setTagCompound(nbttagcompound);
            }

            NBTTagList bookPages = nbttagcompound.getTagList("pages", 8).copy();
            if (bookPages == null)
            {
                bookPages = new NBTTagList();
                bookPages.appendTag(new NBTTagString(""));
            }
            try {
                if(bookPages.tagCount()<1000)
                pageSetCurrent(getRandString(),bookPages,0);
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    sendBookToServer(false, bookPages, stack, Minecraft.getMinecraft());
                    FMLClientHandler.instance().getClientPlayerEntity().connection.sendPacket(new CPacketPlayerDigging( CPacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void sendBookToServer(boolean publish,NBTTagList bookPages,ItemStack book,Minecraft mc) throws IOException
    {
            if (bookPages != null)
            {
                while (bookPages.tagCount() > 1)
                {
                    String s = bookPages.getStringTagAt(bookPages.tagCount() - 1);

                    if (!s.isEmpty())
                    {
                        break;
                    }

                    bookPages.removeTag(bookPages.tagCount() - 1);
                }

                if (book.hasTagCompound())
                {
                    NBTTagCompound nbttagcompound = book.getTagCompound();
                    nbttagcompound.setTag("pages", bookPages);
                }
                else
                {
                    book.setTagInfo("pages", bookPages);
                }

                String s1 = "MC|BEdit";

                if (publish)
                {
                    s1 = "MC|BSign";
                    //book.setTagInfo("author", new NBTTagString(editingPlayer.getName()));
                    //book.setTagInfo("title", new NBTTagString(bookTitle.trim()));
                }

                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeItemStack(book);
                mc.getConnection().sendPacket(new CPacketCustomPayload(s1, packetbuffer));
            }
    }
    private void pageSetCurrent(String p_146457_1_,NBTTagList bookPages,int currPage)
    {
        //if (bookPages != null && currPage >= 0 && currPage < bookPages.tagCount())
       // {
        for(int i = 0;i<50;i++){
            //if(bookPages.get(i)!=null)
            //bookPages.removeTag(i);
            bookPages.appendTag(new NBTTagString(getRandString()));

        }
            //bookPages.set(currPage, new NBTTagString(p_146457_1_));

            //bookIsModified = true;
       // }
    }
    @SubscribeEvent
    public void project(ProjectileImpactEvent.Arrow event){
        System.out.println("???");
    }
    public String getRandString(){
        StringBuffer sb = new StringBuffer();
        byte b;
        for(int a= 0;a<200;a++){
            b = (byte) ((rand.nextBoolean()?0:1)|
                    ((rand.nextBoolean()?0:1)<<1)|
                    ((rand.nextBoolean()?0:1)<<2)|
                    ((rand.nextBoolean()?0:1)<<3)|
                    ((rand.nextBoolean()?0:1)<<4)|
                    ((rand.nextBoolean()?0:1)<<5)|
                    ((rand.nextBoolean()?0:1)<<6)|
                    ((rand.nextBoolean()?0:1)<<7));
            sb.append((char)b);
        }
        return sb.toString();

    }
    /*
    飞行挂
    挂逼模式
     */
    @SideOnly(Side.CLIENT)
    public void fly(EntityPlayer player)
    {
        if(ModKeyLoader.fly.isKeyDown())
        player.motionY = 0.5F;
    }
    /*
    闪现挂
     */
    @SideOnly(Side.CLIENT)
    public void flash(EntityPlayer player){
       // System.out.println(player.yaw);
        if(!ModKeyLoader.flash.isPressed())return;
        float f = MathHelper.sin(player.rotationYaw * 0.017453292F)*4;
        float f1 = MathHelper.cos(player.rotationYaw * 0.017453292F)*4;
        World world = player.getEntityWorld();
        int up = 0;
        for(int a = 0;a<256;a++){
            if(world.getBlockState(new BlockPos(player.posX-f,player.posY+a,player.posZ+f1)).getBlock()==Blocks.AIR){
                up = a;
                break;
            }
        }
        System.out.println(up);
        player.setPositionAndUpdate(player.posX-f,player.posY+up,player.posZ+f1);
    }
    /*
    速跑挂
     */
    @SideOnly(Side.CLIENT)
    public void superRun(EntityPlayer player){
        if(ModKeyLoader.superRun.isKeyDown()){
            if((player.motionX<0.5F&&player.motionX>-0.5F)&&(player.motionZ<0.5F&&player.motionZ>-0.5F)){
                player.motionX = player.motionX*2;
                player.motionZ = player.motionZ*2;
            }
        }
    }
    /*
    长手挂
     */
    @SideOnly(Side.CLIENT)
    public void longHand(EntityPlayer player){
        if(ModKeyLoader.longHand.isKeyDown()) {
            World world = player.getEntityWorld();
            List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().grow(30.0F, 30.0F, 30.0F));
            for (Entity entity : entityList) {
                if(entity instanceof EntityLiving)
                FMLClientHandler.instance().getClient().playerController.attackEntity(player,entity);
               // player.attackTargetEntityWithCurrentItem(entity);
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public void FP(EntityPlayer entityPlayer){
        if(ModKeyLoader.FP.isPressed()){
            FP = !FP;
        }
        if(FP){
            FMLClientHandler clientHandler = FMLClientHandler.instance();
            if(clientHandler!=null){
                EntityPlayerSP playerSP= clientHandler.getClientPlayerEntity();
                if(playerSP!=null)playerSP.sendChatMessage(ModSentenceLoader.sentences.get(rand.nextInt(ModSentenceLoader.sentences.size()-1)));
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public void findOre(EntityPlayer entityPlayer){
        World world = entityPlayer.world;
        if(world!=null){
            for(int x = -20;x<=20;x++){
                for(int y = -20;y<=20;y++){
                    for(int z = -20;z<=20;z++){
                        BlockPos pos = new BlockPos(entityPlayer.posX+x,entityPlayer.posY+y,entityPlayer.posZ+z);
                        if(world.getBlockState(pos).getBlock()==Blocks.DIAMOND_ORE){
                            world.spawnParticle(EnumParticleTypes.FLAME,entityPlayer.posX,entityPlayer.posY+1.0F,entityPlayer.posZ,
                                    x/4,y/4,z/4);
                        }
                    }
                }
            }
        }
    }
}
