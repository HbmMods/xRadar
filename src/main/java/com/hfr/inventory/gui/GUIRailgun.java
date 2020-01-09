package com.hfr.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerRailgun;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.weapon.TileEntityRailgun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRailgun extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_railgun.png");
	private TileEntityRailgun railgun;

	public GUIRailgun(InventoryPlayer invPlayer, TileEntityRailgun tedf) {
		super(new ContainerRailgun(invPlayer, tedf));
		railgun = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.railgun.hasCustomInventoryName() ? this.railgun.getInventoryName() : I18n.format(this.railgun.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);


		if(!isTargeting() && !isFiring()) {
	    	if(guiLeft + 79 <= x && guiLeft + 79 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
	    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(railgun.xCoord, railgun.yCoord, railgun.zCoord, 0, 0));
	    	}
			
	    	if(guiLeft + 106 <= x && guiLeft + 106 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
	    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(railgun.xCoord, railgun.yCoord, railgun.zCoord, 0, 1));
	    	}
	    }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j1 = (int)railgun.getPowerScaled(160);
		drawTexturedModalRect(guiLeft + 8, guiTop + 53, 8, 166, j1, 16);
		
		if(isTargeting())
			drawTexturedModalRect(guiLeft + 79, guiTop + 16, 176, 0, 18, 18);
		
		if(isFiring())
			drawTexturedModalRect(guiLeft + 106, guiTop + 16, 194, 0, 18, 18);
	}
	
	private boolean isTargeting() {
		
		return System.currentTimeMillis() < railgun.startTime + railgun.cooldownDurationMillis;
	}
	
	private boolean isFiring() {
		
		return System.currentTimeMillis() < railgun.fireTime + railgun.cooldownDurationMillis;
	}
}
