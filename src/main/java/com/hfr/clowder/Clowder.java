package com.hfr.clowder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.command.CommandClowder;
import com.hfr.data.ClowderData;
import com.hfr.data.MarketData.Offer;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.prop.TileEntityProp;

import cpw.mods.fml.common.Loader;
import hardcorequesting.Team;
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

	public static HashSet<Integer> colours = new HashSet<Integer>();

	private static float prestigeCap = 1000F;
	private static float prestigeGenCap = 500F;
	
	public String uuid;
	public String name;
	public String motd;
	public ClowderFlag flag;
	public int color;
	public boolean canChangeColour = true;
	public boolean canDisband = false;

	public int homeX;
	public int homeY;
	public int homeZ;
	public HashMap<String, int[]> warps = new HashMap();
	
	//tracks how many times the clowder has bought from this market option
	//non-persistent so it won't save when the server stops
	public HashMap<Offer, Integer> offerHistory = new HashMap();

	//for limited only x bonus minutes from capping chunks
	//Limit is found in the MainRegistry class
	public int bonusPoints = 0;
	
	//for war delcaration system labjac punjab
	public Clowder enemy = null;
	public String enemyS = "nobody2584369"; 
	
	//for paying tribute
	public Clowder suzerain = null;
	public Set<String> dickSuckers = new HashSet();
	public String suzerainS = "nobody2584369"; 
	
	//for being a bitch
	public boolean bitch = false;
	
	//for totalen krieg
	public boolean totalenKrieg = false;
	//for total peace
	public boolean paxBritannica = false;
	
	//debug to force offlines to be online
	public static boolean forceOnline = false;
	
	//for poorly coded treaty system
	public String treaty1 = "nobody2584369";
	public String treaty2 = "nobody2584369"; 
	public String treaty3 = "nobody2584369"; 
	public String treaty4 = "nobody2584369"; 
	public String treaty5 = "nobody2584369"; 
	public String treaty6 = "nobody2584369"; 
	public String treaty7 = "nobody2584369";
	
	public float treatyTime1 = 0;
	public float treatyTime2 = 0;
	public float treatyTime3 = 0;
	public float treatyTime4 = 0;
	public float treatyTime5 = 0;
	public float treatyTime6 = 0;
	public float treatyTime7 = 0;

	//c retreat tempban
	public float retreatBan = 0;
		
	//for tributary/bitch stuff
	private float peaceTreaty = 0;
	
	//to prevent sethome abuse
	public float sethomeDelay = 0;
		
	//for pussying out system
	public boolean targeted = false;
		
	//for vassal wars not targeting master
	public boolean vassalTarget = false;
		
	//for preventing retreat during insta-raid time
	public boolean hungaryTarget = false;
		
	//for one-time use war extension for attacker
	public boolean overtime = false;
		
	public String leader;
	public Set<String> officers = new HashSet();
	public HashMap<String, Long> members = new HashMap();
	public Set<String> applications = new HashSet();
	public int flags = 0;

	//shitty ally system
	public Set<String> potentialFriends = new HashSet();
	public HashMap<Clowder, Long> allies = new HashMap();
	public HashMap<String, Long> alliesS = new HashMap(); //string version since NBT cringe memory gay
	public int allyWarpX;
	public int allyWarpY;
	public int allyWarpZ;
	
	public static List<Clowder> clowders = new ArrayList();
	public static HashMap<String, Clowder> inverseMap = new HashMap();
	public static HashSet<String> retreating = new HashSet();
	public static HashMap<Long, ScheduledTeleport> teleports = new HashMap();



	// because we can't have ANYTHING nice
	private float prestige = 0;
	private float prestigeGen = 0;
	private float prestigeReq = 0;
	private float fabricateTime = 0;
	private float canDeclareTime = 0;
	private float warTime = 0;

	public static final float tentRate = 0.1F;
	public static final float statueRate = 0.5F;
	public static final float flagRate = 0.1F;
	public static final float flagReq = 1.0F;

	public boolean markTreaty(EntityPlayer player, String Attacker, String Target) {

		if(getClowderFromName(Attacker) == null || getClowderFromName(Target) == null)
			return false;
		
		boolean puppy = true;
		
		String RealTarget = Target;
		if (getClowderFromName(Target)!= null && getClowderFromName(Target).suzerain != null)
			RealTarget = getClowderFromName(Target).suzerain.name;
		
		//check if there is already a treaty. if so cancel fabrication
		if(getClowderFromName(getClowderFromName(Attacker).treaty1) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime1 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty1");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty2) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime2 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty2");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty3) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime3 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty3");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty4) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime4 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty4");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty5) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime5 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty5");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty6) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime6 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty6");
			return false;
		}
		else if(getClowderFromName(getClowderFromName(Attacker).treaty7) == getClowderFromName(RealTarget))
		{
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString() + "You tried to target " + RealTarget +" recently, try again in " + getClowderFromName(Attacker).treatyTime7 + " minutes!"));
			puppy = false;
			//System.out.println("it tried to treaty7");
			return false;
		}
		else if (puppy = true)
		{
		neofabricate(player, Attacker, Target);
		}
		
		return puppy;
	}


	public boolean neofabricate(EntityPlayer player, String Attacker, String Target)
	{
		//System.out.println("it tried to neo fabricate");
		//for vassal kikery
		String Master = Target;
		if (getClowderFromName(Target)!= null && getClowderFromName(Target).suzerain != null)
			Master = getClowderFromName(Target).suzerain.name;
		
		
		if(treaty1 == "nobody2584369" && treaty1 != Master)
		{
			getClowderFromName(Attacker).treaty1 = Master;
			getClowderFromName(Attacker).treatyTime1 = 420;
			//System.out.println("it tried to treaty1 puppy");
		}
		else if(treaty2 == "nobody2584369" && treaty2 != Master)
		{
			getClowderFromName(Attacker).treaty2 = Master;
			getClowderFromName(Attacker).treatyTime2 = 420;
		}
		else if(treaty3 == "nobody2584369" && treaty3 != Master)
		{
			getClowderFromName(Attacker).treaty3 = Master;
			getClowderFromName(Attacker).treatyTime3 = 420;
		}
		else if(treaty4 == "nobody2584369" && treaty4 != Master)
		{
			getClowderFromName(Attacker).treaty4 = Master;
			getClowderFromName(Attacker).treatyTime4 = 420;
		}
		else if(treaty5 == "nobody2584369" && treaty5 != Master)
		{
			getClowderFromName(Attacker).treaty5 = Master;
			getClowderFromName(Attacker).treatyTime5 = 420;
		}
		else if(treaty6 == "nobody2584369" && treaty6 != Master)
		{
			getClowderFromName(Attacker).treaty6 = Master;
			getClowderFromName(Attacker).treatyTime6 = 420;
		}
		else if(treaty7 == "nobody2584369" && treaty7 != Master)
		{
			getClowderFromName(Attacker).treaty7 = Master;
			getClowderFromName(Attacker).treatyTime7 = 420;
		}
		else return false;
		
		
		if(treaty1 != "nobody2584369" && treaty2 != "nobody2584369" && treaty3 != "nobody2584369" && treaty4 != "nobody2584369" && treaty5 != "nobody2584369" && treaty6 != "nobody2584369" && treaty7 != "nobody2584369")
			return false;
		
		
		//do all the dirty work here, it doesnt like it in command clowder!!!
		
		
		//for targeting a vassal
		String trueTarget = Target;
		
		if(getClowderFromName(Target).suzerain != null)
		{
			this.vassalTarget = true;
			trueTarget = getClowderFromName(Target).suzerain.name;		
			getClowderFromName(Target).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "WARNING! " + getClowderFromName(Attacker).name + " is preparing to declare war on your master to target us!"));
			getClowderFromName(trueTarget).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "WARNING! " + getClowderFromName(Attacker).name + " is preparing to declare war on you to target one of your vassals!"));
			getClowderFromName(Attacker).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "Since our target is a vassal, we will declare war against their master!"));
			getClowderFromName(Attacker).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "This will let us kill vassals even if the master is offline!"));
			getClowderFromName(Attacker).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "However if the master is offline we cannot take their master's land!"));
		}
		
		
		
		
		//System.out.println("it did the dirty work");
			player.addChatMessage(new ChatComponentText(CommandClowder.TITLE + "War fabrication process has begun!"));
			getClowderFromName(Attacker).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "Allah be with us! We will soon vanquish " + getClowderFromName(trueTarget).name + "! Use /c info to see remaining time!"));
	player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiFabrication", 2.0F, 1.0F);
