package com.hfr.blocks.clowder;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.clowder.TileEntityFlag;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Flag extends BlockContainer {

	public Flag(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFlag();
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_flag, world, x, y, z);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if(player instanceof EntityPlayer && !world.isRemote) {
			TileEntityFlag flag = (TileEntityFlag)world.getTileEntity(x, y, z);
			
			Clowder clowder = Clowder.getClowderFromPlayer((EntityPlayer)player);
			
			if(clowder != null && flag.canSeeSky()) {
				flag.setOwner(clowder);
				flag.setMode(3);
				flag.isClaimed = true;
				flag.generateClaim();
			} else {
				flag.height = 0.0F;
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You won't be able to raise this flag. This may be due to:"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag lacking a solid 5x5 block foundation"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag's foundation not having sky access"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-You not being in any faction"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag being below Y:45 or above Y:100"));
			}
			
			flag.markDirty();
		}

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i)
    {
		Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
		
		if(owner != null && owner.zone == Zone.FACTION) {
			TileEntityFlag flag = (TileEntityFlag)world.getTileEntity(x, y, z);
			
			if(flag.owner != null)
				flag.setOwner(null);
		}
		
		super.breakBlock(world, x, y, z, b, i);
    }

}
