package com.hfr.inventory.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerMachineRadar;
import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.machine.TileEntityMachineRadar;
import com.hfr.tileentity.machine.TileEntityMachineRadar.RadarEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadar extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radar.png");
	private TileEntityMachineRadar diFurnace;

	public GUIMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
		super(new ContainerMachineRadar(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 216;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(RadarEntry m : diFurnace.nearbyMissiles) {
				int x = guiLeft + (int)((m.posX - diFurnace.xCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) + 108;
				int z = guiTop + (int)((m.posZ - diFurnace.zCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) + 117;
				
				if(mouseX + 4 > x && mouseX - 4 < x && 
						mouseY + 4 > z && mouseY - 4 < z) {

					
					String[] text = new String[] { "Error." };
					
					if(m.name.equals("Tier 1 Missile")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Tier 2 Missile")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Tier 3 Missile")) {
						
						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Tier 1 Missile (Ascending)")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Tier 2 Missile (Ascending)")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Tier 3 Missile (Ascending)")) {
						
						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Anti-Ballistic Missile")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Explosive Shell")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else if(m.name.equals("Railgun Charge")) {

						text = new String[] { m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					} else {

						text = new String[] { "Player " + m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					}
					
					this.func_146283_a(Arrays.asList(text), x, z);
					
					return;
				}
			}
		}
	}
	
	private String getNameFromPlayer(int id) {
		
		List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
		
		for(EntityPlayer player : players) {
			
			if(player.getEntityId() == id) {
				return player.getDisplayName();
			}
		}
		
		return "UNKNOWN";
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radar");
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 218 <= x && guiLeft + 218 + 18 > x && guiTop + 108 < y && guiTop + 108 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 0));
    	}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//Power gauge
		if(diFurnace.storage.getEnergyStored() > 0) {
			int i = (int)diFurnace.getPowerScaled(200);
			drawTexturedModalRect(guiLeft + 8, guiTop + 221, 0, 234, i, 16);
		}
		
		//Toggle
		drawTexturedModalRect(guiLeft + 216, guiTop + 105, 216, 156, 23, 24);
		drawTexturedModalRect(guiLeft + 218, guiTop + 108, 216, 180 + diFurnace.mode * 18, 18, 18);
		
		//Radar dots
		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(RadarEntry m : diFurnace.nearbyMissiles) {
				
				int x = (int)((m.posX - diFurnace.xCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int z = (int)((m.posZ - diFurnace.zCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int t = 1;
				
				if(m.name.equals("Tier 1 Missile")) {

					t = 0;
				} else if(m.name.equals("Tier 2 Missile")) {

					t = 1;
				} else if(m.name.equals("Tier 3 Missile")) {

					t = 2;
				} else if(m.name.equals("Tier 1 Missile (Ascending)")) {

					t = 0;
				} else if(m.name.equals("Tier 2 Missile (Ascending)")) {

					t = 1;
				} else if(m.name.equals("Tier 3 Missile (Ascending)")) {

					t = 2;
				} else if(m.name.equals("Anti-Ballistic Missile")) {

					t = 4;
				} else if(m.name.equals("Explosive Shell")) {

					t = 9;
				} else if(m.name.equals("Railgun Charge")) {

					t = 9;
				} else {

					t = 6;
				}

				drawTexturedModalRect(guiLeft + 108 + x, guiTop + 117 + z, 216, 8 * t, 8, 8);
			}
		}
	}
}
