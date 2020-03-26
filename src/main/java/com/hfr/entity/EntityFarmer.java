package com.hfr.entity;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFarmer extends EntityLiving {

	private int wheat;
	private int potato;
	private int carrot;
	private int rice;
	private int sugar;
	private int pumpkin;
	private int melon;
	
	private int fuel;
	
    public EntityFarmer(World p_i1594_1_) {
		super(p_i1594_1_);
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    protected void entityInit()
    {
        super.entityInit();
        fuel = 5 * 60 * 20;
        this.dataWatcher.addObject(16, 0);
    }
    
    public void setType(int type) {
    	this.dataWatcher.updateObject(16, type);
    }
    
    public int getType() {
    	return this.dataWatcher.getWatchableObjectInt(16);
    }
    
    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();
        super.onLivingUpdate();
    }

	protected boolean canDespawn()
    {
        return false;
    }

	@Override
	public ItemStack getHeldItem() {
		return new ItemStack(Items.iron_hoe);
	}

	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
		
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return new ItemStack[0];
	}

    public void onUpdate() {
    	super.onUpdate();

		
    	if(!worldObj.isRemote) {
    		
    		if(posY < 40 || !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
    			this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 10, 0));
    			return;
    		}
    		
    		if(this.ticksExisted % 100 == 0 && fuel > 0) {
    			this.swingItem();
    			farm();
    			this.heal(2.5F);
    		}
    		
    		if(fuel == 0) {
    			this.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 10, 0));
    			
    			//if(rand.nextInt(100) == 0)
    			//	this.attackEntityFrom(DamageSource.starve, 1.0F);
    		} else {
    			fuel--;
    		}
    	}
    }
    
    public void farm() {

    	int range = 5;
    	
    	for(int i = (int) (posX - range - 1); i < (int) (posX + range); i++) {
        	for(int j = (int) (posZ - range); j <= (int) (posZ + range); j++) {

        		Block b = worldObj.getBlock(i, (int) posY, j);
        		Block b1 = worldObj.getBlock(i, (int) posY - 1, j);
        		Block b2 = worldObj.getBlock(i, (int) posY + 1, j);
        		Block b3 = worldObj.getBlock(i, (int) posY + 2, j);
        		
        		int meta = worldObj.getBlockMetadata(i, (int) posY, j);
        		
        		//FARM CROPS
	        	if(b instanceof BlockCrops && meta == 7) {
	        		worldObj.func_147480_a(i, (int)posY, j, false);
	        		worldObj.setBlock(i, (int)posY, j, b);
	        		processCrop(b);
	        	}
	        	
	        	//WATER FARM
	        	if(b1 == Blocks.farmland && !b1.isFertile(worldObj, i, (int)posY - 1, j)) {
	        		worldObj.setBlockMetadataWithNotify(i, (int)posY - 1, j, 6, 3);
	        	}
	        	
	        	//TIL DIRT
	        	if((b1 == Blocks.dirt || b1 == Blocks.grass) && !b.isNormalCube() && rand.nextInt(5) == 0) {
	        		worldObj.playSoundEffect(posX, posY, posZ, b1.stepSound.getStepResourcePath(), (b1.stepSound.getVolume() + 1.0F) / 2.0F, b1.stepSound.getPitch() * 0.8F);
	        		worldObj.setBlock(i, (int) posY - 1, j, Blocks.farmland);
	        	}
	        	
	        	//FARM SUGAR
	        	if(getType() == 1 && (b1 == Blocks.dirt || b1 == Blocks.grass || b1 == Blocks.sand) && b == Blocks.reeds) {

	        		if(b3 == Blocks.reeds) {
		        		worldObj.func_147480_a(i, (int)posY + 2, j, false);
		        		processCrop(b3);
	        		}
	        		if(b2 == Blocks.reeds) {
		        		worldObj.func_147480_a(i, (int)posY + 1, j, false);
		        		processCrop(b2);
	        		}
	        	}
	        	
	        	//FARM GURDS
	        	if(getType() == 2) {

	        		if(b == Blocks.pumpkin || b == Blocks.melon_block) {
		        		worldObj.func_147480_a(i, (int)posY, j, false);
		        		processCrop(b);
	        		}
	        	}
        	}
    	}
    }
    
    private void processCrop(Block b) {
    	
    	int type = getType();
    	
    	if(b == Blocks.wheat)
    		wheat += 2;
    	
    	if(b == Blocks.potatoes)
    		potato += (3 * (type == 0 ? 3 : 1));
    	
    	if(b == Blocks.carrots)
    		carrot += 3;
    	
    	if(b == ModBlocks.rice)
    		rice += 2 + (type == 1 ? 2 : 0);
    	
    	if(b == Blocks.reeds)
    		sugar += 1;
    	
    	if(b == Blocks.pumpkin)
    		pumpkin += 1;
    	
    	if(b == Blocks.melon_block)
    		melon += 1;

    	if(wheat > 64 * 3)
    		wheat = 64 * 3;
    	if(potato > 64 * 3)
    		potato = 64 * 3;
    	if(carrot > 64 * 3)
    		carrot = 64 * 3;
    	if(rice > 64 * 3)
    		rice = 64 * 3;
    	
    	if(sugar > 64 * 2)
    		sugar = 64 * 2;
    	if(pumpkin > 64 * 2)
    		pumpkin = 64 * 2;
    	if(melon > 64 * 2)
    		melon = 64 * 2;
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

    	if(wheat > 0)
    		this.dropItem(Items.wheat, wheat);
    	if(potato > 0)
    		this.dropItem(Items.potato, potato);
    	if(carrot > 0)
    		this.dropItem(Items.carrot, carrot);
    	if(rice > 0)
    		this.dropItem(ModItems.rice, rice);
    	if(sugar > 0)
    		this.dropItem(Items.reeds, sugar);
    	if(pumpkin > 0)
    		this.dropItem(Item.getItemFromBlock(Blocks.pumpkin), pumpkin);
    	if(melon > 0)
    		this.dropItem(Item.getItemFromBlock(Blocks.melon_block), melon);
    	
    	this.entityDropItem(new ItemStack(Items.iron_hoe, 1, rand.nextInt(Items.iron_hoe.getMaxDamage())), 0.0F);
    }
    
    public boolean interact(EntityPlayer player)
    {
    	if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemFood && fuel < 60 * 60 * 20) {
    		
    		fuel += ((ItemFood)player.getHeldItem().getItem()).func_150905_g(player.getHeldItem()) * 1200;
            worldObj.playSoundAtEntity(this, "random.burp", 0.5F, rand.nextFloat() * 0.1F + 0.9F);
    		
    		player.getHeldItem().stackSize--;
    		
    		if(player.getHeldItem().stackSize == 0)
    			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
    		
    		return true;
    	}
    	
    	boolean b = false;

        if(wheat > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Items.wheat, wheat));
        	wheat = 0;
        	b = true;
        }
        if(potato > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Items.potato, potato));
        	potato = 0;
        	b = true;
        }
        if(carrot > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Items.carrot, carrot));
        	carrot = 0;
        	b = true;
        }
        if(rice > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(ModItems.rice, rice));
        	rice = 0;
        	b = true;
        }
        if(sugar > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Items.reeds, sugar));
        	sugar = 0;
        	b = true;
        }
        if(pumpkin > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Blocks.pumpkin, pumpkin));
        	pumpkin = 0;
        	b = true;
        }
        if(melon > 0) {
        	player.inventory.addItemStackToInventory(new ItemStack(Blocks.melon_block, melon));
        	melon = 0;
        	b = true;
        }
        
        if(b) {
            worldObj.playSoundAtEntity(this, "mob.chicken.plop", 1.0F, rand.nextFloat() * 0.1F + 0.9F);
        	player.inventoryContainer.detectAndSendChanges();
        } else {
            worldObj.playSoundAtEntity(this, "mob.villager.no", 1.0F, 1.0F);
        }
        
        return b;
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		wheat = nbt.getInteger("wheat");
		potato = nbt.getInteger("potato");
		carrot = nbt.getInteger("carrot");
		rice = nbt.getInteger("rice");
		sugar = nbt.getInteger("sugar");
		pumpkin = nbt.getInteger("pumpkin");
		melon = nbt.getInteger("melon");
		fuel = nbt.getInteger("fuel");
		setType(nbt.getInteger("type"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		nbt.setInteger("wheat", wheat);
		nbt.setInteger("potato", potato);
		nbt.setInteger("carrot", carrot);
		nbt.setInteger("rice", rice);
		nbt.setInteger("sugar", sugar);
		nbt.setInteger("pumpkin", pumpkin);
		nbt.setInteger("melon", melon);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("type", getType());
		
	}

}
