package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerDieselGen;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityDieselGen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIDieselGen extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_dieselgen.png");
	private TileEntityDieselGen diFurnace;
	
	public GUIDieselGen(InventoryPlayer invPlayer, TileEntityDieselGen tedf) {
		super(new ContainerDieselGen(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(guiLeft + 62 <= x && guiLeft + 62 + 16 > x && guiTop + 17 < y && guiTop + 17 + 52 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { diFurnace.diesel + "/" + diFurnace.maxDiesel + "mB"}), x - guiLeft, y - guiTop);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = diFurnace.storage.getEnergyStored() * 52 / diFurnace.storage.getMaxEnergyStored();
		drawTexturedModalRect(guiLeft + 98, guiTop + 69 - i, 176, 52 - i, 16, i);
		
		int j = diFurnace.diesel * 52 / diFurnace.maxDiesel;
		drawTexturedModalRect(guiLeft + 62, guiTop + 69 - j, 192, 52 - j, 16, j);
	}
}
