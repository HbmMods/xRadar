package com.hfr.ai;

import java.util.List;

import com.hfr.main.MainRegistry;
import com.hfr.main.MainRegistry.GriefEntry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityAI_MLPF extends EntityAIBase {

	private Class targetClass;
    private EntityLivingBase target;
	private EntityLiving mover;
	private int range;
	private static final int vertical = 10;
    private double speed;
	
	public EntityAI_MLPF(EntityLiving entity, Class targetClass, int range, double speed)
	{
		this.mover = entity;
		this.targetClass = targetClass;
		this.range = range;
		this.speed = speed;
	}
	
	@Override
	public boolean shouldExecute() {
		
		//roll the dice for targetiing if there's nothing to track
		if(mover.getRNG().nextInt(100) < 5 && mover.getAttackTarget() == null) {
			//System.out.println("Randomizer fired!");
			//load potential targets
			calculateTarget();
			
			//start if there is a valid target
			return target != null;
		}
		
		return false;
	}
	
    public void startExecuting() {

    	//create a path line from mover to target
    	Vec3 vec = Vec3.createVectorHelper(
    			target.posX - mover.posX,
    			target.posY - mover.posY,
    			target.posZ - mover.posZ);
    	
    	vec = vec.normalize();
    	
    	//line length is capped so the pathfinder can manage it
    	int range = 20;

    	vec.xCoord *= range;
    	vec.yCoord *= range;
    	vec.zCoord *= range;
    	
    	//target positions are set (with randomized Y-offset)
    	double x = mover.posX + vec.xCoord;
    	double y = mover.posY + vec.yCoord - 5 + mover.getRNG().nextInt(11);
    	double z = mover.posZ + vec.zCoord;
    	
    	//System.out.println("Routing to " + x + "/" + y + "/" + z);
    	
		//this is where the magic happens
        boolean success = this.mover.getNavigator().tryMoveToXYZ(x, y, z, this.speed);
        
        //System.out.println("Start successful? " + success);
    }
	
	@Override
	public boolean continueExecuting() {

		//only continue if the path is valid
        return !this.mover.getNavigator().noPath();
	}
	
	@Override
	public void resetTask()
	{
		//once the task is complete, remove target
		target = null;
	}
	
	//scans the area and determines a new target entity
	private void calculateTarget() {
		
		List list = mover.worldObj.getEntitiesWithinAABB(targetClass, AxisAlignedBB.getBoundingBox(
				mover.posX - range,
				mover.posY - vertical,
				mover.posZ - range,
				mover.posX + range,
				mover.posY + vertical,
				mover.posZ + range));
		
		if(list != null && !list.isEmpty())
			target = (EntityLivingBase)list.get(mover.getRNG().nextInt(list.size()));
		//else
		//	System.out.println("No target...");
	}
}
