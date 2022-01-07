package com.hfr.blocks.clowder;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.tileentity.clowder.TileEntityConquerer;
import com.hfr.tileentity.clowder.TileEntityFlag;

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

public class Conquerer extends BlockContainer {

	public Conquerer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityConquerer();
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
			TileEntityConquerer flag = (TileEntityConquerer)world.getTileEntity(x, y, z);
			
			Clowder clowder = Clowder.getClowderFromPlayer((EntityPlayer)player);
			flag.owner = clowder;
			
			
			//prerequisite to placing a flag very important allah bookmark
			if(clowder != null && flag.checkBorder(x, z) && flag.canSeeSky() && noProximity(world, x, y, z, clowder)) //weeder change for faction specific flag range
			{
				
				//labjac punjab limits
				CoordPair loc = ClowderTerritory.getCoordPair(x, z);
				Ownership owner = ClowderTerritory.getOwnerFromCoords(loc);
				
				//exception so no capping from offline master if you were targetting a vassal
				if (owner.owner != null && clowder.vassalTarget && clowder.enemy == owner.owner && !clowder.enemy.isRaidable())
				{
					flag.owner = null;
					((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "If targetting a vassal, the master's land is safe when the master is offline!"));	
				}
				//exception so no capping from bitches but reminder you can bomb them
				else if (owner.owner != null && clowder.vassalTarget && clowder.enemy == owner.owner.suzerain && owner.owner.bitch)
				{
					flag.owner = null;
					((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You cannot capture from another man's bitch, but you can use raiding weapons against their land!"));	
				}
				//exception so bitches can only cap from master in a revolt
				else if (clowder.bitch && owner.owner != clowder.suzerain)
				{
					flag.owner = null;
					((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Bitches can only capture land during revolts from their master!"));	
				}
				else
				//exception so no capping during revolts, but griefing should still be allowed - wait nvm retconned allow conquest during revolts
				//if (owner.owner != null && clowder.enemy != clowder.suzerain && owner.owner!= clowder.suzerain && owner.owner.suzerain != clowder)
				//if you are at war with terrain owner, or you are victim and in attacker terrain, success!!! PRAISE ALLAH - also added vassal exceptions     Also doesnt need to be "raidable" >:)
				if (   (owner.owner != null && clowder.enemy == owner.owner && clowder.getWartime() > 0)  ||   (owner.owner != null && owner.owner.enemy == clowder && owner.owner.getWartime() > 0) || (owner.owner.suzerain != null && clowder.enemy == owner.owner.suzerain && clowder.getWartime() > 0) || (owner.owner.suzerain != null && owner.owner.suzerain.enemy == clowder && owner.owner.suzerain.getWartime() > 0))
				{
				//flag.owner.addPrestigeReq(0.2F, world);   holding provinces will be prestige req determiner
				flag.markDirty();
				}
				else
				{
					flag.owner = null;
					((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You are not at war with this faction! Use /c fabricate *name*"));				
				}
			}
			else {
				flag.owner = null;
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You won't be able to raise this flag. This may be due to:"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-You not being in any faction"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag not having sky access"));
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag not being in a foreign border chunk"));
				//((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The enemy faction or your faction not being raidable"));   >:)
				((EntityPlayer)player).addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag being too close to another conquest flag"));
			}
		}

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	public boolean noProximity(World world, int x, int y, int z, Clowder clowder) {//weeder change for faction specific
		
		int range = 4;
		
		for(int ix = x - range; ix <= x + range; ix++) {
			for(int iy = y - 3; iy <= y + 3; iy++) {
				for(int iz = z - range; iz <= z + range; iz++) {
					
					if(ix == x && iy == y && iz == z)
						continue;
					//weeder change for faction specific
					if(world.getBlock(ix, iy, iz) == ModBlocks.clowder_conquerer && world.getTileEntity(ix, iy, iz) instanceof TileEntityConquerer && ((TileEntityConquerer)world.getTileEntity(ix, iy, iz)).owner == clowder) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i)
    {
		TileEntityConquerer flag = (TileEntityConquerer)world.getTileEntity(x, y, z);
		if(flag != null && flag.owner != null) {
			//flag.owner.addPrestigeReq(-0.2F, world);  holding provinces will be prestige req determiner
		}
		
		super.breakBlock(world, x, y, z, b, i);
    }
}
