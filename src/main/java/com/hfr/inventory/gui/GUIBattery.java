package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerBattery;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityBattery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBattery extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_battery.png");
	private TileEntityBattery diFurnace;
	
	public GUIBattery(InventoryPlayer invPlayer, TileEntityBattery tedf) {
		super(new ContainerBattery(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(guiLeft + 71 <= x && guiLeft + 71 + 34 > x && guiTop + 17 < y && guiTop + 17 + 52 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { diFurnace.storage.getEnergyStored() + "/" + diFurnace.storage.getMaxEnergyStored() + "RF"}), x - guiLeft, y - guiTop);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = diFurnace.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 71, guiTop + 69 - i, 176, 52 - i, 34, i);
	}
}
