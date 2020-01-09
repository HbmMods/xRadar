package com.hfr.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerMachineRefinery;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityMachineRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRefinery extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_refinery.png");
	private TileEntityMachineRefinery refinery;

	public GUIMachineRefinery(InventoryPlayer invPlayer, TileEntityMachineRefinery tedf) {
		super(new ContainerMachineRefinery(invPlayer, tedf));
		refinery = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int j = (int)refinery.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 176, 52 - j, 16, j);
		
		int k = (int)refinery.getOilScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 70 - k, 192, 52 - k, 34, k);
		
		int l = (int)refinery.getHeavyScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 70 - l, 176, 104 - l, 16, l);
		
		int m = (int)refinery.getNaphScaled(52);
		drawTexturedModalRect(guiLeft + 98, guiTop + 70 - m, 192, 104 - m, 16, m);
		
		int n = (int)refinery.getLightScaled(52);
		drawTexturedModalRect(guiLeft + 116, guiTop + 70 - n, 208, 104 - n, 16, n);
		
		int o = (int)refinery.getPetroScaled(52);
		drawTexturedModalRect(guiLeft + 134, guiTop + 70 - o, 224, 104 - o, 16, o);
	}
}
