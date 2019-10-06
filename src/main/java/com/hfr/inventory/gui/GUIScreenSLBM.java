package com.hfr.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hfr.handler.SLBMHandler;
import com.hfr.items.ModItems;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.packet.client.ItemDesignatorPacket;
import com.hfr.packet.client.SLBMCommandPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenSLBM extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_slbm.png");
	private static final RenderItem itemRenderer = RenderItem.getInstance();
    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;
    private final EntityPlayer player;
    private GuiTextField xCoord;
    private GuiTextField zCoord;
    
    public GUIScreenSLBM(EntityPlayer player) {
    	
    	this.player = player;
        xSize = 176;
        ySize = 86;
    }
    
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        
        this.xCoord.drawTextBox();
        this.zCoord.drawTextBox();
        
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.xCoord = new GuiTextField(this.fontRendererObj, guiLeft + 64, guiTop + 21, 50, 12);
        this.xCoord.setTextColor(-1);
        this.xCoord.setDisabledTextColour(-1);
        this.xCoord.setEnableBackgroundDrawing(false);
        this.xCoord.setMaxStringLength(8);
        
        this.zCoord = new GuiTextField(this.fontRendererObj, guiLeft + 64, guiTop + 57, 50, 12);
        this.zCoord.setTextColor(-1);
        this.zCoord.setDisabledTextColour(-1);
        this.zCoord.setEnableBackgroundDrawing(false);
        this.zCoord.setMaxStringLength(8);
    }

    protected void mouseClicked(int x, int y, int i) {

		if(guiLeft + 124 <= x && guiLeft + 124 + 18 > x && guiTop + 34 < y && guiTop + 34 + 18 >= y) {

			if(player.inventory.hasItem(SLBMHandler.getMissileFromPlayer(player))) {

				try {
					Integer posX = Integer.valueOf(xCoord.getText());
					Integer posZ = Integer.valueOf(zCoord.getText());
					PacketDispatcher.wrapper.sendToServer(new SLBMCommandPacket(posX, posZ));
				} catch(Exception ex) { }
			}
    	}
    	

        this.xCoord.mouseClicked(x, y, i);
        this.zCoord.mouseClicked(x, y, i);
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(SLBMHandler.getMissileFromPlayer(player)), guiLeft + 35, guiTop + 35);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(player.inventory.hasItem(SLBMHandler.getMissileFromPlayer(player)))
			drawTexturedModalRect(guiLeft + 34, guiTop + 34, 176, 18, 18, 18);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
    	this.xCoord.textboxKeyTyped(p_73869_1_, p_73869_2_);
    	this.zCoord.textboxKeyTyped(p_73869_1_, p_73869_2_);
    	
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
