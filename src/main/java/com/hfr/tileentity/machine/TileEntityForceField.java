package com.hfr.tileentity.machine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hfr.entity.missile.EntityMissileAntiBallistic;
import com.hfr.entity.missile.EntityMissileBaseSimple;
import com.hfr.entity.projectile.EntityRailgunBlast;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.TEFFPacket;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityForceField extends TileEntity implements ISidedInventory, IEnergyHandler {

	private ItemStack slots[];

	public int health = 100;
	public int maxHealth = 100;
	public EnergyStorage storage = new EnergyStorage(
			(MainRegistry.fieldBase + MainRegistry.fieldRange * 16 + MainRegistry.fieldHealth) * 50,
			(MainRegistry.fieldBase + MainRegistry.fieldRange * 16 + MainRegistry.fieldHealth) * 5,
			(MainRegistry.fieldBase + MainRegistry.fieldRange * 16 + MainRegistry.fieldHealth) * 5);

	public int powerCons;
	public int cooldown = 0;
	public int blink = 0;
	public float radius = 16;
	public boolean isOn = false;
	public int color = 0x0000FF;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };

	private String customName;

	public TileEntityForceField() {
		slots = new ItemStack[3];
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (slots[i] != null) {
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.forceField";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {

		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
				slots[i] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		storage.readFromNBT(nbt);
		this.health = nbt.getInteger("health");
		this.maxHealth = nbt.getInteger("maxHealth");
		this.cooldown = nbt.getInteger("cooldown");
		this.blink = nbt.getInteger("blink");
		this.radius = nbt.getFloat("radius");
		this.isOn = nbt.getBoolean("isOn");

		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		storage.writeToNBT(nbt);
		nbt.setInteger("health", health);
		nbt.setInteger("maxHealth", maxHealth);
		nbt.setInteger("cooldown", cooldown);
		nbt.setInteger("blink", blink);
		nbt.setFloat("radius", radius);
		nbt.setBoolean("isOn", isOn);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	public int getHealthScaled(int i) {
		return (health * i) / maxHealth;
	}

	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			int rStack = 0;
			int hStack = 0;
			radius = 16;
			maxHealth = 100;

			if (slots[1] != null && slots[1].getItem() == ModItems.upgrade_radius) {
				rStack = slots[1].stackSize;
				radius += rStack * MainRegistry.upRange;
			}

			if (slots[2] != null && slots[2].getItem() == ModItems.upgrade_health) {
				hStack = slots[2].stackSize;
				maxHealth += hStack * MainRegistry.upHealth;
			}

			this.powerCons = MainRegistry.fieldBase + rStack * MainRegistry.fieldRange
					+ hStack * MainRegistry.fieldHealth;

			if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[0].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[0]));

				item.extractEnergy(slots[0], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + extract);
			}

			if (blink > 0) {
				blink--;
				color = 0xFF0000;
			} else {
				color = 0x5050FF;
			}
		}

		if (cooldown > 0) {
			cooldown--;
		} else {
			if (health < maxHealth)
				health += maxHealth / 100;

			if (health > maxHealth)
				health = maxHealth;
		}

		if (isOn && cooldown == 0 && health > 0 && storage.getEnergyStored() >= powerCons) {
			doField(radius);

			if (!worldObj.isRemote) {
				storage.setEnergyStored(storage.getEnergyStored() - powerCons);
			}
		} else {
			this.outside.clear();
			this.inside.clear();
		}

		if (!worldObj.isRemote) {
			if (storage.getEnergyStored() < powerCons)
				storage.setEnergyStored(0);
		}

		if (!worldObj.isRemote) {

			if(slots[0] != null && slots[0].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			PacketDispatcher.wrapper.sendToAll(new TEFFPacket(xCoord, yCoord, zCoord, radius, health, maxHealth,
					storage.getEnergyStored(), isOn, color, cooldown));
		}
	}

	private int impact(Entity e) {

		/*
		 * MainRegistry.logger.info("==============");
		 * MainRegistry.logger.info(e.toString());
		 * MainRegistry.logger.info(e.getClass().toString());
		 */
		
		if(e instanceof EntityRailgunBlast)
			return 1000000;
		
		if(e instanceof EntityMissileBaseSimple) {
			
			((EntityMissileBaseSimple)e).onForceImpact();
			
			if(((EntityMissileBaseSimple)e).getIsBreaching())
				return 1000000;
			else
				return 0;
		}

		double flanDMG = 0;

		try {

			Field bullettype = ReflectionHelper.findField(e.getClass(), "type");

			Object bulletO = bullettype.get(e);

			if (bulletO != null) {

				Class cl = bullettype.getType();

				Field fdmg = ReflectionHelper.findField(cl.cast(bulletO).getClass().getSuperclass(), "damageVsLiving");

				if (fdmg != null) {

					flanDMG = fdmg.getFloat(bulletO);
				}
			}

		} catch (Exception e1) {
		}

		if (flanDMG > 0) {
			return (int) (flanDMG * MainRegistry.flanmult);
		}

		if (isUnbreakable())
			return 0;

		double mass = Math.pow(e.height * e.width * e.width, MainRegistry.exWeight);
		double speed = Math.pow(getMotionWithFallback(e), MainRegistry.exSpeed);
		return (int) (mass * speed * MainRegistry.mult);
	}

	private double getMotionWithFallback(Entity e) {

		Vec3 v1 = Vec3.createVectorHelper(e.motionX, e.motionY, e.motionZ);
		Vec3 v2 = Vec3.createVectorHelper(e.posX - e.prevPosY, e.posY - e.prevPosY, e.posZ - e.prevPosZ);

		double s1 = v1.lengthVector();
		double s2 = v2.lengthVector();

		if (s1 == 0)
			return s2;

		if (s2 == 0)
			return s1;

		return Math.min(s1, s2);
	}

	private void damage(int ouch) {
		health -= ouch;

		if (ouch >= (this.maxHealth / 250D))
			blink = 5;

		if (health <= 0) {
			health = 0;
			cooldown = (int) (MainRegistry.baseCooldown + radius * MainRegistry.rangeCooldown);
		}
	}

	List<Entity> outside = new ArrayList();
	List<Entity> inside = new ArrayList();

	private void doField(float rad) {

		List<Entity> oLegacy = new ArrayList(outside);
		List<Entity> iLegacy = new ArrayList(inside);

		outside.clear();
		inside.clear();

		List<Object> list = worldObj.getEntitiesWithinAABBExcludingEntity(null,
				AxisAlignedBB.getBoundingBox(xCoord + 0.5 - (rad + 25), yCoord + 0.5 - (rad + 25),
						zCoord + 0.5 - (rad + 25), xCoord + 0.5 + (rad + 25), yCoord + 0.5 + (rad + 25),
						zCoord + 0.5 + (rad + 25)));

		for (Object o : list) {

			//deflects all entities        except players                  and ABMs
			if (o instanceof Entity && !(o instanceof EntityPlayer) && !(o instanceof EntityMissileAntiBallistic)) {
				Entity entity = (Entity) o;

				double dist = Math.sqrt(Math.pow(xCoord + 0.5 - entity.posX, 2)
						+ Math.pow(yCoord + 0.5 - entity.posY, 2) + Math.pow(zCoord + 0.5 - entity.posZ, 2));

				boolean out = dist > rad;

				// if the entity has not been registered yet
				if (!oLegacy.contains(entity) && !iLegacy.contains(entity)) {
					if (out) {
						outside.add(entity);
					} else {
						inside.add(entity);
					}

					// if the entity has been detected before
				} else {

					// if the entity has crossed inwards
					if (oLegacy.contains(entity) && !out) {
						Vec3 vec = Vec3.createVectorHelper(xCoord + 0.5 - entity.posX, yCoord + 0.5 - entity.posY,
								zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();

						double mx = -vec.xCoord * (rad + 1);
						double my = -vec.yCoord * (rad + 1);
						double mz = -vec.zCoord * (rad + 1);

						entity.setLocationAndAngles(xCoord + 0.5 + mx, yCoord + 0.5 + my, zCoord + 0.5 + mz, 0, 0);

						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2)
								+ Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * -mo;
						entity.motionY = vec.yCoord * -mo;
						entity.motionZ = vec.zCoord * -mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

						// worldObj.playSoundAtEntity(entity,
						// "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						outside.add(entity);

						if (!worldObj.isRemote) {
							this.damage(this.impact(entity));
						}

					} else

					// if the entity has crossed outwards
					if (iLegacy.contains(entity) && out) {
						Vec3 vec = Vec3.createVectorHelper(xCoord + 0.5 - entity.posX, yCoord + 0.5 - entity.posY,
								zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();

						double mx = -vec.xCoord * (rad - 1);
						double my = -vec.yCoord * (rad - 1);
						double mz = -vec.zCoord * (rad - 1);

						entity.setLocationAndAngles(xCoord + 0.5 + mx, yCoord + 0.5 + my, zCoord + 0.5 + mz, 0, 0);

						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2)
								+ Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * mo;
						entity.motionY = vec.yCoord * mo;
						entity.motionZ = vec.zCoord * mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

						// worldObj.playSoundAtEntity(entity,
						// "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						inside.add(entity);

						if (!worldObj.isRemote) {
							this.damage(this.impact(entity));
						}

					} else {

						if (out) {
							outside.add(entity);
						} else {
							inside.add(entity);
						}
					}
				}
			}
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

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	private boolean isUnbreakable() {
		return (slots[2] != null && slots[2].getItem() == ModItems.upgrade_bedrock);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		double toSend = Math.min(storage.getEnergyStored(), storage.getMaxExtract());

		if (!simulate) {
			storage.setEnergyStored(storage.getEnergyStored() - (int) Math.round(toSend));
		}

		return (int) Math.round(toSend);
	}
}
