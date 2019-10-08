package com.hfr.tileentity;

import com.hfr.items.ItemFuel;
import com.hfr.items.ModItems;

public class TileEntityRBMKElement extends TileEntityMachineBase {
	
	public int reactivity;
	public static final int maxReactivity = 400;

	public TileEntityRBMKElement() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.rbmkElement";
	}

	@Override
	public void updateEntity() {
		
	}
	
	//the generation rate of xenon poison based on the reactivity
	public float getPoisonRate() {
		
		float x = (getEnrichtment() / 100F) * ((float)reactivity / (float)maxReactivity);
		return x / 4F;
	}
	
	//the reduction rate of xenon poison based on the reactivity
	//surpasses the poison rate at >25 reactivity
	public float getXenonBurnup() {
		
		float x = (getEnrichtment() / 100F) * ((float)reactivity / (float)maxReactivity);
		return (float) (Math.pow(x, 2) / 4F);
	}
	
	//calculates the output reactivity based on input reactivity, xenon poison and enrichment
	public float calculateReactivity() {
		
		float neutrons = 0;
		
		if(reactivity < 25) {
			//if the reaction is dead, the maximum reactivity is 5;
			neutrons = (getEnrichtment() / 100F) * ((100 - getXenonPoison()) / 100F) * 5F;
		} else {
			//if the reaction is running, the maximum reactivity is determined by reactivity
			//base calculation (enrichment * 1-poison * reactivity scaled to 45)...
			neutrons = (getEnrichtment() / 100F) * ((100 - getXenonPoison()) / 100F) * (reactivity * 45F / 400F);
			//...and adds a flat 5
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

	//returns the relative amount of xenon poison from 0-100%
	public float getXenonPoison() {
		
		float cap = 0;
		float poison = 0;
		
		for(int i = 0; i < 4; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.uranium_fuel) {
				cap += 100;
				poison += ItemFuel.getPoison(slots[0]);
			}
		}
		
		if(cap == 0)
			return 0;
		
		return (poison * 100F) / cap;
	}
}