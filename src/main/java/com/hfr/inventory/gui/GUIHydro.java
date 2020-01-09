package com.hfr.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerHydro;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityHydro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIHydro extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_hydro.png");
	private TileEntityHydro diFurnace;
	
	public GUIHydro(InventoryPlayer invPlayer, TileEntityHydro tedf) {
		super(new ContainerHydro(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		int walls = diFurnace.getWallNr();
		int turbines = diFurnace.getTurbineNr();
		this.fontRendererObj.drawString("Size: " + walls, 16, 24, 4210752);
		this.fontRendererObj.drawString("Turbines: " + turbines, 16, 34, 4210752);
		this.fontRendererObj.drawString("Output: " + (diFurnace.getRate(walls, turbines) / 500D) + " TE/s", 16, 44, 4210752);
		
		if(walls > 0 && turbines > 0)
			this.fontRendererObj.drawString(Math.round(1D / (diFurnace.getRate(walls, turbines) / 500D) * 10D) / 10D + " s/TE", 54, 54, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)diFurnace.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 69 - i, 176, 52 - i, 16, i);
	}
}
