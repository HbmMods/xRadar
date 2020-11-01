package com.hfr.handler;

import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.AuxParticlePacketNT;
import com.hfr.packet.effect.ExplosionSoundPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosionSound {
	
	public static final double max = 8;
	
	public static void handleExplosion(World world, Explosion explosion) {
		
		if(world.isRemote)
			return;

		double x = explosion.explosionX;
		double y = explosion.explosionY;
		double z = explosion.explosionZ;
		float pow = explosion.explosionSize;
		
		if(pow < 3)
			return;
		
		if(pow > max)
			pow = (float)max;
		
		double farRange = 1000D * pow / max;
		PacketDispatcher.wrapper.sendToAllAround(new ExplosionSoundPacket((int)x, (int)y, (int)z, pow), new TargetPoint(world.provider.dimensionId, x, y, z, farRange));

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "explosion");
		data.setFloat("strength", pow);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
	}
	
	public static void handleClient(EntityPlayer player, int x, int y, int z, float pow) {

		World world = player.worldObj;
		double closeRange = 50D * pow / max;
		double mediumRange = 250D * pow / max;
		double farRange = 1000D * pow / max;
		
		double distance = Math.sqrt(Math.pow(player.posX - x, 2) + Math.pow(player.posY - y, 2) + Math.pow(player.posZ - z, 2));
		
		if(distance <= closeRange) {
			world.playSound(player.posX, player.posY, player.posZ, "hfr:explosion.close", 100, 0.8F + world.rand.nextFloat() * 0.4F, false);
		} else if(distance <= mediumRange) {
			world.playSound(player.posX, player.posY, player.posZ, "hfr:explosion.medium", 100, 0.8F + world.rand.nextFloat() * 0.4F, false);
		} else if(distance <= farRange) {
			world.playSound(player.posX, player.posY, player.posZ, "hfr:explosion.rumble", 100, 0.8F + world.rand.nextFloat() * 0.4F, false);
		} 
	}

}
