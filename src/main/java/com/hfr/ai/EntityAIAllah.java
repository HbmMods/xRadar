package com.hfr.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIAllah extends EntityAIBase {

	EntityLivingBase target;
	EntityCreeper entityDigger;
	
	public EntityAIAllah(EntityCreeper entity)
	{
		this.entityDigger = entity;
	}
	
	@Override
	public boolean shouldExecute()
	{
		target = entityDigger.getAttackTarget();
		
		if(entityDigger.ticksExisted % 10 == 0 && target != null && entityDigger.getNavigator().noPath() && entityDigger.getDistanceToEntity(target) > 1D && (target.onGround || !entityDigger.canEntityBeSeen(target)))
		{
	        Vec3 vec3 = Vec3.createVectorHelper(entityDigger.posX, entityDigger.posY + entityDigger.getEyeHeight(), entityDigger.posZ);
	        Vec3 vec31 = Vec3.createVectorHelper(target.posX, target.posY + target.getEyeHeight(), target.posZ);
			MovingObjectPosition mop = RayCastBlocks(entityDigger.worldObj, vec3, vec31, false);
			
			if(mop != null) {
			
				Vec3 vec32 = Vec3.createVectorHelper(
						mop.hitVec.xCoord - entityDigger.posX,
						mop.hitVec.yCoord - (entityDigger.posY + entityDigger.getEyeHeight()),
						mop.hitVec.zCoord - entityDigger.posZ);
			
				return mop != null && mop.typeOfHit == MovingObjectType.BLOCK && vec32.lengthVector() <= 3;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean continueExecuting()
	{
		return target != null && entityDigger != null && target.isEntityAlive() && entityDigger.isEntityAlive() && entityDigger.getNavigator().noPath() && entityDigger.getDistanceToEntity(target) > 1D && (target.onGround || !entityDigger.canEntityBeSeen(target));
	}
	
	@Override
	public void updateTask()
	{
    	entityDigger.func_146079_cb();
	}
	
	@Override
	public void resetTask()
	{ }
	
	public static MovingObjectPosition RayCastBlocks(World world, Vec3 vector1, Vec3 vector2, boolean liquids)
    {
        return world.func_147447_a(vector1, vector2, liquids, !liquids, false);
    }
}
