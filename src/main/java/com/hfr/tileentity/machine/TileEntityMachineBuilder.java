package com.hfr.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hfr.items.ModItems;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxGaugePacket;
import com.hfr.packet.tile.BuilderPacket;
import com.hfr.schematic.Schematic;
import com.hfr.schematic.SchematicPronter;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineBuilder extends TileEntityMachineBase {
	
	public Schematic preview;
	public int ox = 1;
	public int oz = 1;
	public int lock = -1;
	public static List<SchemOffer> offers = new ArrayList();

	public TileEntityMachineBuilder() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.builder";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			/*worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			
			if(!MainRegistry.schems.isEmpty())
				SchematicPronter.pront(worldObj, xCoord, yCoord, zCoord, MainRegistry.schems.get(worldObj.rand.nextInt(MainRegistry.schems.size())));*/
			
			if(preview != null) {
				if(ox > 1)
					ox = 1;
				if(oz > 1)
					oz = 1;
				if(ox < -preview.width)
					ox = -preview.width;
				if(oz < -preview.length)
					oz = -preview.length;
			} else {
				ox = 1;
				oz = 1;
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new BuilderPacket(xCoord, yCoord, zCoord, ox, oz), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		}
	}
	
	public boolean doesIntersect() {
		
		if(ox <= 0 && oz <= 0 &&
				ox + preview.width > 0 && oz + preview.length > 0)
			return true;
		
		return false;
	}
	
	public void construct(Schematic schem) {
		
		int i = schem.value;
		
		if(slots[0] != null && slots[0].getItem() == ModItems.wrench && slots[0].stackSize >= i) {

			this.decrStackSize(0, i);
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hfr:block.buttonYes", 1.0F, 1.0F);
			SchematicPronter.pront(worldObj, xCoord + ox, yCoord, zCoord + oz, schem);
			
			preview = null;
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		
		} else {

			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hfr:block.buttonNo", 1.0F, 1.0F);
		}
	}
	
	public void deconstruct(Schematic schem) {
		
		SchematicPronter.delet(worldObj, xCoord + ox, yCoord, zCoord + oz, schem);
		preview = null;
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
	}
	
	public static class SchemOffer {
		public String name;
		public int value;
		
		public SchemOffer(String name, int value) {
			this.name = name;
			this.value = value;
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}