//	Minecraft.getMinecraft().thePlayer.playSound("hfr:item.hoiFabrication", 0.5F, 1.0F);
	getClowderFromName(trueTarget).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "WARNING! " + getClowderFromName(Attacker).name + " is preparing to attack you or your vassals!"));
	getClowderFromName(trueTarget).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "If we do not use /c retreat within the next 29 minutes, "));
	getClowderFromName(trueTarget).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + getClowderFromName(Attacker).name + " will gain the ability to instantly declare "));
	getClowderFromName(trueTarget).notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "war against us if we are 'online' for FOUR hours! "));
	
	//prestige price toll paid
	getClowderFromName(Attacker).addPrestige(-MainRegistry.fabricateCost, player.worldObj);
	
	//for now 3 minute fabrication for testing - change to bigger number later allah bookmark
	getClowderFromName(Attacker).addFabricateTime(30, player.worldObj);
	//marks your target as your faction's enemy
	getClowderFromName(Attacker).enemy = getClowderFromName(trueTarget);
	//hopefully server will memorize your enemy now
	getClowderFromName(Attacker).markEnemy(player.worldObj, getClowderFromName(Attacker).name, getClowderFromName(trueTarget).name);
	//mark the victim as being targeted unless they pussy out
	if (!getClowderFromName(trueTarget).targeted && getClowderFromName(trueTarget).isRaidable())
		getClowderFromName(trueTarget).targeted = true;
	
	//poorly coded treaty system, memorize the victim in first available treaty slot
	//enjoy yanderedev tier code to check all 7 treaty slots and try adding
	//actually nevermind i moved the naming yanderedev code to a void in clowder.java	
	ClowderData.getData(player.worldObj).markDirty();

	return true;
		}
	
	//i guess you have to do this horseshit for the save to work
	public  void retreatBanHammer(EntityPlayer player, String Clowder, int number) {

		if(getClowderFromName(Clowder) != null )
		{
		this.retreatBan = (int)number;
		ClowderData.getData(player.worldObj).markDirty();
		}
		//this.save(player.worldObj);
		
		
	}
	
	
	public void addretreatBan(float f, World world) {
		retreatBan += f;

		this.save(world);
	}
	
	//minute timer for war declarations and war timer
	public static void updateWars(World world) {

		for(Clowder clowder : clowders) {
			
			//actually nvm send the message every 3 minutes
			boolean isDivisibleBy5fab = clowder.getFabricatetime() % 5 == 0;
			
			
			//initialize memory again just to be safe every minute
			{			
			//memory memes so i can save clowders as strings for enemy and suzerain shit
			if(clowder.suzerainS == "nobody2584369")
				clowder.suzerain = null;
			else
			clowder.suzerain = getClowderFromName(clowder.suzerainS);
			
			if(clowder.enemyS == "nobody2584369")
				clowder.enemy = null;
			else
			clowder.enemy = getClowderFromName(clowder.enemyS);
			}
			
			if(clowder.treaty1 == "")
			{
				clowder.treaty1 = "nobody2584369";
				clowder.treatyTime1 = 0;
			}
			if(clowder.treaty2 == "")
			{
				clowder.treaty2 = "nobody2584369";
				clowder.treatyTime2 = 0;
			}
			if(clowder.treaty3 == "")
			{
				clowder.treaty3 = "nobody2584369";
				clowder.treatyTime3 = 0;
			}
			if(clowder.treaty4 == "")
			{
				clowder.treaty4 = "nobody2584369";
				clowder.treatyTime4 = 0;
			}
			if(clowder.treaty5 == "")
			{
				clowder.treaty5 = "nobody2584369";
				clowder.treatyTime5 = 0;
			}
			if(clowder.treaty6 == "")
			{
				clowder.treaty6 = "nobody2584369";
				clowder.treatyTime6 = 0;
			}
			if(clowder.treaty7 == "")
			{
				clowder.treaty7 = "nobody2584369";
				clowder.treatyTime7 = 0;
			}
			
			
			if(clowder.suzerain==null)
				clowder.bitch = false;
			//end of initialize again
			
			//peacetreaty timer tick (for revolt, not the fabricate one)
			if(clowder.peaceTreaty > 0)
				clowder.addPeaceTreaty(-1, world);
			else
				clowder.peaceTreaty = 0;
			
			if(clowder.sethomeDelay > 0)
				clowder.addSethomeDelay(-1, world);
			else
				clowder.sethomeDelay = 0;
			
			/*
			if(clowder.peaceTreaty > 60)
				clowder.peaceTreaty = 60;
				*/
			
			
			//retreat tempban
			if (clowder.retreatBan > 0)
				clowder.retreatBan --;
			
			
			//poorly coded anti fabrication bullying treaty
			//timers
			if (clowder.treatyTime1 > 0)
				clowder.treatyTime1 --;
			if (clowder.treatyTime2 > 0)
				clowder.treatyTime2 --;
			if (clowder.treatyTime3 > 0)
				clowder.treatyTime3 --;
			if (clowder.treatyTime4 > 0)
				clowder.treatyTime4 --;
			if (clowder.treatyTime5 > 0)
				clowder.treatyTime5 --;
			if (clowder.treatyTime6 > 0)
				clowder.treatyTime6 --;
			if (clowder.treatyTime7 > 0)
				clowder.treatyTime7 --;
			
			//if timer done, "null" the treaties
			if (clowder.treatyTime1 <= 1 && clowder.treaty1 != "nobody2584369")
				clowder.treaty1 = "nobody2584369";
			if (clowder.treatyTime2 <= 1 && clowder.treaty2 != "nobody2584369")
				clowder.treaty2 = "nobody2584369";
			if (clowder.treatyTime3 <= 1 && clowder.treaty3 != "nobody2584369")
				clowder.treaty3 = "nobody2584369";
			if (clowder.treatyTime4 <= 1 && clowder.treaty4 != "nobody2584369")
				clowder.treaty4 = "nobody2584369";
			if (clowder.treatyTime5 <= 1 && clowder.treaty5 != "nobody2584369")
				clowder.treaty5 = "nobody2584369";
			if (clowder.treatyTime6 <= 1 && clowder.treaty6 != "nobody2584369")
				clowder.treaty6 = "nobody2584369";
			if (clowder.treatyTime7 <= 1 && clowder.treaty7 != "nobody2584369")
				clowder.treaty7 = "nobody2584369";
			
			clowder.save(world);
			
			
			
			if(clowder.getFabricatetime() > -1 && (clowder.enemy)!=null && clowder.getWartime() <= 0 && clowder.getCanDeclareTime() <= 0 && clowder.enemy != clowder.suzerain)
			{
			//float fabricateTime = clowder.getFabricatetime();
			
			//fabricateTime *= (float)Math.pow(0.99, clowder.getFabricatetime());

				//spam message per minute during last 1 min
				if(clowder.getFabricatetime()<5 && clowder.getFabricatetime()>2 && clowder.getCanDeclareTime() == 0 && clowder.getWartime() == 0 && clowder.enemy != clowder.suzerain)
				{
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "War declaration will be ready in " + (int)(clowder.getFabricatetime()-2) + " minutes!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " will be able to attack us in " + (int)(clowder.getFabricatetime()-2) + " minute(s)!"));
				}
				
				
			clowder.addFabricateTime(-1, world);
			}
			
			//for revolt waiting time
			if(clowder.getFabricatetime() > 0 && (clowder.enemy)!=null && clowder.getWartime() <= 0 && clowder.getCanDeclareTime() <= 0 && clowder.enemy == clowder.suzerain)
			{
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If our oppressors don't strike back, we will be free in " + (int)(clowder.getFabricatetime()-1) + " minutes!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.ERROR + clowder.name + " will break free from us in " + (int)(clowder.getFabricatetime()-1) + " minute(s)!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + " Use /c suppress " + clowder.name + " to forcefully stop them! (Will start civil-war)"));
				clowder.addFabricateTime(-1, world);
			}
			
			
			
			
			//handling vassal notifications
			if(clowder.suzerain != null)
				{
				if(clowder.suzerain.targeted)
				{
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "An enemy has war goals against our master!"));
					if(clowder.bitch)
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If they go to war, enemies may bomb our land!"));
					else
						clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If they go to war, enemies may take our land!"));
						
				}
				}
			
			
			if (isDivisibleBy5fab)
			{
			//fabricate timer countdown per minute
			if(clowder.valid()) 
			{
				if(clowder.getFabricatetime() > -1 && (clowder.enemy)!=null && clowder.getCanDeclareTime() == 0 && clowder.getWartime() == 0)
				{
				//float fabricateTime = clowder.getFabricatetime();
				
				//fabricateTime *= (float)Math.pow(0.99, clowder.getFabricatetime());
				
					//dont hsow the message if its a revolt
				if (clowder.enemy != clowder.suzerain)
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " will be able to attack us in " + (int)(clowder.getFabricatetime()-1) + " minute(s)!"));
				//if there is enough time to retreat, also suggest retreat to victim. not for revolts
				if(clowder.getFabricatetime() > 10 && clowder.enemy != clowder.suzerain)
				{
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Officers can use /c retreat to try to cancel this attack!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Retreating makes you the aggressor's bitch, so choose wisely!"));
				}
			}
			}
			}
			
			if(clowder.enemy == null)
			{
clowder.pussy(world);
			}
			
			
			
			//if fabricate time is 1 minute left be ready for war      added extra enemy null check to prevent random declarations ready generating for free
			if(clowder.valid() && clowder.getFabricatetime() > -1 && clowder.getFabricatetime() <= 1.5 && clowder.enemy != null && clowder.getCanDeclareTime() == 0 && clowder.getWartime() == 0)
			{
				
				if(clowder.enemy != clowder.suzerain)
				{
				//10 minutes you can instantly start war and fabricate timer is set to 0, nvm changed to 3 hours per hungary suggestion
				clowder.endFabricateTime(world);
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "War declaration is ready! Officers can /c declare for the next 4 hours!"));
				}
				//for revolt fabrication time end sending unltimatum
				else if (clowder.enemy == clowder.suzerain && clowder.getFabricatetime()==0 && clowder.getWartime() == 0)
				{
					//rebels break free peacefully and win some prestige
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Our former masters lacked the will to oppose us! We have peacefully broken free of their grasp!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.INFO + "We also get back some prestige!"));
					clowder.addPrestige(MainRegistry.fabricateCost*1.75f, world);
					//former master suffers
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " is no longer our vassal!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.ERROR + "This shameful setback will cost us some prestige..."));
					clowder.enemy.addPrestige(-MainRegistry.fabricateCost, world);
					clowder.breakFree(world);
					
				}
					
					
			}
			
			//when war timer ends
			if(clowder.valid() && clowder.getWartime() <= 1.5 && (clowder.enemy)!=null && clowder.getCanDeclareTime() == 0 && clowder.getFabricatetime() == 0)
			{		
				if(clowder.enemy != clowder.suzerain)
				{
					
					//end of war treaty
					if(clowder.treaty1 == "nobody2584369" && clowder.treaty1 != clowder.enemy.name)
					{
						clowder.treaty1 = clowder.enemy.name;
						clowder.treatyTime1 = 90;
						//System.out.println("it tried to treaty1 puppy");
					}
					else if(clowder.treaty2 == "nobody2584369" && clowder.treaty2 != clowder.enemy.name)
					{
						clowder.treaty2 = clowder.enemy.name;
						clowder.treatyTime2 = 90;
					}
					else if(clowder.treaty3 == "nobody2584369" && clowder.treaty3 != clowder.enemy.name)
					{
						clowder.treaty3 = clowder.enemy.name;
						clowder.treatyTime3 = 90;
					}
					else if(clowder.treaty4 == "nobody2584369" && clowder.treaty4 != clowder.enemy.name)
					{
						clowder.treaty4 = clowder.enemy.name;
						clowder.treatyTime4 = 90;
					}
					else if(clowder.treaty5 == "nobody2584369" && clowder.treaty5 != clowder.enemy.name)
					{
						clowder.treaty5 = clowder.enemy.name;
						clowder.treatyTime5 = 90;
					}
					else if(clowder.treaty6 == "nobody2584369" && clowder.treaty6 != clowder.enemy.name)
					{
						clowder.treaty6 = clowder.enemy.name;
						clowder.treatyTime6 = 90;
					}
					else if(clowder.treaty7 == "nobody2584369" && clowder.treaty7 != clowder.enemy.name)
					{
						clowder.treaty7 = clowder.enemy.name;
						clowder.treatyTime7 = 90;
					}
					
					
					
					
				//war ended
				clowder.endWarTime(world);
				
				//bitchify coward defender and take their bitches/vassals. checks if they retreated with the old retreatban timer
				if(clowder.enemy.retreatBan>0)
				{
				clowder.enemy.suzerain = clowder;
				clowder.enemy.suzerainS = clowder.name;
				clowder.enemy.bitch = true;
				clowder.enemy.notifyAll(world, new ChatComponentText("You are now " + clowder.name + "'s bitch!"));
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + clowder.enemy.name + " is now our bitch!"));
				//first bitched 12 hour treaty
				clowder.enemy.longPeace(world);
				for(Clowder bitches : clowder.enemy.clowders)
				{
					if(bitches.suzerain == clowder.enemy)
					{
						bitches.suzerain = clowder;
						bitches.suzerainS = clowder.name;
						bitches.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Our former master has been subjugated!"));
						bitches.notifyAll(world, new ChatComponentText("Our new master is " + clowder.name));
					}
						
				}
				}
				
				//bitchify coward attacker and take their bitches/vassals. old retreatban timer used to check if you were a coward
				if(clowder.retreatBan>0)
				{
				clowder.suzerain = clowder.enemy;
				clowder.suzerainS = clowder.enemy.name;
				clowder.bitch = true;
				clowder.notifyAll(world, new ChatComponentText("You are now " + clowder.enemy.name + "'s bitch!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + clowder.name + " is now our bitch!"));
				//first bitched 12 hour treaty
				clowder.longPeace(world);
				for(Clowder bitches : clowder.clowders)
				{
					if(bitches.suzerain == clowder)
					{
						bitches.suzerain = clowder.enemy;
						bitches.suzerainS = clowder.enemy.name;
						bitches.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Our former master has been subjugated!"));
						bitches.notifyAll(world, new ChatComponentText("Our new master is " + clowder.enemy.name));
					}
						
				}
				}
				
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "The war period has ended! Pack it up boys!"));
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.enemy.name + " also gets a 1.5 hour grace period."));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s war against us has ended!"));
				if(!clowder.enemy.bitch)
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "We also get a 1.5 hour treaty!"));
				//unmarks victim as being targeted
				clowder.enemy.targeted = false;
				//unmarks being a vassal targeting war
				clowder.vassalTarget = false;
				//unmarks victim as being the attacker's target
				clowder.enemy = null;
				clowder.enemyS = "nobody2584369";
				if(clowder.suzerain != null)
				{
				clowder.suzerain.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Our vassal, " + clowder.name + ", has finished their war!"));
				}
				//resets overtime
				clowder.overtime = false;
				//and reset bonus minutes
				clowder.bonusPoints = 0;
				}
				//for revolts
				else if (clowder.getWartime() == 1)
				{
					clowder.endWarTime(world);
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Our revolt has failed. We must pay extra reparations to our master."));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s revolt against us has failed! Time for reparations!"));
					//unmarks victim as being targeted
					clowder.enemy.targeted = false;
					//unmarks victim as being the attacker's target
					clowder.enemy = null;
					clowder.enemyS = "nobody2584369";
					//resets peace treaty to 3 hours
					clowder.threePeace(world);
					
					clowder.addPrestige(-MainRegistry.fabricateCost*2, world);
					
					//if victim can afford it, master gets double the declare price
					if(clowder.getPrestige() > MainRegistry.fabricateCost*2)
					clowder.suzerain.addPrestige(MainRegistry.fabricateCost*2, world);
					else
					{
						//prevent generating nonexistent prestige from poor victims
						clowder.suzerain.addPrestige(clowder.getPrestige(), world);
					}	
					clowder.save(world);
				}
			}
			
			//if master loses a revolt (war score went up to 60 minutes
			if(clowder.valid() && clowder.getWartime() >= 60 && clowder.enemy == clowder.suzerain && clowder.getCanDeclareTime() == 0 && clowder.getFabricatetime() == 0)
			{
				clowder.endWarTime(world);
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Our revolt has succeeded. We are now independent!"));
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "We also get back our prestige!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s revolt against us has cost us too much."));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "We have no choice but to let them leave the alliance. (for now)"));
				//unmarks victim as being targeted
				clowder.enemy.targeted = false;
				//unmarks victim as being the attacker's target
				clowder.enemy = null;
				clowder.enemyS = "nobody2584369";
				
				//get back prestige revolt cost
				clowder.addPrestige(MainRegistry.fabricateCost*2, world);
				//unbitches bitches
				clowder.bitch = false;
				clowder.suzerain = null;
				clowder.suzerainS = "nobody2584369";
				
				ClowderData.getData(world).markDirty();
			}
			
			
			
			//didnt declare war within the 10 minutes i mean 3 hours
			if(clowder.valid() && clowder.getCanDeclareTime() <= 1.5 && (clowder.enemy)!=null && clowder.getWartime() <= 0 && clowder.getFabricatetime() <= 0 && clowder.enemy != clowder.suzerain)
			{
				//you missed the 10 minutes
				//clowder.endDeclareTime(world);
				clowder.enemy.targeted = false;
				clowder.vassalTarget = false;
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s war fabrication against us has expired!"));
				clowder.pussy(world);
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Too late! War fabrication has expired!"));
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "We also get most of our prestige back!"));
				clowder.addPrestige(MainRegistry.fabricateCost*0.9f, world);
				
				//target is no longer enemy
				clowder.enemy = null;
				clowder.enemyS = "nobody2584369";
			}
			
			
			
			//war time ticker
			if(clowder.valid() && clowder.getWartime() > 0 && (clowder.enemy)!=null) {
				
				//float fabricateTime = clowder.getWartime();
				
				//fabricateTime *= (float)Math.pow(0.99, clowder.getFabricatetime());
				
				clowder.addWarTime(-1, world);
				
				//during a revolt, if master pussies out, rebels get war score (timer going up to 1 hour means rebel victory)    also dont tick up if peace treaty so no 10 minute free conquest revolt timers from newly bitched
				if(clowder.enemy == clowder.suzerain && !clowder.enemy.isRaidable() && clowder.peaceTreaty <= 0)
				{
					clowder.addWarTime(2, world);
				}
				
				
				//if its alst 5 minutes of war ping everyone every minute    nvm every minute because divisble by 5 broken by decimal minutes
				if (clowder.getWartime() > 0  && clowder.enemy != clowder.suzerain ) //&& clowder.getWartime() < 5 )
				{
					
					
					if (clowder.retreatBan == 0 && clowder.enemy.retreatBan == 0)
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "War will last for about " + clowder.getWartime() + " more minutes!"));
					//if a vassal target war, suggest enemy hands over a bitch
					if(clowder.vassalTarget)
					{
						clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "This war was meant to target one of our vassals!"));
						clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If we are offline, our land will be safe!"));
						//clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Use /c bitchpass <bitch_name> <enemy_name> to sacrifice a vassal!"));
					}
					if(clowder.enemy.isRaidable()) //no extending the bonus free kill time
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War can be extended by using '/c overtime' but if we take casualties, it will end faster!"));
					//boonus point remaining messages attacker view
					if(clowder.bonusPoints < MainRegistry.bonusPointLimit && clowder.enemy.isRaidable() && clowder.enemy.retreatBan <= 0 && clowder.retreatBan <= 0 && !clowder.enemy.bitch)
					{
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "We also get an extra-minute per each chunk we capture!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "We can get bonus minutes " + (MainRegistry.bonusPointLimit - clowder.bonusPoints) + " more times!"));
					}
					//normal enemy view messages
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s war against us will last for about " + clowder.getWartime() + " more minutes!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War may be extended if " + clowder.name + " uses '/c overtime', but we can speed up the war by killing their members!"));
					//boonus point remaining messages victim view
					if(clowder.bonusPoints < MainRegistry.bonusPointLimit && clowder.enemy.isRaidable() && clowder.enemy.retreatBan <= 0)
					{
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "If they take a chunk of land from us, war will last one minute longer!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "The enemy can get bonus minutes " + (MainRegistry.bonusPointLimit - clowder.bonusPoints) + " more times!"));
					}
					
					
					if (clowder.retreatBan > 0 || clowder.enemy.retreatBan > 0) //different messages if retreat bonus mode
					{
						clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Bonus free-conquest time will last " + clowder.getWartime() + " more minutes!"));
						clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Bonus free-conquest time will last " + clowder.getWartime() + " more minutes!"));
					}
					
					//handling vassal notifications
					for (Clowder vassals : clowder.clowders)
					{
					if(vassals.suzerain != null && vassals.suzerain == clowder.enemy)
					{
						vassals.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War against our master will last for about " + clowder.getWartime() + " more minutes!"));
						if(vassals.bitch && clowder.vassalTarget && clowder != vassals)
						{
							vassals.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Since we are a bitch, the enemy cannot capture our land!s"));
							vassals.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "However they can bomb us even if we are all offline!"));
							vassals.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "We must bomb them back and scare them into retreating!"));
						}
						
					}

						
					}
					
				}
				
				
				//war-score version for revolts
				else if (clowder.getWartime() > 0  && clowder.enemy == clowder.suzerain ) //&& clowder.getWartime() < 5 )
				{
					//rebel war goal is to make war score go up by killing master
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War-score timer is at " + clowder.getWartime() + " minutes!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If war-score timer goes up to 60, we win!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Every dead master is +3 war-score! If one of us die, -1 to war-score timer!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Timer also goes up if our masters are considered 'offline!' (check with /c info)"));

					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + clowder.name + "'s revolt against us will last for about " + clowder.getWartime() + " more minutes!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "Use /c release " + clowder.name + " to peacefully surrender!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If rebels kill any of us, timer goes up by 3 minutes! If a rebel dies, -1 minute!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If the timer gets up to one hour, they will become independent!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "If we are 'offline' (from too few of us being online), timer will tick backwards!"));
				}
				
				
				//ping everyone about war status every 5 minutes  nevermind glitches out if decimal minutes from casualties
				/*
				boolean isDivisibleBy5 = clowder.getWartime() % 5 == 0;
				
				if (isDivisibleBy5)
		        {
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "War will last for about " + clowder.getWartime() + " more minutes!"));
					clowder.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War can be extended by using (WIP) but if we take casualties, it will end faster!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + "'s war against us will last for about " + clowder.getWartime() + " more minutes!"));
					clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "War may be extended if " + clowder.name + " uses (WIP), but we can speed up the war by killing their members!"));
		        }
				*/
			}
			
			//can declare war time ticks down per minute
			if(clowder.valid() && clowder.getCanDeclareTime() > 0 && clowder.enemy != null) {
				
				//float canDeclareTime = clowder.getWartime();
				
				//fabricateTime *= (float)Math.pow(0.99, clowder.getFabricatetime());
				clowder.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + "Our officers have " + (int)(clowder.getCanDeclareTime()-1) + " minutes to officially declare war with /c declare!"));
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.CRITICAL + (clowder.name) + " can declare war against us for the next " + (int)(clowder.getCanDeclareTime()-1) + " minutes!"));
				//suggest a preemptive strike
				clowder.enemy.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "We can start the war now with /c preemptive " + (clowder.name) ));
				clowder.addCanDeclareTime(-1, world);
			}
			

			
			
		}
	}
	
	//for cancelling wars/fabrications
	public void pussy(World world) {
		fabricateTime = 0;
		canDeclareTime = 0;
		if(enemy != null)
		enemy.targeted = false;
		enemy = null;
		enemyS = "nobody2584369";
		warTime = 0;
		overtime = false;
		bonusPoints = 0;
		vassalTarget = false;
		this.save(world);
	}
	
	//memory memes initialize memory shit for labjac poorly coded stuff
	public static void initializeDiplomacy(World world) 
	{
		
		for(Clowder clowder : clowders)
		{
			
		//memory memes so i can save clowders as strings for enemy and suzerain shit
		if(clowder.suzerainS == "nobody2584369")
			clowder.suzerain = null;
		else
		clowder.suzerain = getClowderFromName(clowder.suzerainS);
		
		if(clowder.enemyS == "nobody2584369")
			clowder.enemy = null;
		else
		clowder.enemy = getClowderFromName(clowder.enemyS);
		
		if(clowder.treaty1 == "")
		{
			clowder.treaty1 = "nobody2584369";
			clowder.treatyTime1 = 0;
		}
		if(clowder.treaty2 == "")
		{
			clowder.treaty2 = "nobody2584369";
			clowder.treatyTime2 = 0;
		}
		if(clowder.treaty3 == "")
		{
			clowder.treaty3 = "nobody2584369";
			clowder.treatyTime3 = 0;
		}
		if(clowder.treaty4 == "")
		{
			clowder.treaty4 = "nobody2584369";
			clowder.treatyTime4 = 0;
		}
		if(clowder.treaty5 == "")
		{
			clowder.treaty5 = "nobody2584369";
			clowder.treatyTime5 = 0;
		}
		if(clowder.treaty6 == "")
		{
			clowder.treaty6 = "nobody2584369";
			clowder.treatyTime6 = 0;
		}
		if(clowder.treaty7 == "")
		{
			clowder.treaty7 = "nobody2584369";
			clowder.treatyTime7 = 0;
		}
		
		if(clowder.suzerain==null)
			clowder.bitch = false;
		
		
		//using shitty strings to trick NBT into being able to memorize your allies
		for (String friend : clowder.alliesS.keySet()) 
		{
			Clowder ally = getClowderFromName(friend);
			
			if (ally != null)
			{
			clowder.allies.put(ally, time());
			//System.out.println(ally.name + " added to ally list of " + clowder.name);
			}
		}
		
		}
		System.out.println("Initializing LabJac's poorly coded clowder diplomacy systems");
		
	}
	
	public void notifyCapture(World world, int x, int z, String type) {

		notifyAll(world, new ChatComponentText(EnumChatFormatting.RED + "One of your " + type + " at X:" + x + " / Z:" + z + " is under attack!"));
		notifyAll(world, new ChatComponentText(EnumChatFormatting.YELLOW + "If this is a defensive war, the enemy gained 1 minute of extra war-time from taking that chunk!"));
		
		if(!warps.isEmpty()) {
			
			double dist = Double.POSITIVE_INFINITY;
			String closest = "";
			
			for(String key : warps.keySet()) {
				
				int[] pos = warps.get(key);
				
				if(pos != null) {
					
					double d = Math.sqrt(Math.pow(x - pos[0], 2) + Math.pow(z - pos[2], 2));
					
					if(d < dist) {
						dist = d;
						closest = key;
					}
				}
			}

			notifyAll(world, new ChatComponentText(EnumChatFormatting.RED + "Your closest warp is " + closest + " (" + ((int)dist) + "m)"));
		}
	}
	
	public void addWarTime(float f, World world) {
		warTime += f;
		
		if(warTime < 0)
			warTime = 0F;
		
		this.save(world);
	}
	
	public void addFabricateTime(float f, World world) {
		fabricateTime += f;
		
		if(fabricateTime < 0)
			fabricateTime = 0F;
		
		this.save(world);
	}
	
	public void addCanDeclareTime(float f, World world) {
		canDeclareTime += f;
		
		if(canDeclareTime < 0)
			canDeclareTime = 0F;
		
		this.save(world);
	}
	
	//fabrication time ends, you get 10 minutes to declare war, nvm buffed to 4 hours so this period acts as a human temp-ban
	public void endFabricateTime(World world) {
		fabricateTime = 0;
		canDeclareTime = 241;
		
		this.save(world);
	}
	
	
	//void for vassal freedom
	public void breakFree(World world) {
		fabricateTime = 0;
		enemy = null;
		enemyS = "nobody2584369";
		suzerainS = "nobody2584369";
		suzerain = null;
		warTime = 0;
		
		
		
		this.save(world);
	}
	
	//declaration time ends, you missed the 10 minutes pussy
	public void endDeclareTime(World world) {
		fabricateTime = 0;
		canDeclareTime = 0;
		
		this.save(world);
	}
	
	//war time ends
	public void endWarTime(World world) {
		warTime = 0;
		
		this.save(world);
	}
	
	//peace treaty stuff so you cant instantly do vassal trickery
	public void addPeaceTreaty(float f, World world) {
		peaceTreaty += f;
		
		if(peaceTreaty < 0)
			peaceTreaty = 0;
		
		this.save(world);
	}
	
	//to prevent sethome spam / allypoint spam
	public void addSethomeDelay(float f, World world) {
		sethomeDelay += f;
		
		if(sethomeDelay < 0)
			sethomeDelay = 0;
		
		this.save(world);
	}
	
	//12 hour peace
	public void longPeace(World world) {
		peaceTreaty = 720;
	
		this.save(world);
	}
	
	//3 hour peace
	public void threePeace(World world) {
		peaceTreaty = 180;
	
		this.save(world);
	}
		
	public float getPeaceTreaty() {
		return peaceTreaty;
	}
	
	public String getDecoratedName() {
		
		String n = this.name.replace("_", " ").trim();

		if(bitch)
			n += " - Cowardly Bitch";
		else if(getPrestige() < 25)
			n += " - Unorganized Mob";
		else if(getPrestige() < 50)
			n += " - Clan";
		else if(getPrestige() < 75)
			n += " - Tribe";
		else if(getPrestige() < 100)
			n += " - Chiefdom";
		else if(getPrestige() < 125)
			n += " - City-State";
		else if(getPrestige() < 150)
			n += " - Sheikhdom";
		else if(getPrestige() < 250)
			n += " - Emirate";
		else if(getPrestige() < 500)
			n += " - Sultanate";
		else if(getPrestige() < 750)
			n += " - Empire";
		/*
		else if(getPrestige() < 1000)
			n += " - Caliphate";
		else if(getPrestige() < 10000)
			n += " - Caliphate";
		*/
		else
			n += " - Caliphate";
		
		return n;
	}
	
	public boolean vassalize(World world, String Master, String Vassal) {

		if(getClowderFromName(Master) == null || getClowderFromName(Vassal) == null)
			return false;
		
		
		
		getClowderFromName(Vassal).suzerainS = Master;
		getClowderFromName(Vassal).suzerain = getClowderFromName(Master);
		ClowderData.getData(world).markDirty();
		
		return true;
	}
	
	
	public boolean markEnemy(World world, String Attacker, String Target) {

		if(getClowderFromName(Attacker) == null || getClowderFromName(Target) == null)
			return false;
		
		
		
		getClowderFromName(Attacker).enemyS = Target;
		getClowderFromName(Attacker).enemy = getClowderFromName(Target);
		ClowderData.getData(world).markDirty();
		
		return true;
	}
	
	public boolean addAlly(World world, Clowder friend) {

		if (!friend.valid())
			return false;

		if (allies.get(friend) != null)
			return false;

		allies.put(friend, time());
		alliesS.put(friend.name, time()); //nbt compliant gay version
		//inverseMap.put(name, this); i dont know wtf the inversemap shit is

		ClowderData.getData(world).markDirty();

		return true;
	}
	
	
	public boolean addMember(World world, String name) {

		if (world.getPlayerEntityByName(name) == null)
			return false;

		if (inverseMap.containsKey(name) || members.get(name) != null)
			return false;

		
		members.put(name, time());
		inverseMap.put(name, this);

		ClowderData.getData(world).markDirty();

		return true;
	}

	public boolean removeMember(World world, String name) {

		if (!inverseMap.containsKey(name) && members.get(name) == null)
			return false;

		members.remove(name);
		officers.remove(name);
		inverseMap.remove(name);

		ClowderData.getData(world).markDirty();

		return true;
	}
	
	public boolean removeAlly(World world, String name) {
		Clowder formerAlly = getClowderFromName(name);
		if (allies.get(formerAlly) == null)
			return false;

		allies.remove(formerAlly);
		ClowderData.getData(world).markDirty();

		return true;
	}

	public void becomeVassal(Clowder vassal, EntityPlayer player, Clowder suzerain) {

		vassal.suzerain = suzerain;

		ClowderData.getData(player.worldObj).markDirty();
	}

	// war declare system
	public float getFabricatetime() {
		return fabricateTime;
	}

	public float getWartime() {
		return warTime;
	}

	public float getCanDeclareTime() {
		return canDeclareTime;
	}

	public boolean transferOwnership(World world, String key) {

		if (members.get(key) == null)
			return false;

		officers.remove(key);
		leader = key;
		ClowderData.getData(world).markDirty();

		return true;
	}

	public void promote(World world, String name) {

		if (!members.containsKey(name))
			return;

		officers.add(name);
		this.save(world);
	}

	public void demote(World world, String name) {

		if (!members.containsKey(name))
			return;

		officers.remove(name);
		this.save(world);
	}

	public int getPermLevel(String name) {

		if (this.leader.equals(name))
			return 3;

		if (this.officers.contains(name))
			return 2;

		if (this.members.get(name) != null)
			return 1;

		return 0;

	}
	
	
	public int getBitches(Clowder clowder) {

		int bitches = 0;

		for(Clowder everyone : clowder.clowders)
		{
			if (everyone.suzerain == clowder && everyone.bitch)
				bitches ++;
		}
		
		return bitches;
	}
	
	public int getVassals(Clowder clowder) {

		int vassals = 0;

		for(Clowder everyone : clowder.clowders)
		{
			if (everyone.suzerain == clowder && !everyone.bitch)
				vassals ++;
		}
		
		return vassals;
	}

	public void setHome(double x, double y, double z, EntityPlayer player) {

		this.homeX = (int) x;
		this.homeY = (int) y;
		this.homeZ = (int) z;

		ClowderData.getData(player.worldObj).markDirty();
	}
	
	public void setAllyWarp(double x, double y, double z, EntityPlayer player) {

		this.allyWarpX = (int) x;
		this.allyWarpY = (int) y;
		this.allyWarpZ = (int) z;

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

		if (world == null)
			return;
		ClowderData.getData(world).markDirty();
	}

	// 0 - created
	// 1 - not home
	// 2 - no tent
	public int tryAddWarp(EntityPlayer player, int x, int y, int z, String name) {

		World world = player.worldObj;

		if (!ClowderTerritory.isPlayerHome(player))
			return 1;

		Clowder clowder = Clowder.getClowderFromPlayer(player);

		for (int i = 2; i <= 5; i++) {

			ForgeDirection dir = ForgeDirection.getOrientation(i);

			Block block = world.getBlock(x + dir.offsetX * 2, y, z + dir.offsetZ * 2);

			if (block == ModBlocks.tp_tent) {

				int[] pos = ((BlockDummyable) ModBlocks.tp_tent).findCore(world, x + dir.offsetX * 2, y,
						z + dir.offsetZ * 2);

				if (pos != null) {

					TileEntityProp tent = (TileEntityProp) world.getTileEntity(pos[0], pos[1], pos[2]);

					if (tent.warp.isEmpty() && tent.operational()) {

						tent.warp = name;
						tent.markDirty();

						clowder.warps.put(name, new int[] { x, y + 1, z });

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

		if (!isOwner(player))
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
		this.members.clear();
		this.allies.clear();

		ClowderData.getData(world).markDirty();

		return true;

	}

	public boolean valid() {
		return this.leader != "" && clowders.contains(this);
	}

	public boolean isRaidable() {

		if (MainRegistry.freeRaid)
			return true;
		
		if (Clowder.forceOnline)
			return true;

		int online = 0;
		int members = this.members.size();

		for (String s : this.members.keySet()) {

			Long l = this.members.get(s);

			if (l > System.currentTimeMillis())
				online++;
		}

		if (members >= 6)
			return online >= 3;

		if (members >= 3)
			return online >= 2;

		return online >= 1;
	}

	public int getPlayersOnline() {

		int online = 0;
		int members = this.members.size();

		for (String s : this.members.keySet()) {

			Long l = this.members.get(s);

			if (l > System.currentTimeMillis())
				online++;
		}

		return online;
	}

	// cohesion my fucking balls
	// if i want a math utility in my clowder class i'll fucking have one
	public static String round(float f) {

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

		if (Loader.isModLoaded("HardcoreQuesting") && CommandClowder.getTeamByName(name) != null) {
			prestige += f;

			if (prestige < 0)
				prestige = 0F;
			
			prestige = Math.min(prestige, prestigeCap);
			CommandClowder.getTeamByName(name).setReputation(0, (int) prestige);
			if ((CommandClowder.getTeamByName(name).getReputation(0) != 0 || (int) prestige == 0)) {
				prestige -= (int) prestige;
				prestige += CommandClowder.getTeamByName(name).getReputation(0);
			}
		} else {
			prestige += f;

			if (prestige < 0)
				prestige = 0F;
		}
		prestige = Math.min(prestige, prestigeCap);
		this.save(world);
	}

	public void addPrestigeGen(float f, World world) {
		prestigeGen += f;

		if (prestigeGen < 0)
			prestigeGen = 0F;

		prestigeGen = Math.min(prestigeGen, prestigeGenCap);
		this.save(world);
	}

	public void addPrestigeReq(float f, World world) {
		prestigeReq += f;

		if (prestigeReq < 0)
			prestigeReq = 0F;

		this.save(world);
	}

	public void multPrestige(float f, World world) {
		prestige *= f;
		prestige = (float)(Math.floor(prestige * 10D) / 10D);
		
		if(prestige < 0)
			prestige = 0;
		
		this.save(world);
	}

	//war time goes to 10 for retreat bonus level
		public void freeWarTime(World world) {
			warTime = 10;
			
			this.save(world);
		}
	
	public void saveClowder(int i, NBTTagCompound nbt) {
		nbt.setString(i + "_uuid", this.uuid);
		nbt.setString(i + "_name", this.name);
		nbt.setString(i + "_motd", this.motd);
		nbt.setInteger(i + "_flag", this.flag.ordinal());
		nbt.setInteger(i + "_color", this.color);
		nbt.setBoolean(i + "_canChange", canChangeColour);
		nbt.setBoolean(i + "_canDisband", canDisband);
		nbt.setInteger(i + "_homeX", this.homeX);
		nbt.setInteger(i + "_homeY", this.homeY);
		nbt.setInteger(i + "_homeZ", this.homeZ);
		nbt.setInteger(i + "_allyWarpX", this.allyWarpX);
		nbt.setInteger(i + "_allyWarpY", this.allyWarpY);
		nbt.setInteger(i + "_allyWarpZ", this.allyWarpZ);
		nbt.setFloat(i + "_prestige", this.prestige);
		nbt.setFloat(i + "_prestigeGen", this.prestigeGen);
		nbt.setFloat(i + "_prestigeReq", this.prestigeReq);
		nbt.setInteger(i + "_flags", this.flags);
		nbt.setFloat(i + "_fabricateTime", this.fabricateTime);
		nbt.setFloat(i + "_warTime", this.warTime);
		nbt.setFloat(i + "_canDeclareTime", this.canDeclareTime);
		nbt.setBoolean(i + "_vassalTarget", this.vassalTarget);
		
		//bitch system
		nbt.setBoolean(i + "_bitch", this.bitch);
		
		//force online
		nbt.setBoolean(i + "_forceOnline", this.forceOnline);
		
		//total war
		nbt.setBoolean(i + "_totalenKrieg", this.totalenKrieg);
		
		//total peace
		nbt.setBoolean(i + "_paxBritannica", this.paxBritannica);

		nbt.setFloat(i + "_peaceTreaty", this.peaceTreaty);

		nbt.setBoolean(i + "_targeted", this.targeted);
		nbt.setBoolean(i + "_overtime", this.overtime);
		
		nbt.setString(i + "_enemy",this.enemyS);
		
		nbt.setString(i + "_suzerain",this.suzerainS);
		
		nbt.setString(i + "_leader", this.leader);
		nbt.setInteger(i + "_members", this.members.size());
		nbt.setInteger(i + "_officers", this.officers.size());
		nbt.setInteger(i + "_warps", this.warps.size());
		
		///poorly coded "treaty" system///
		nbt.setString(i + "_treaty1", this.treaty1);
		nbt.setString(i + "_treaty2", this.treaty2);
		nbt.setString(i + "_treaty3", this.treaty3);
		nbt.setString(i + "_treaty4", this.treaty4);
		nbt.setString(i + "_treaty5", this.treaty5);
		nbt.setString(i + "_treaty6", this.treaty6);
		nbt.setString(i + "_treaty7", this.treaty7);
		
		nbt.setFloat(i + "_treatyTime1", this.treatyTime1);
		nbt.setFloat(i + "_treatyTime2", this.treatyTime2);
		nbt.setFloat(i + "_treatyTime3", this.treatyTime3);
		nbt.setFloat(i + "_treatyTime4", this.treatyTime4);
		nbt.setFloat(i + "_treatyTime5", this.treatyTime5);
		nbt.setFloat(i + "_treatyTime6", this.treatyTime6);
		nbt.setFloat(i + "_treatyTime7", this.treatyTime7);
		
		//retreat ban
		nbt.setFloat(i + "_retreatBan", this.retreatBan);
		
		//limited chunk cap minute extension
		nbt.setFloat(i + "_bonusPoints", this.bonusPoints);

		/// SAVE MEMBERS ///
		for (int j = 0; j < this.members.keySet().size(); j++)
			nbt.setString(i + "_" + j, (String) this.members.keySet().toArray()[j]);
		
		// try to save allies
		for (int j = 0; j < this.alliesS.keySet().size(); j++)
			nbt.setString(i + "_" + j + "_ally", (String) this.alliesS.keySet().toArray()[j]);

		/// SAVE OFFICERS ///
		for (int j = 0; j < this.officers.size(); j++)
			nbt.setString(i + "_" + j + "_off", (String) this.officers.toArray()[j]);

		/// SAVE WARPS ///
		for (int j = 0; j < this.warps.keySet().size(); j++) {

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

		c.uuid = nbt.getString(i + "_uuid");

		if (c.uuid.isEmpty())
			c.uuid = UUID.randomUUID().toString();

		c.name = nbt.getString(i + "_name");
		c.motd = nbt.getString(i + "_motd");
		c.flag = ClowderFlag.values()[nbt.getInteger(i + "_flag")];
		c.color = nbt.getInteger(i + "_color");
		c.canChangeColour = nbt.getBoolean(i + "_canChange");
		c.canDisband = nbt.getBoolean(i + "_canDisband");
		c.homeX = nbt.getInteger(i + "_homeX");
		c.homeY = nbt.getInteger(i + "_homeY");
		c.homeZ = nbt.getInteger(i + "_homeZ");
		c.allyWarpX = nbt.getInteger(i + "_allyWarpX");
		c.allyWarpY = nbt.getInteger(i + "_allyWarpY");
		c.allyWarpZ = nbt.getInteger(i + "_allyWarpZ");
		c.prestige = Math.max(nbt.getFloat(i + "_prestige"), 1F);
		c.canDeclareTime = Math.max(nbt.getFloat(i + "_canDeclareTime"), 0F);
		c.fabricateTime = Math.max(nbt.getFloat(i + "_fabricateTime"), 0F);
		c.warTime = Math.max(nbt.getFloat(i + "_warTime"), 0F);
		c.prestigeGen = Math.max(nbt.getFloat(i + "_prestigeGen"), 0F);
		c.prestigeReq = Math.max(nbt.getFloat(i + "_prestigeReq"), 0F);
		c.peaceTreaty = Math.max(nbt.getFloat(i + "_peaceTreaty"), 0F);
		c.flags = nbt.getInteger(i + "_flags");
		
		//bitch system
		c.bitch = nbt.getBoolean(i + "_bitch");
		
		//force online
		c.forceOnline = nbt.getBoolean(i + "_forceOnline");
		
		//total war
		c.totalenKrieg = nbt.getBoolean(i + "_totalenKrieg");
		
		//total peace
		c.paxBritannica = nbt.getBoolean(i + "_paxBritannica");

		c.targeted = nbt.getBoolean(i + "_targeted");
		c.overtime = nbt.getBoolean(i + "_overtime");

		c.enemy = getClowderFromName(nbt.getString(i + "_enemy"));
		c.suzerain = getClowderFromName(nbt.getString(i + "_suzerain"));
		
		c.enemyS = nbt.getString(i + "_enemy");
		c.suzerainS = nbt.getString(i + "_suzerain");
		c.vassalTarget = nbt.getBoolean(i + "_vassalTarget");
		
		//poorly coded treaty
		c.treaty1 = nbt.getString(i + "_treaty1");
		c.treaty2 = nbt.getString(i + "_treaty2");
		c.treaty3 = nbt.getString(i + "_treaty3");
		c.treaty4 = nbt.getString(i + "_treaty4");
		c.treaty5 = nbt.getString(i + "_treaty5");
		c.treaty6 = nbt.getString(i + "_treaty6");
		c.treaty7 = nbt.getString(i + "_treaty7");
		
		c.treatyTime1 = Math.max(nbt.getFloat(i + "_treatyTime1"), 0F);
		c.treatyTime2 = Math.max(nbt.getFloat(i + "_treatyTime2"), 0F);
		c.treatyTime3 = Math.max(nbt.getFloat(i + "_treatyTime3"), 0F);
		c.treatyTime4 = Math.max(nbt.getFloat(i + "_treatyTime4"), 0F);
		c.treatyTime5 = Math.max(nbt.getFloat(i + "_treatyTime5"), 0F);
		c.treatyTime6 = Math.max(nbt.getFloat(i + "_treatyTime6"), 0F);
		c.treatyTime7 = Math.max(nbt.getFloat(i + "_treatyTime7"), 0F);
		
		//retreat ban
		c.retreatBan = Math.max(nbt.getFloat(i + "_retreatBan"), 0F);
		
		//limit to conquering a chunk bonus point
		c.bonusPoints = nbt.getInteger(i + "_bonusPoints");

		c.leader = nbt.getString(i + "_leader");
		int count = nbt.getInteger(i + "_members");
		int co = nbt.getInteger(i + "_officers");
		int cwarp = nbt.getInteger(i + "_warps");

		for (int j = 0; j < count; j++)
			c.members.put(nbt.getString(i + "_" + j), time());
		
		for (int j = 0; j < count; j++)
			c.alliesS.put(nbt.getString(i + "_" + j + "_ally"), time());

		for (int j = 0; j < co; j++)
			c.officers.add(nbt.getString(i + "_" + j + "_off"));

		for (int j = 0; j < cwarp; j++) {

			String name = nbt.getString(i + "_" + j + "_name");
			int[] coord = new int[] { nbt.getInteger(i + "_" + j + "_x"), nbt.getInteger(i + "_" + j + "_y"),
					nbt.getInteger(i + "_" + j + "_z") };

			c.warps.put(name, coord);
		}

		return c;
	}

	public void notifyLeader(World world, ChatComponentText message) {

		notifyPlayer(world, this.leader, message);
	}

	public void notifyAll(World world, ChatComponentText message) {

		for (String player : this.members.keySet()) {
			notifyPlayer(world, player, message);
		}
	}

	/*
	 * failure for pinging everyone with sounds public void soundAll(World world,
	 * String sound) {
	 * 
	 * for(String player : this.members.keySet()) { EntityPlayer notif =
	 * world.getPlayerEntityByName(player);
	 * 
	 * //world.playSoundEffect(notif.posX, notif.posY, notif.posZ,
	 * "hfr:item.hoiFabrication", 2.0F, 1.0F); //xddd fuck it ill use flansmod sound
	 * packet PacketPlaySound.sendSoundPacket(notif.posX, notif.posY, notif.posZ,
	 * 10f, notif.dimension, "hfr:item.hoiFabrication", false); } }
	 */

	public void notifyPlayer(World world, String player, ChatComponentText message) {

		EntityPlayer notif = world.getPlayerEntityByName(player);

		if (notif != null) {
			notif.addChatMessage(message);
		}
	}
	
	public void restockMarkets() {
		this.offerHistory.clear();
	}

	/// GLOBAL METHODS ///
	public static void recalculateIMap() {

		inverseMap.clear();

		for (Clowder clowder : clowders) {
			for (String member : clowder.members.keySet()) {
				inverseMap.put(member, clowder);
			}
		}
	}

	public static void readFromNBT(NBTTagCompound nbt) {

		colours.add(ClowderTerritory.SAFEZONE_COLOR);
		colours.add(ClowderTerritory.WARZONE_COLOR);
		colours.add(ClowderTerritory.WILDERNESS_COLOR);

		clowders.clear();

		int count = nbt.getInteger("clowderCount");

		for (int i = 0; i < count; i++)
			clowders.add(loadClowder(i, nbt));

		recalculateIMap();
	}

	public static void writeToNBT(NBTTagCompound nbt) {

		nbt.setInteger("clowderCount", clowders.size());

		for (int i = 0; i < clowders.size(); i++)
			clowders.get(i).saveClowder(i, nbt);
	}

	public static boolean areFriends(EntityPlayer player1, EntityPlayer player2) {

		if (player1 == null)
			return false;
		if (player2 == null)
			return false;

		Clowder c1 = getClowderFromPlayer(player1);
		Clowder c2 = getClowderFromPlayer(player2);

		if (c1 == null)
			return false;

		return c1 == c2;
	}

	public static Clowder getClowderFromPlayer(EntityPlayer player) {

		return getClowderFromPlayerName(player.getDisplayName());
	}

	public static Clowder getClowderFromPlayerName(String key) {

		return inverseMap.get(key);
	}

	public static Clowder getClowderFromName(String name) {

		name = name.toLowerCase();

		for (Clowder clowder : clowders) {
			if (clowder.name.toLowerCase().equals(name))
				return clowder;
		}

		return null;
	}

	public static Clowder getClowderFromUUID(String uuid) {

		uuid = uuid.toLowerCase();

		for (Clowder clowder : clowders) {
			if (clowder.uuid.toLowerCase().equals(uuid))
				return clowder;
		}

		return null;
	}

	public static void createClowder(EntityPlayer player, String name) {

		String leader = player.getDisplayName();

		Clowder c = new Clowder();

		c.uuid = UUID.randomUUID().toString();
		c.name = name;
		c.leader = leader;
		c.members.put(leader, time());

		int colour = player.getRNG().nextInt(0x1000000);
		while (colours.contains(colour))
			colour = player.getRNG().nextInt(0x1000000);

		c.color = colour;
		colours.add(colour);

		c.setHome(player.posX, player.posY, player.posZ, player);
		
		c.setAllyWarp(player.posX, player.posY, player.posZ, player);

		c.motd = "Message of the day!";
		c.flag = ClowderFlag.TRICOLOR;

		c.prestige = 25;
		c.addPrestigeGen(3, player.worldObj);

		clowders.add(c);
		inverseMap.put(leader, c);

		ClowderData.getData(player.worldObj).markDirty();
	}

	// the thing that adds prestige to prestige bank - allah note
	public static void updatePrestige(World world) {

		for (Clowder clowder : clowders) {

			if (clowder.valid()) {

				float prestige = clowder.getPrestigeGen();
				
				float loss = clowder.getPrestigeReq();

//				// multiplication prestige decay thing allah bookmark
//				prestige *= (float) Math.pow(0.99, clowder.getPrestige());
				
				
				//consume prestige required
				clowder.addPrestige((float) (-1f*loss), world);

				// if you are not a tributary, use normal prestige adding
				if (clowder.suzerain == null)
					clowder.addPrestige(prestige, world);

				else {
					//bitches lose 1/2, vassals lose 1/5, master gets 1/10 prestige off the top. ignores prestige generation cap
					if(clowder.bitch)
					{
						clowder.addPrestige((float) (prestige * 0.5), world);
						clowder.notifyAll(world,
								new ChatComponentText(CommandClowder.TITLE + "We have lost " + (float) prestige * 0.5
										+ " prestige because we are a tributary to " + clowder.suzerain.name));
					}
					else
					{
					clowder.addPrestige((float) (prestige * 0.8), world);
					clowder.notifyAll(world,
							new ChatComponentText(CommandClowder.TITLE + "We have lost " + (float) prestige * 0.2
									+ " prestige because we are a tributary to " + clowder.suzerain.name));
					}
					clowder.suzerain.addPrestige((float) (prestige * 0.1), world);
					clowder.suzerain.notifyAll(world, new ChatComponentText(CommandClowder.TITLE + "We have received "
							+ (float) prestige * 0.1f + " extra prestige as tribute from " + clowder.name));
			
				}
			}
		}
	}

	public static long time() {
		return System.currentTimeMillis();
	}

	public static class ScheduledTeleport {

		int posX;
		int posY;
		int posZ;
		String player;
		String warp;
		boolean home;
		boolean rendezvous = false;
		String allyName;

		public ScheduledTeleport(int posX, int posY, int posZ, String player, String warp) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.player = player;
			this.warp = warp;
		}

		public ScheduledTeleport(int posX, int posY, int posZ, String player, boolean home) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.player = player;
			this.home = home;
		}
		
		//for ally warp
		public ScheduledTeleport(int posX, int posY, int posZ, String player, boolean home, boolean rendezvous, String allyName) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.player = player;
			this.home = home;
			this.rendezvous = rendezvous;
			this.allyName = allyName;
		}
	}
}