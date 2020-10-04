package com.hfr.packet;

import com.hfr.lib.RefStrings;
import com.hfr.packet.client.*;
import com.hfr.packet.effect.*;
import com.hfr.packet.tile.*;

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
		wrapper.registerMessage(StockPacket.Handler.class, StockPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(StockDisplayPacket.Handler.class, StockDisplayPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(SchematicPreviewPacket.Handler.class, SchematicPreviewPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(BuilderPacket.Handler.class, BuilderPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(SchemOfferPacket.Handler.class, SchemOfferPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(SLBMCommandPacket.Handler.class, SLBMCommandPacket.class, i++, Side.SERVER);
		wrapper.registerMessage(SLBMOfferPacket.Handler.class, SLBMOfferPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ClowderFlagPacket.Handler.class, ClowderFlagPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ClowderBorderPacket.Handler.class, ClowderBorderPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(ExplosionSoundPacket.Handler.class, ExplosionSoundPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(OfferPacket.Handler.class, OfferPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(RVIPacket.Handler.class, RVIPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(CumPacket.Handler.class, CumPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(PlayerDataPacket.Handler.class, PlayerDataPacket.class, i++, Side.CLIENT);
		wrapper.registerMessage(AuxParticlePacketNT.Handler.class, AuxParticlePacketNT.class, i++, Side.CLIENT);
		wrapper.registerMessage(ReseatRequestPacket.Handler.class, ReseatRequestPacket.class, i++, Side.SERVER);

	}
	
}
