package com.hfr.inventory;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.TileEntityMachineRadar;
import com.hfr.tileentity.TileEntityMachineRadar.RadarEntry;

import net.minecraft.client.Minecraft;
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

					text = new String[] { "Player " + m.name +": ", m.posX + " / " + m.posZ, "Alt.: " + m.posY };
					
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

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.storage.getEnergyStored() > 0) {
			int i = (int)diFurnace.getPowerScaled(200);
			drawTexturedModalRect(guiLeft + 8, guiTop + 221, 0, 234, i, 16);
		}
		
		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(RadarEntry m : diFurnace.nearbyMissiles) {
				int x = (int)((m.posX - diFurnace.xCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int z = (int)((m.posZ - diFurnace.zCoord) / ((double)MainRegistry.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int t = 1;

				drawTexturedModalRect(guiLeft + 108 + x, guiTop + 117 + z, 216, 8 * t, 8, 8);
			}
		}
	}
}
