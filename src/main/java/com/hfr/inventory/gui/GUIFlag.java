package com.hfr.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.Clowder;
import com.hfr.inventory.container.ContainerFlag;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.clowder.TileEntityFlag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFlag extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_flag.png");
	private TileEntityFlag diFurnace;
	private long press;
	
	public GUIFlag(InventoryPlayer invPlayer, TileEntityFlag tedf) {
		super(new ContainerFlag(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
		String name = I18n.format(diFurnace.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		float prestige = diFurnace.prestige;
		float prestigeReq = diFurnace.prestigeReq;
		
		int color = 0xFF0000;
		
		if(prestigeReq <= prestige)
			color = 0x008000;
		
		this.fontRendererObj.drawString("Prestige: " + Clowder.round(prestige), 50, 34, 4210752);
		this.fontRendererObj.drawString("Required: " + Clowder.round(prestigeReq), 50, 46, color);
		

    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
    		this.func_146283_a(Arrays.asList(new String[] {"Requires 3 prestige", "Claims land in a 10 chunk radius"}), x - guiLeft, y - guiTop);
    	}
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 35 < y && guiTop + 35 + 18 >= y) {
    		this.func_146283_a(Arrays.asList(new String[] {"Requires 2 prestige", "Claims land in a 5 chunk radius"}), x - guiLeft, y - guiTop);
    	}
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 53 < y && guiTop + 53 + 18 >= y) {
    		this.func_146283_a(Arrays.asList(new String[] {"Requires 1 prestige", "Claims land in a 1 chunk radius"}), x - guiLeft, y - guiTop);
    	}
    	if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
    		if(diFurnace.height < 1.0F )
        		this.func_146283_a(Arrays.asList(new String[] {"Not claimed"}), x - guiLeft, y - guiTop);
    		else if(diFurnace.mode == 0)
        		this.func_146283_a(Arrays.asList(new String[] {"Not active"}), x - guiLeft, y - guiTop);
    		else
        		this.func_146283_a(Arrays.asList(new String[] {"Active"}), x - guiLeft, y - guiTop);
    	}
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);

    	//A
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {

    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 0));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    	}
    	
    	//B
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 35 < y && guiTop + 35 + 18 >= y) {

    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 1));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    	}
    	
    	//C
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 53 < y && guiTop + 53 + 18 >= y) {

    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 2));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    	}
    	
    	//CAPTURE
    	/*if(!isPressed() && guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {

    		press = System.currentTimeMillis();
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 3));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    	}*/
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = diFurnace.mode;
		
		if(i > 0)
			drawTexturedModalRect(guiLeft + 25, guiTop + 16 - 18 + 18 * i, 176, -18 + 18 * diFurnace.mode, 18, 18);
		
		if(diFurnace.height < 1.0F)
			drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 72, 18, 18);
		else if(i > 0)
			drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 108, 18, 18);
		else
			drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 90, 18, 18);
		
		if(isPressed())
			drawTexturedModalRect(guiLeft + 133, guiTop + 16, 176, 54, 18, 18);
	}
	
	private boolean isPressed() {
		return press + 1000 >= System.currentTimeMillis();
	}
}
