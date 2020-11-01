package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerFoundry;
import com.hfr.inventory.container.ContainerTurbine;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.machine.TileEntityFoundry;
import com.hfr.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIFoundry extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/nuMachines/gui_caster.png");
	private TileEntityFoundry foundry;
	
	public GUIFoundry(InventoryPlayer invPlayer, TileEntityFoundry tedf) {
		super(new ContainerFoundry(invPlayer, tedf));
		foundry = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);

	    if(guiLeft + 108 <= x && guiLeft + 108 + 6 > x && guiTop + 21 < y && guiTop + 21 + 8 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(foundry.xCoord, foundry.yCoord, foundry.zCoord, 0, 0));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    }

	    if(guiLeft + 134 <= x && guiLeft + 134 + 6 > x && guiTop + 21 < y && guiTop + 21 + 8 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(foundry.xCoord, foundry.yCoord, foundry.zCoord, 0, 1));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    }
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.foundry.hasCustomInventoryName() ? this.foundry.getInventoryName() : I18n.format(this.foundry.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(guiLeft + 115 <= x && guiLeft + 115 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
			this.func_146283_a(Arrays.asList(new String[] {
					EnumChatFormatting.YELLOW + I18n.format(foundry.options.get(foundry.index).getUnlocalizedName() + ".name" ),
					"Costs " + foundry.recipes.get(foundry.options.get(foundry.index).getUnlocalizedName())
					}), x - guiLeft, y - guiTop);
		}

		if(guiLeft + 26 <= x && guiLeft + 52 + 18 > x && guiTop + 37 < y && guiTop + 37 + 12 >= y) {
			this.func_146283_a(Arrays.asList(new String[] {
					foundry.steel + " Ingots"
					}), x - guiLeft, y - guiTop);
		}
		
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int steel = foundry.getSteelScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 37, 176, 0, steel, 12);
		
		int heat = foundry.getHeatScaled(16);
		drawTexturedModalRect(guiLeft + 38, guiTop + 69 - heat, 228, 16 - heat, 4, heat);
		
		int progress = foundry.getProgressScaled(48);
		drawTexturedModalRect(guiLeft + 80, guiTop + 40, 176, 12, progress, 11);
		
		int i = foundry.index;
		
		drawTexturedModalRect(guiLeft + 115, guiTop + 16, 18 * (i % 8), 166 + 18 * (i / 8), 18, 18);
	}
}
