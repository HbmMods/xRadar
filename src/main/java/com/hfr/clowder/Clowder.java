package com.hfr.clowder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.data.ClowderData;
import com.hfr.tileentity.TileEntityProp;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//it's like a faction
//but with cats!
public class Clowder {

	public String name;
	public String motd;
	public ClowderFlag flag;
	public int color;
	
	public int homeX;
	public int homeY;
	public int homeZ;
	public HashMap<String, int[]> warps = new HashMap();
	
	public String leader;
	public HashMap<String, Long> members = new HashMap();
	public Set<String> applications = new HashSet();
	
	public static List<Clowder> clowders = new ArrayList();
	public static HashMap<String, Clowder> inverseMap = new HashMap();
	public static HashSet<String> retreating = new HashSet();

	//because we can't have ANYTHING nice
	private float prestige = 0;
	private float prestigeGen = 0;
	private float prestigeReq = 0;

	public static final float tentRate = 0.1F;
	public static final float statueRate = 0.5F;
	public static final float flagRate = 0.1F;
	public static final float flagReq = 1.0F;
	
	public boolean addMember(World world, String name) {
		
		if(world.getPlayerEntityByName(name) == null)
			return false;
		
		if(inverseMap.containsKey(name) || members.get(name) != null)
			return false;
		
		members.put(name, time());
		inverseMap.put(name, this);
		
		ClowderData.getData(world).markDirty();
		
		return true;
	}
	
	public boolean removeMember(World world, String name) {

		if(!inverseMap.containsKey(name) && members.get(name) == null)
			return false;
		
		members.remove(name);
		inverseMap.remove(name);

		ClowderData.getData(world).markDirty();
		
		return true;
	}
	
	public boolean transferOwnership(EntityPlayer player) {
		
		String key = player.getDisplayName();

		if(members.get(key) == null)
			return false;
		
		leader = key;
		ClowderData.getData(player.worldObj).markDirty();
		
		return true;
	}
	
