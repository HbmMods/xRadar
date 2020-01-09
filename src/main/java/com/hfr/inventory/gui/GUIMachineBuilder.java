package com.hfr.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hfr.inventory.container.ContainerMachineBuilder;
import com.hfr.items.ModItems;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.client.AuxButtonPacket;
import com.hfr.tileentity.machine.TileEntityMachineBuilder;
import com.hfr.tileentity.machine.TileEntityMachineBuilder.SchemOffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBuilder extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_builder.png");
	private TileEntityMachineBuilder diFurnace;
	private static int index = 0;
	
	public GUIMachineBuilder(InventoryPlayer invPlayer, TileEntityMachineBuilder tedf) {
		super(new ContainerMachineBuilder(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		if(TileEntityMachineBuilder.offers.size() == 0)
			return;
		
		if(getSchematic(index) == null)
			return;
		
		SchemOffer schem = getSchematic(index);

		String name = schem.name;
		String val = "Cost: " + schem.value;
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 21, 4210752);
		this.fontRendererObj.drawString(val, this.xSize / 2 - this.fontRendererObj.getStringWidth(val) / 2, 34, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
		if(TileEntityMachineBuilder.offers.size() == 0)
			return;
		
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			index--;
    	}
		
    	if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			index++;
    	}
		
    	if(guiLeft + 61 <= x && guiLeft + 61 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, getIndex(index), 0));
    	}
		
    	if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, getIndex(index), 1));
    	}
		
    	/*if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, getIndex(index), 2));
    	}*/
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(TileEntityMachineBuilder.offers.size() == 0)
			return;
		
		int cost = getSchematic(index).value;
		ItemStack slot = diFurnace.slots[0];
		
		if(slot != null && slot.getItem() == ModItems.wrench && slot.stackSize >= cost) {
			drawTexturedModalRect(guiLeft + 97, guiTop + 52, 176, 0, 18, 18);
		}
	}
	
	private int getIndex(int i) {
		
		i = i % TileEntityMachineBuilder.offers.size();
		
		if(i < 0) {
			i = TileEntityMachineBuilder.offers.size() + i;
		}
		
		return i;
	}
	
	private SchemOffer getSchematic(int i) {
		
		i = getIndex(i);
		return TileEntityMachineBuilder.offers.get(i);
	}
}
