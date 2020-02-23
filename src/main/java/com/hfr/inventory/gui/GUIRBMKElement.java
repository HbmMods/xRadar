package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerRBMKElement;
import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityRBMKElement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKElement extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_rbmk.png");
	private TileEntityRBMKElement diFurnace;
	
	public GUIRBMKElement(InventoryPlayer invPlayer, TileEntityRBMKElement tedf) {
		super(new ContainerRBMKElement(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		if(guiLeft + 26 <= mouseX && guiLeft + 26 + 16 > mouseX && guiTop + 69 - 52 < mouseY && guiTop + 69 >= mouseY)
			this.func_146283_a(Arrays.asList("Water: " + diFurnace.water.getFluidAmount() + "mB", "Steam: " + diFurnace.steam.getFluidAmount() + "mB"), mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		//this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = diFurnace.getXenonScaled(52);
		drawTexturedModalRect(guiLeft + 134, guiTop + 69 - i, 208, 52 - i, 16, i);

		int j = diFurnace.getReactivityScaled(62);
		drawTexturedModalRect(guiLeft + 57, guiTop + 74 - j, 176, 114 - j, 62, j);

		int k = diFurnace.getGaugeScaled(52, diFurnace.water);
		drawTexturedModalRect(guiLeft + 26, guiTop + 69 - k, 176, 52 - k, 16, k);

		int l = diFurnace.getGaugeScaled(52, diFurnace.steam);
		drawTexturedModalRect(guiLeft + 26, guiTop + 69 - k - l, 192, 52 - k - l, 16, l);
	}
}