	public void setHome(double x, double y, double z, EntityPlayer player) {
		
		this.homeX = (int)x;
		this.homeY = (int)y;
		this.homeZ = (int)z;
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public void rename(String name, EntityPlayer player) {
		
		this.name = name;
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public void setMotd(String motd, EntityPlayer player) {
		
		this.motd = motd;
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public void setColor(int color, EntityPlayer player) {
		
		this.color = color;
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public void save(World world) {
		
		if(world == null)
			return;
		ClowderData.getData(world).markDirty();
	}
	
	//0 - created
	//1 - not home
	//2 - no tent
	public int tryAddWarp(EntityPlayer player, int x, int y, int z, String name) {
		
		World world = player.worldObj;
		
		if(!ClowderTerritory.isPlayerHome(player))
			return 1;
		
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		for(int i = 2; i <= 5; i++) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			
			Block block = world.getBlock(x + dir.offsetX * 2, y, z + dir.offsetZ * 2);
			
			if(block == ModBlocks.tp_tent) {
				
				int[] pos = ((BlockDummyable)ModBlocks.tp_tent).findCore(world, x + dir.offsetX * 2, y, z + dir.offsetZ * 2);
				
				if(pos != null) {
					
					TileEntityProp tent = (TileEntityProp)world.getTileEntity(pos[0], pos[1], pos[2]);
					
					if(tent.warp.isEmpty() && tent.operational()) {

						tent.warp = name;
						
						clowder.warps.put(name, new int[] {x, y, z});
						
						ClowderData.getData(world).markDirty();
						return 0;
					}
				}
			}
		}
		
		return 2;
	}
	
	public boolean isOwner(EntityPlayer player) {
		
		String key = player.getDisplayName();
		
		return this.leader.equals(key);
	}
	
	public boolean disbandClowder(EntityPlayer player) {
		
		if(!isOwner(player))
			return false;
		
		clowders.remove(this);
		recalculateIMap();
		this.leader = "";
		
		ClowderData.getData(player.worldObj).markDirty();
		
		return true;
		
	}
	
	public boolean disbandClowder(World world) {
		
		clowders.remove(this);
		recalculateIMap();
		this.leader = "";
		
		ClowderData.getData(world).markDirty();
		
		return true;
		
	}
	
	public boolean valid() {
		return this.leader != "";
	}
	
	public boolean isRaidable() {
		
		int online = 0;
		
		for(String s : this.members.keySet()) {
			
			Long l = this.members.get(s);
			
			if(l > System.currentTimeMillis())
				online++;
		}
		
		int percent = online * 100 / this.members.size();
		
		return percent > 33;
	}
	
	public String round(float f) {
		
		return "" + Math.floor(f * 10D) / 10D;
	}
	
	public float getPrestige() {
		return prestige;
	}
	
	public float getPrestigeGen() {
		return prestigeGen;
	}
	
	public float getPrestigeReq() {
		return prestigeReq;
	}
	
	public void addPrestige(float f, World world) {
		prestige += f;
		this.save(world);
	}
	
	public void addPrestigeGen(float f, World world) {
		prestigeGen += f;
		this.save(world);
	}
	
	public void addPrestigeReq(float f, World world) {
		prestigeReq += f;
		this.save(world);
	}
	
	public void saveClowder(int i, NBTTagCompound nbt) {
		nbt.setString(i + "_name", this.name);
		nbt.setString(i + "_motd", this.motd);
		nbt.setInteger(i + "_flag", this.flag.ordinal());
		nbt.setInteger(i + "_color", this.color);
		nbt.setInteger(i + "_homeX", this.homeX);
		nbt.setInteger(i + "_homeY", this.homeY);
		nbt.setInteger(i + "_homeZ", this.homeZ);
		nbt.setFloat(i + "_prestige", this.prestige);
		nbt.setFloat(i + "_prestigeGen", this.prestigeGen);
		nbt.setFloat(i + "_prestigeReq", this.prestigeReq);

		nbt.setString(i + "_leader", this.leader);
		nbt.setInteger(i + "_members", this.members.size());
		nbt.setInteger(i + "_warps", this.warps.size());
		
		/// SAVE MEMBERS ///
		for(int j = 0; j < this.members.keySet().size(); j++)
			nbt.setString(i + "_" + j, (String) this.members.keySet().toArray()[j]);
		
		/// SAVE WARPS ///
		for(int j = 0; j < this.warps.keySet().size(); j++) {

			String name = (String) this.warps.keySet().toArray()[j];
			int[] coords = this.warps.get(name);

			nbt.setString(i + "_" + j + "_name", name);
			nbt.setInteger(i + "_" + j + "_x", coords[0]);
			nbt.setInteger(i + "_" + j + "_y", coords[1]);
			nbt.setInteger(i + "_" + j + "_z", coords[2]);
		}
	}
	
	public static Clowder loadClowder(int i, NBTTagCompound nbt) {
		
		Clowder c = new Clowder();
		c.name = nbt.getString(i + "_name");
		c.motd = nbt.getString(i + "_motd");
		c.flag = ClowderFlag.values()[nbt.getInteger(i + "_flag")];
		c.color = nbt.getInteger(i + "_color");
		c.homeX = nbt.getInteger(i + "_homeX");
		c.homeY = nbt.getInteger(i + "_homeY");
		c.homeZ = nbt.getInteger(i + "_homeZ");
		c.prestige = nbt.getFloat(i + "_prestige");
		c.prestigeGen = nbt.getFloat(i + "_prestigeGen");
		c.prestigeReq = nbt.getFloat(i + "_prestigeReq");

		c.leader = nbt.getString(i + "_leader");
		int count = nbt.getInteger(i + "_members");
		int cwarp = nbt.getInteger(i + "_warps");
		
		for(int j = 0; j < count; j++)
			c.members.put(nbt.getString(i + "_" + j), time());
		
		for(int j = 0; j < cwarp; j++) {
			
			String name = nbt.getString(i + "_" + j + "_name");
			int[] coord = new int[] {
					nbt.getInteger(i + "_" + j + "_x"),
					nbt.getInteger(i + "_" + j + "_y"),
					nbt.getInteger(i + "_" + j + "_z")
			};
			
			c.warps.put(name, coord);
		}
		
		return c;
	}
	
	public void notifyLeader(World world, ChatComponentText message) {

		notifyPlayer(world, this.leader, message);
	}
	
	public void notifyAll(World world, ChatComponentText message) {

		for(String player : this.members.keySet()) {
			notifyPlayer(world, player, message);
		}
	}
	
	public void notifyPlayer(World world, String player, ChatComponentText message) {

		EntityPlayer notif = world.getPlayerEntityByName(player);
		
		if(notif != null) {
			notif.addChatMessage(message);
		}
	}
	
	public void notifyCapture(World world, int x, int z, String type) {

		notifyAll(world, new ChatComponentText(EnumChatFormatting.RED + "One of your " + type + " at X:" + x + "/Z:" + z + " is under attack!"));
	}
	
	/// GLOBAL METHODS ///
	public static void recalculateIMap() {
		
		inverseMap.clear();
		
		for(Clowder clowder : clowders) {
			for(String member : clowder.members.keySet()) {
				inverseMap.put(member, clowder);
			}
		}
	}
	
	public static void readFromNBT(NBTTagCompound nbt) {
		
		clowders.clear();
		
		int count = nbt.getInteger("clowderCount");
		
		for(int i = 0; i < count; i++)
			clowders.add(loadClowder(i, nbt));
		
		recalculateIMap();
	}
	
	public static void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("clowderCount", clowders.size());

		for(int i = 0; i < clowders.size(); i++)
			clowders.get(i).saveClowder(i, nbt);
	}
	
	public static Clowder getClowderFromPlayer(EntityPlayer player) {
		
		String key = player.getDisplayName();
		
		return inverseMap.get(key);
	}
	
	public static Clowder getClowderFromName(String name) {

		name = name.toLowerCase();
		
		for(Clowder clowder : clowders) {
			if(clowder.name.toLowerCase().equals(name))
				return clowder;
		}
		
		return null;
	}
	
	public static void createClowder(EntityPlayer player, String name) {

		String leader = player.getDisplayName();
		
		Clowder c = new Clowder();
		c.name = name;
		c.leader = leader;
		c.members.put(leader, time());
		c.color = player.getRNG().nextInt(0x1000000);
		c.setHome(player.posX, player.posY, player.posZ, player);
		
		c.motd = "Message of the day!";
		c.flag = ClowderFlag.TRICOLOR;
		
		c.prestige = 1;
		
		clowders.add(c);
		inverseMap.put(leader, c);
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public static long time() {
		return System.currentTimeMillis();
	}
}
