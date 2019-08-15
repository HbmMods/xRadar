package com.hfr.packet;

import com.hfr.lib.RefStrings;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketDispatcher {

	//Mark 1 Packet Sending Device
	public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(RefStrings.MODID);

	public static final void registerPackets()
	{
		int i = 0;
		wrapper.registerMessage(TERadarPacket.Handler.class, TERadarPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(TERadarDestructorPacket.Handler.class, TERadarDestructorPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(TESirenPacket.Handler.class, TESirenPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(AuxElectricityPacket.Handler.class, AuxElectricityPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(TEFFPacket.Handler.class, TEFFPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(AuxButtonPacket.Handler.class, AuxButtonPacket.class, i++, Side.SERVER);
		wrapper.registerMessage(TEVaultPacket.Handler.class, TEVaultPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(TEMissilePacket.Handler.class, TEMissilePacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ParticleBurstPacket.Handler.class, ParticleBurstPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ParticleControlPacket.Handler.class, ParticleControlPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
		wrapper.registerMessage(VRadarPacket.Handler.class, VRadarPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(VRadarDestructorPacket.Handler.class, VRadarDestructorPacket.class, i++, Side.CLIENT);
		//first successful serialized packet, eliminates race conditions between sender and destructor
		wrapper.registerMessage(SRadarPacket.Handler.class, SRadarPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(AuxGaugePacket.Handler.class, AuxGaugePacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(TESRadarPacket.Handler.class, TESRadarPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(RailgunCallbackPacket.Handler.class, RailgunCallbackPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(RailgunFirePacket.Handler.class, RailgunFirePacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(RailgunRotationPacket.Handler.class, RailgunRotationPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(CBTPacket.Handler.class, CBTPacket.class, i++, Side.CLIENT);

	}
	
}
