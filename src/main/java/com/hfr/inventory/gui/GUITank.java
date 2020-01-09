package com.hfr.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerTank;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityTank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITank extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_tank.png");
	private TileEntityTank siren;

	public GUITank(InventoryPlayer invPlayer, TileEntityTank tedf) {
		super(new ContainerTank(invPlayer, tedf));
		siren = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = (this.siren.hasCustomInventoryName() ? this.siren.getInventoryName() : I18n.format(this.siren.getInventoryName())) + " (" + siren.fill + "mB)";
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int k = (int)siren.getFillScaled(52);
		drawTexturedModalRect(guiLeft + 71, guiTop + 70 - k, 34 * siren.type, 218 - k, 34, k);
	}
}
