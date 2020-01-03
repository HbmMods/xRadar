package com.hfr.blocks;

import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.tileentity.TileEntityDebug;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockDebug extends BlockContainer {

	public BlockDebug(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDebug();
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
		
		if(!world.isRemote) {
			
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			System.out.println(owner.zone);
			if(owner.zone == Zone.WILDERNESS) {
				ClowderTerritory.setZoneForCoord(world, ClowderTerritory.getCoordPair(x, z), Zone.SAFEZONE);
				player.addChatMessage(new ChatComponentText("NOW SAFE ZONE"));
			} else if(owner.zone == Zone.SAFEZONE) {
				ClowderTerritory.setZoneForCoord(world, ClowderTerritory.getCoordPair(x, z), Zone.WARZONE);
				player.addChatMessage(new ChatComponentText("NOW WAR ZONE"));
			} else {
				ClowderTerritory.setZoneForCoord(world, ClowderTerritory.getCoordPair(x, z), Zone.WILDERNESS);
				player.addChatMessage(new ChatComponentText("NOW WILDERNESS"));
			}
			
			/*world.setBlockToAir(x, y, z);
			
			DemoThread thread = new DemoThread();
			thread.explosion = new ExplosionNukeRay(world, x, y, z, 100, (int)(4 * Math.PI * Math.pow(100, 2) * 25));
			thread.start();*/
			
			/*world.setBlockToAir(x, y, z);
			
			ExplosionController.registerExplosion(
				new ExplosionNukeRay(world, x, y, z, 50, (int)(4 * Math.PI * Math.pow(50, 2) * 25))
			);*/
			
			return true;
		}

		return true;
	}
	
	/*class DemoThread extends Thread {
		
		public ExplosionNukeRay explosion;
		
		@Override
		public void run() {
			
			System.out.println("Initiating Demon Test Thread!");
			
			boolean looper = true;
			
			do {
				if(!explosion.isAusf3Complete) {
					explosion.collectTipMk4_5(1000);
					System.out.println("DT-Tip collector, passed!");
				} else if(explosion.getStoredSize() > 0) {
					explosion.processTip(1000);
					System.out.println("DT-Tip processor, passed!");
				} else {
					looper = false;
				}
			} while(looper);
			
			System.out.println("DT-Thread, over!");
		}
	}*/

}
