package com.hfr.tileentity.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.handler.FluidHandler;
import com.hfr.items.ItemFuel;
import com.hfr.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityRBMKElement extends TileEntityMachineBase implements IFluidHandler {
	
	public float reactivity;
	public static final float maxReactivity = 400;
	
	public FluidTank water;
	public FluidTank steam;

	public TileEntityRBMKElement() {
		super(4);
		water = new FluidTank(FluidRegistry.WATER, 4000, 8000);
		steam = new FluidTank(FluidHandler.STEAM, 4000, 8000);
	}

	@Override
	public String getName() {
		return "container.rbmkElement";
	}

	@Override
	public void updateEntity() {
		this.updateGauge((int)reactivity, 0, 15);
		this.updateGauge(water.getFluidAmount(), 1, 15);
		this.updateGauge(steam.getFluidAmount(), 2, 15);
		
		if(!worldObj.isRemote)
			process();
	}
	
	public void processGauge(int val, int id) {
		switch(id) {
		case 0: reactivity = val; break;
		case 1: water.getFluid().amount = val; break;
		case 2: steam.getFluid().amount = val; break;
		}
	}
	
	public void process() {
		
		float reactivity = calculateReactivity();
		generateXenon(getPoisonRate());
		burnXenon(getXenonBurnup());

		irradiate(ForgeDirection.NORTH, reactivity);
		irradiate(ForgeDirection.SOUTH, reactivity);
		irradiate(ForgeDirection.EAST, reactivity);
		irradiate(ForgeDirection.WEST, reactivity);
		
		int conv = 100;

		int defacto = Math.min(steam.getCapacity() - steam.getFluidAmount(), water.getFluidAmount());
		defacto = (int) Math.min(defacto, reactivity * conv / maxReactivity);
		
		if(defacto > 0) {
			steam.getFluid().amount += defacto;
			water.getFluid().amount -= defacto;
			this.markDirty();
		}
		
		this.reactivity = 0;
	}
	
	public void irradiate(ForgeDirection dir, float reactivity) {
		
		int x = xCoord;
		int z = zCoord;
		
		float origin = reactivity;
		float adjusted = origin;
		
		for(int i = 0; i < 3; i++) {
			
			x += dir.offsetX;
			z += dir.offsetZ;
			
			Block b = worldObj.getBlock(x, yCoord, z);
			
			if(b == ModBlocks.block_boron)
				return;
			
			if(b == ModBlocks.block_graphite) {
				adjusted = origin * 2;
				
				//one side can release an unadjusted amount of up to 75 at max (exactly 50 would be required to keep the reaction stable)
				//the surplus 25 reactivity will simulate the chain reaction increasing in magnitude
				if(adjusted > 100)
					adjusted = 100;
			}
			
			if(b == ModBlocks.rbmk_element) {
				TileEntityRBMKElement element = (TileEntityRBMKElement)worldObj.getTileEntity(x, yCoord, z);
				element.reactivity += adjusted;
				return;
			}
		}
	}
	
	//adds xenon to each pellet
	public void generateXenon(float xenon) {

		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				float poison = ItemFuel.getPoison(slots[i]) + xenon;
				
				if(poison > 100)
					poison = 100;
				
				ItemFuel.setPoison(slots[i], poison);
			}
		}
	}
	
	//removes xenon from each pellet
	public void burnXenon(float xenon) {

		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				float poison = ItemFuel.getPoison(slots[i]) - xenon;
				
				if(poison < 0)
					poison = 0;
				
				ItemFuel.setPoison(slots[i], poison);
			}
		}
	}
	
	//the generation rate of xenon poison based on the reactivity
	public float getPoisonRate() {
		
		float x = (getEnrichtment()) * ((float)reactivity / (float)maxReactivity);
		
		return x / 800F;
	}
	
	//the reduction rate of xenon poison based on the reactivity
	//surpasses the poison rate at >25 reactivity
	public float getXenonBurnup() {
		
		float x = (getEnrichtment()) * ((float)reactivity / (float)maxReactivity);
		return (float) (Math.pow(x, 2) / 10000F);
	}
	
	//calculates the output reactivity based on input reactivity, xenon poison and enrichment
	public float calculateReactivity() {
		
		float neutrons = 0;
		
		if(reactivity < 25) {
			//if the reaction is dead, the maximum reactivity is 5;
			neutrons = (getEnrichtmentFlat() / 100F) * ((100 - getXenonPoison()) / 100F) * 5F;
		} else {
			//if the reaction is running, the maximum reactivity is determined by reactivity
			//base calculation (enrichment * 1-poison * reactivity scaled to 70)...
			neutrons = (getEnrichtmentFlat() / 100F) * ((100 - getXenonPoison()) / 100F) * (reactivity * 70F / 400F);
			//...and adds a flat 5
			
			if(neutrons > 0)
				neutrons += 5F;
		}
		
		return neutrons;
	}
	
	//returns the total enrichment degree from 0-100%
	public float getEnrichtment() {

		float cap = 400;
		float enrichment = 0;
		
		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				enrichment += (ItemFuel.getDura(slots[i]) * 100F / (float)ItemFuel.maxLife);
			}
		}
		
		return (enrichment * 100F) / cap;
	}
	
	//returns 25% per given fuel pellet
	//used to keep reactivity constant during depletion
	public float getEnrichtmentFlat() {
		
		float enrichment = 0;
		
		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				enrichment += 25;
			}
		}
		
		return enrichment;
	}

	//returns the relative amount of xenon poison from 0-100%
	public float getXenonPoison() {
		
		float cap = 0;
		float poison = 0;
		
		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				cap += 100;
				poison += ItemFuel.getPoison(slots[i]);
			}
		}
		
		if(cap == 0)
			return 0;
		
		return (poison * 100F) / cap;
	}
	
	public int getXenonScaled(int i) {
		return (int) ((getXenonPoison() * i) / 100F);
	}
	
	public int getReactivityScaled(int i) {
		return (int)((reactivity * i) / maxReactivity);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.reactivity = nbt.getFloat("reactivity");

		water.readFromNBT(nbt);
		steam.readFromNBT(nbt);
		
		if(water.getFluid() == null)
			water.setFluid(new FluidStack(FluidRegistry.WATER, 0));
		
		if(steam.getFluid() == null)
			steam.setFluid(new FluidStack(FluidHandler.STEAM, 0));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setFloat("reactivity", this.reactivity);

		water.writeToNBT(nbt);
		steam.writeToNBT(nbt);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		
		if(canFill(from, resource.getFluid())) {
			
			//the cap is 8000 for BOTH tanks together
			int cap = 8000 - water.getFluidAmount() - steam.getFluidAmount();
			
			//calculates how much space the shared tank has
			int fill = Math.min(cap, resource.amount);
			
			//cancels filling if there's nothing to fill
			if(fill <= 0)
				return 0;
			
			//creates a copy of the fluidstack but with the cap in mind
			FluidStack sauce = resource.copy();
			sauce.amount = fill;


			this.markDirty();
			return water.fill(sauce, doFill);
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		
		if(canDrain(from, resource.getFluid()) && steam.getFluidAmount() > 0) {
			this.markDirty();
			
			FluidStack drain = steam.drain(Math.min(steam.getFluidAmount(), resource.amount), doDrain);
			
			if(steam.getFluid() == null)
				steam.setFluid(new FluidStack(FluidHandler.STEAM, 0));
			
			return drain;
		}
		
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		
		if(steam.getFluidAmount() > 0) {
			this.markDirty();
			
			FluidStack drain = steam.drain(Math.min(steam.getFluidAmount(), maxDrain), doDrain);
			
			if(steam.getFluid() == null)
				steam.setFluid(new FluidStack(FluidHandler.STEAM, 0));
			
			return drain;
		}
		
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		
		if(fluid == FluidRegistry.WATER && water.getFluidAmount() < water.getCapacity()) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		
		if(fluid == FluidHandler.STEAM && steam.getFluidAmount() > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { new FluidTankInfo(water), new FluidTankInfo(steam) };
	}
}