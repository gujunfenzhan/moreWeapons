package ink.mhxk.sword.utils.bigsword;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PathNavigatePlayer {
    public EntityPlayer player;
    public World world;
    public BlockPos tryTopos;
    public BlockPos playerPos;
    public PlayerControllerMP controllerMP;
    public BlockPos nowPos;
    public List<BlockPos> pathPos = new ArrayList<>();
    public PathNavigatePlayer(EntityPlayer player, World world, PlayerControllerMP controllerMP){
        this.player = player;
        this.world = world;
        this.controllerMP = controllerMP;

    }
    public void tryMoveToXYZ(BlockPos pos){
        this.tryTopos = pos;
        nowPos = playerPos;
        while(true){
            int nextNu = getNextNu(nowPos.getY(),tryTopos.getY());
            if(nextNu==-1)break;
            BlockPos testPos = new BlockPos(nowPos.getX(),nextNu,nowPos.getZ());
            nowPos = new BlockPos(nowPos.getX(),nextNu,nowPos.getZ());

        }
        while(true){

        }

    }
    public int getNextNu(int a1,int a2){
        if(a1<a2)return a1+1;
        else if(a1>a2)return a2+1;
        else return -1;
    }
    public void updatePath(){
        //更新玩家pos
        playerPos = new BlockPos(player);
        //检查是否到达终点
        if(playerPos==tryTopos)tryTopos=null;
        //玩家在下方，需要跳跃
        if(playerPos.getY()<tryTopos.getY()){
            player.motionY = 1.0F;
            player.motionX = tryTopos.getX()-player.posX;
            player.motionZ = tryTopos.getZ()-player.posZ;
        }else{
            //直接行走
            player.motionX = tryTopos.getX()-player.posX;
            player.motionZ = tryTopos.getZ()-player.posZ;
        }
    }
    public boolean isArrive(){
        return tryTopos==null;
    }
    public boolean isFindPostion(BlockPos pos) {
        for (int x = -2; x < 2; x++) {
            for (int y = -2; y < 2; y++) {
                for (int z = -2; z < 2; z++) {
                    IBlockState block = world.getBlockState(pos.add(x, y, z));
                    if (block.getBlock() != Blocks.AIR || block.getBlock() == Blocks.LAVA || block.getBlock() == Blocks.WATER) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
