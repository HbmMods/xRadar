package com.hfr.entity.logic;

import java.util.List;
import java.util.Random;

import com.hfr.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNuclearBlast extends Entity {
	
	int progress;
	int size;
	float strength;
	int dist;
	int blast;
	boolean simple = MainRegistry.nukeSimple;

	public EntityNuclearBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {

			fire();
			explode();
			
			if(progress > size)
				this.setDead();
		}
	}

	Random rand = new Random();
	private void explode() {
		
		int steps = MainRegistry.nukeStep;
		
		for(int i = progress; i < progress + steps; i++) {
			
			Vec3 vec =  Vec3.createVectorHelper(i, 0, 0);
			double circ = i * 2 * Math.PI;
			int count = (int) (circ / dist);
			
			for(int j = 0; j < count; j++) {
				
				vec.rotateAroundY(rand.nextFloat() * 360);
				EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
				double y = worldObj.getHeightValue((int)(this.posX + vec.xCoord), (int)(this.posZ + vec.zCoord)) + 1.5;
				
				if(this.simple)
					y = posY;
				
				this.worldObj.createExplosion(scapegoat, this.posX + vec.xCoord, y, this.posZ + vec.zCoord, this.strength, true);
			}
		}
		
		progress += steps;
		
	}
	
	private void fire() {
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size));
		
		for(Entity e : list) {
			
			if(e instanceof EntityPlayer || !(e instanceof EntityLivingBase)) {
				
				double dist = Math.sqrt(
						Math.pow(e.posX - posX, 2) +
						Math.pow(e.posY - posY, 2) +
						Math.pow(e.posZ - posZ, 2));
				
				if(dist <= size && canHurt(e)) {
					e.setFire(5);
					e.attackEntityFrom(MainRegistry.blast, MainRegistry.nukeDamage);
				}
			}
		}
	}
	
	private boolean canHurt(Entity e) {
		
		if(e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e;
			if(p.getUniqueID().equals("192af5d7-ed0f-48d8-bd89-9d41af8524f8"))
				return false;
		}
		
		return !isObstructed(worldObj, posX, posY, posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ);
	}
	
	private static boolean canBlock(Block b) {
		
		/*if(b == ModBlocks.concrete || b == ModBlocks.concrete_bricks || b == ModBlocks.vault_door || b == ModBlocks.vault_door_dummy || b == Blocks.obsidian)
			return true;
		
		if(Block.getIdFromBlock(b) == 699 || Block.getIdFromBlock(b) == 700 || Block.getIdFromBlock(b) == 701 || Block.getIdFromBlock(b) == 702)
			return true;*/
		
		if(MainRegistry.blastShields.contains(b))
			return true;
		
		return false;
	}
	
	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {
		
		Vec3 vector = Vec3.createVectorHelper(a - x, b - y, c - z);
		double length = vector.lengthVector();
		Vec3 nVec = vector.normalize();
		
		for(float i = 0; i < length; i += 0.25F) {
			
			Block tile = world.getBlock((int) Math.round(x + (nVec.xCoord * i)), (int) Math.round(y + (nVec.yCoord * i)), (int) Math.round(z + (nVec.zCoord * i)));

			if(canBlock(tile))
				return true;
		}
		
		return false;
	}
	
	public static EntityNuclearBlast statFac(World world, double posX, double posY, double posZ, int size, float strength, int dist, int killblast) {
		
		EntityNuclearBlast blast = new EntityNuclearBlast(world);
		blast.posX = posX;
		blast.posY = posY;
		blast.posZ = posZ;
		blast.size = size;
		blast.strength = strength;
		blast.dist = dist;
		blast.blast = killblast;
		return blast;
	}
	
	public EntityNuclearBlast setSimple(boolean b) {
		this.simple = b;
		return this;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("age");
		this.progress = nbt.getInteger("progress");
		this.size = nbt.getInteger("size");
		this.strength = nbt.getFloat("strength");
		this.dist = nbt.getInteger("dist");
		this.blast = nbt.getInteger("blast");
		this.simple = nbt.getBoolean("simple");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.ticksExisted);
		nbt.setInteger("progress", this.progress);
		nbt.setFloat("strength", this.strength);
		nbt.setInteger("dist", this.dist);
		nbt.setInteger("blast", this.blast);
		nbt.setBoolean("simple", this.simple);
	}

}
