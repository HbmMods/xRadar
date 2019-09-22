package com.hfr.inventory.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.inventory.container.ContainerMachineMarket;
import com.hfr.lib.RefStrings;
import com.hfr.packet.AuxButtonPacket;
import com.hfr.packet.PacketDispatcher;
import com.hfr.render.hud.RenderRadarScreen.Blip;
import com.hfr.tileentity.TileEntityMachineMarket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineMarket extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_stocks.png");
	private TileEntityMachineMarket diFurnace;
	
	int index = 0;
	//Stock stock;
	
	public GUIMachineMarket(InventoryPlayer invPlayer, TileEntityMachineMarket tedf) {
		super(new ContainerMachineMarket(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 222;
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			index--;
    	}
		
    	if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			index++;
    	}

    	if(guiLeft + 79 <= x && guiLeft + 79 + 18 > x && guiTop + 89 < y && guiTop + 89 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, index, 0));
    	}

    	if(guiLeft + 79 <= x && guiLeft + 79 + 18 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, index, 1));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		if(getStock(index) == null)
			return;
		
		String name = getStock(index).name + " (" + getStock(index).shortname + ")";
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        Tessellator tessellator = Tessellator.instance;
		Minecraft minecraft = Minecraft.getMinecraft();
        
        float vals[] = getStock(index).value;
        float render[] = new float[15];

        List<Float> floats = Arrays.asList(ArrayUtils.toObject(vals));
        
        float min = Collections.min(floats);
        float max = Collections.max(floats);
        
        int padding = 5;
        int range = 52 - padding * 2;
        float d = max - min;
        
        if(d == 0)
        	d = 1F;
        
        for(int i = 0; i < render.length; i++) {
        	render[i] = (vals[i] - min) * range / d;
        }
        
        if(vals[14] > vals[13])
    		drawTexturedModalRect(guiLeft + 150, guiTop + 38, 176, 0, 5, 5);
        else if(vals[14] == vals[13])
    		drawTexturedModalRect(guiLeft + 150, guiTop + 38, 176, 5, 5, 5);
        else
    		drawTexturedModalRect(guiLeft + 150, guiTop + 38, 176, 10, 5, 5);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glLineWidth(3F);

        float last = render[0];
        
        for(int i = 1; i < render.length; i++) {
        	
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(0xFFFFFF);
            
            tessellator.addVertex(guiLeft + 8 + 10 * (i - 1), guiTop + 88 - last - padding, 0);
            tessellator.addVertex(guiLeft + 8 + 10 * (i - 1) + 10, guiTop + 88 - render[i] - padding, 0);
            tessellator.draw();
            
            last = render[i];
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        float fontScale = 2F;
        
		GL11.glScalef(1 / fontScale, 1 / fontScale, 1 / fontScale);

	    minecraft.fontRenderer.drawString("" + round(max), (int) ((guiLeft + 9) * fontScale), (int) ((guiTop + 37) * fontScale), 0xFFFFFF);
	    minecraft.fontRenderer.drawString("" + round(min), (int) ((guiLeft + 9) * fontScale), (int) ((guiTop + 83.5) * fontScale), 0xFFFFFF);

	    float diff = (vals[14] - vals[13]) / vals[13] * 100;
	    
	    int color = 0xFF0000;
	    
	    if(diff > 0)
	    	color = 0x00FF00;
	    if(diff == 0)
	    	color = 0xFFFF00;
	    
	    minecraft.fontRenderer.drawString("" + round(diff) + "%", (int) ((guiLeft + 155.5) * fontScale), (int) ((guiTop + 38.5) * fontScale), color);
	    minecraft.fontRenderer.drawString("=" + round(vals[14]), (int) ((guiLeft + 150) * fontScale), (int) ((guiTop + 48.5) * fontScale), 0xFFFFFF);

	    
		GL11.glScalef(fontScale, fontScale, fontScale);

		fontScale = 1.5F;
		GL11.glScalef(1 / fontScale, 1 / fontScale, 1 / fontScale);
	    minecraft.fontRenderer.drawString("Your " + getStock(index).shortname + " shares:", (int) ((guiLeft + 105) * fontScale), (int) ((guiTop + 95) * fontScale), 4210752);
	    minecraft.fontRenderer.drawString("" + getStock(index).shares, (int) ((guiLeft + 110) * fontScale), (int) ((guiTop + 105) * fontScale), 4210752);
		GL11.glScalef(fontScale, fontScale, fontScale);
		
	    GL11.glEnable(GL11.GL_LIGHTING);
		
        GL11.glPopMatrix();
	}
	
	public float round(float f) {
		
		f *= 100F;
		f = (int)f;
		f /= 100F;
		
		return f;
	}
	
	private int getIndex(int i) {
		
		i = i % diFurnace.stocks.size();
		
		if(i < 0) {
			i = diFurnace.stocks.size() + i;
		}
		
		return i;
	}
	
	private Stock getStock(int i) {
		
		i = getIndex(i);
		return diFurnace.stocks.get(i);
	}
}
