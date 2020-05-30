package com.hfr.tileentity.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.blocks.door.DummyBlockBlast;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.TEVaultPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBlastDoor extends TileEntity {
	
	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public boolean redstoned = false;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
    public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) || worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord + 6, zCoord)) {
				
				if(!redstoned) {
					this.tryToggle();
				}
				redstoned = true;
				
			} else {
				redstoned = false;
			}
	    			
	    	if(state != 1) {
	    		timer = 0;
	    	} else {
	    		timer++;
    			
    			if(isOpening) {
    				if(timer >= 0) {
    					removeDummy(xCoord, yCoord + 1, zCoord);
    				}
    				if(timer >= 20) {
    					removeDummy(xCoord, yCoord + 2, zCoord);
    				}
    				if(timer >= 40) {
    					removeDummy(xCoord, yCoord + 3, zCoord);
    				}
    				if(timer >= 60) {
    					removeDummy(xCoord, yCoord + 4, zCoord);
    				}
    				if(timer >= 80) {
    					removeDummy(xCoord, yCoord + 5, zCoord);
    				}
    			} else {
    				if(timer >= 20) {
    					placeDummy(xCoord, yCoord + 5, zCoord);
    				}
    				if(timer >= 40) {
    					placeDummy(xCoord, yCoord + 4, zCoord);
    				}
    				if(timer >= 60) {
    					placeDummy(xCoord, yCoord + 3, zCoord);
    				}
    				if(timer >= 80) {
    					placeDummy(xCoord, yCoord + 2, zCoord);
    				}
    				if(timer >= 100) {
    					placeDummy(xCoord, yCoord + 1, zCoord);
    				}
    			}
	    		
	    		if(timer >= 100) {
	    			
	    			if(isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
		}
    }
	
	public void open() {
		if(state == 0) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 1, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			isOpening = true;
			state = 1;

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.reactorStart", 0.5F,
					0.75F);
		}
	}
	
	public void finishOpen() {
		state = 2;

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.reactorStop", 0.5F,
				1.0F);
	}
	
	public void close() {
		if(state == 2) {
	    	PacketDispatcher.wrapper.sendToAll(new TEVaultPacket(xCoord, yCoord, zCoord, isOpening, state, 1, 0));
			isOpening = false;
			state = 1;

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.reactorStart", 0.5F,
					0.75F);
		}
	}
	
	public void finishClose() {
		state = 0;

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.reactorStop", 0.5F,
				1.0F);
	}
	
	public void openNeigh() {

		TileEntity te0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity te1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity te2 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity te3 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canOpen()) {
				((TileEntityBlastDoor)te0).open();
				((TileEntityBlastDoor)te0).openNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canOpen()) {
				((TileEntityBlastDoor)te1).open();
				((TileEntityBlastDoor)te1).openNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canOpen()) {
				((TileEntityBlastDoor)te2).open();
				((TileEntityBlastDoor)te2).openNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canOpen()) {
				((TileEntityBlastDoor)te3).open();
				((TileEntityBlastDoor)te3).openNeigh();
			}
		}
	}
	
	public void closeNeigh() {

		TileEntity te0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity te1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity te2 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity te3 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canClose()) {
				((TileEntityBlastDoor)te0).close();
				((TileEntityBlastDoor)te0).closeNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canClose()) {
				((TileEntityBlastDoor)te1).close();
				((TileEntityBlastDoor)te1).closeNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canClose()) {
				((TileEntityBlastDoor)te2).close();
				((TileEntityBlastDoor)te2).closeNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canClose()) {
				((TileEntityBlastDoor)te3).close();
				((TileEntityBlastDoor)te3).closeNeigh();
			}
		}
	}
	
	public boolean canOpen() {
		return state == 0;
	}
	
	public boolean canClose() {
		return state == 2;
	}
	
	public void tryToggle() {

		if(canOpen()) {
			open();
			openNeigh();
		} else if(canClose()) {
			close();
			closeNeigh();
		}
	}
	
	public boolean placeDummy(int x, int y, int z) {
		
		if(!worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z))
			return false;
		
		worldObj.setBlock(x, y, z, ModBlocks.blast_door_dummy);
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
		
		return true;
	}
	
	public void removeDummy(int x, int y, int z) {
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.blast_door_dummy) {
			DummyBlockBlast.safeBreak = true;
			worldObj.setBlock(x, y, z, Blocks.air);
			DummyBlockBlast.safeBreak = false;
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		isOpening = nbt.getBoolean("isOpening");
		state = nbt.getInteger("state");
		sysTime = nbt.getLong("sysTime");
		timer = nbt.getInteger("timer");
		redstoned = nbt.getBoolean("redstoned");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOpening", isOpening);
		nbt.setInteger("state", state);
		nbt.setLong("sysTime", sysTime);
		nbt.setInteger("timer", timer);
		nbt.setBoolean("redstoned", redstoned);
	}

}
