package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerTurbine;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITurbine extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_turbine.png");
	private TileEntityMachineTurbine diFurnace;
	
	public GUITurbine(InventoryPlayer invPlayer, TileEntityMachineTurbine tedf) {
		super(new ContainerTurbine(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);

	    if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 0));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    }

	    if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 34 < y && guiTop + 34 + 18 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 1));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    }

	    if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 2));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    }
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { "DISCARD ALL WATER", "Not recommended for setups that", "require a water cycle. Since there's no", "water coming out, you know."}), x - guiLeft, y - guiTop);
		}

		if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 34 < y && guiTop + 34 + 18 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { "DISCARD WATER WHEN FULL", "May lead to water deficit", "when overloaded."}), x - guiLeft, y - guiTop);
		}

		if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { "STOP TURBINE WHEN FULL", "Necessary for setups that", "completely rely on the water cycle."}), x - guiLeft, y - guiTop);
		}

		if(guiLeft + 44 <= x && guiLeft + 44 + 16 > x && guiTop + 17 < y && guiTop + 17 + 52 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { "Steam:", diFurnace.steam.getFluidAmount() + "mB"}), x - guiLeft, y - guiTop);
		}

		if(guiLeft + 80 <= x && guiLeft + 80 + 16 > x && guiTop + 17 < y && guiTop + 17 + 52 >= y) {
			this.func_146283_a(Arrays.asList(new String[] { "Water:", diFurnace.water.getFluidAmount() + "mB"}), x - guiLeft, y - guiTop);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = diFurnace.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 116, guiTop + 69 - i, 208, 52 - i, 16, i);
		
		int j = diFurnace.getWaterScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 69 - j, 192, 52 - j, 16, j);
		
		int k = diFurnace.getSteamScaled(52);
		drawTexturedModalRect(guiLeft + 44, guiTop + 69 - k, 176, 52 - k, 16, k);
		
		int l = diFurnace.mode;
		drawTexturedModalRect(guiLeft + 7, guiTop + 16 + l * 18, 176, 52 + l * 18, 18, 18);
	}
}
