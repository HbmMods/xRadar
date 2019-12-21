package com.hfr.clowder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hfr.data.ClowderData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

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
	
	public String leader;
	public HashMap<String, Long> members = new HashMap();
	public Set<String> applications = new HashSet();
	
	public static List<Clowder> clowders = new ArrayList();
	public static HashMap<String, Clowder> inverseMap = new HashMap();
	public static HashSet<String> retreating = new HashSet();
	
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
	
	public void saveClowder(int i, NBTTagCompound nbt) {
		nbt.setString(i + "_name", this.name);
		nbt.setString(i + "_motd", this.motd);
		nbt.setInteger(i + "_flag", this.flag.ordinal());
		nbt.setInteger(i + "_color", this.color);
		nbt.setInteger(i + "_homeX", this.homeX);
		nbt.setInteger(i + "_homeY", this.homeY);
		nbt.setInteger(i + "_homeZ", this.homeZ);

		nbt.setString(i + "_leader", this.leader);
		nbt.setInteger(i + "_members", this.members.size());
		
		for(int j = 0; j < this.members.keySet().size(); j++) {

			nbt.setString(i + "_" + j, (String) this.members.keySet().toArray()[j]);
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

		c.leader = nbt.getString(i + "_leader");
		int count = nbt.getInteger(i + "_members");
		
		for(int j = 0; j < count; j++) {
			
			c.members.put(nbt.getString(i + "_" + j), time());
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

		for(Clowder clowder : clowders) {
			if(clowder.name.equals(name))
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
		
		clowders.add(c);
		inverseMap.put(leader, c);
		
		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public static long time() {
		return System.currentTimeMillis();
	}
}
