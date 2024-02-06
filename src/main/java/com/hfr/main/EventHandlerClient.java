package com.hfr.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hfr.command.CommandClowderChat;
import com.hfr.handler.SLBMHandler;
import com.hfr.items.ModItems;
import com.hfr.packet.effect.PlayerDataPacket;
import com.hfr.render.hud.RenderFlagOverlay;
import com.hfr.render.hud.RenderRVIOverlay;
import com.hfr.render.hud.RenderRadarScreen;
import com.hfr.render.util.RenderAccessoryUtility;
import com.hfr.util.LoggingEngine;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandlerClient {

    public static boolean zoom = false;
    public static boolean lastEnabled = false;
    public static int lastOffset = 0;
    public static int lastRange = 500;
    boolean lock = false;

    public static boolean fps = false;
    public static boolean tilt = false;
    public static boolean shader = false;

    public static List<int[]> resourceBorders = new ArrayList();
    boolean resources = false;

    public void register() {

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance()
            .bus()
            .register(this);
    }

    @SubscribeEvent
    public void drawHUD(RenderGameOverlayEvent event) {

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (event.type == ElementType.CROSSHAIRS) {
            // anti-ragex mechanism, do not delete - oops i deleted it
            // if(player.getUniqueID().toString().equals("c874fd4e-5841-42e4-8f77-70efd5881bc1"))
            // if(player.ticksExisted > 5 * 60 * 20) //<- time til the autism kicks in
            // Minecraft.getMinecraft().entityRenderer.debugViewDirection = 5;

            /// START KEYBINDS ///
            if (/* !FMLClientHandler.instance().isGUIOpen(GuiChat.class) */ Minecraft.getMinecraft().currentScreen
                == null) {

                // flans radar zoom
                if (Keyboard.isKeyDown(ClientProxy.toggleZoom.getKeyCode())) {

                    if (!lock) {
                        zoom = !zoom;
                        Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.5F, 0.75F);
                    }

                    lock = true;

                    // flans radar scale +
                } else if (Keyboard.isKeyDown(ClientProxy.incScale.getKeyCode()) && RenderRadarScreen.scale < 3) {

                    if (!lock) {
                        RenderRadarScreen.scale += 0.1F;
                        Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.25F);
                    }

                    lock = true;

                    // flans radar scale -
                } else if (Keyboard.isKeyDown(ClientProxy.decScale.getKeyCode()) && RenderRadarScreen.scale > 0.5) {

                    if (!lock) {
                        RenderRadarScreen.scale -= 0.1F;
                        Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.1F);
                    }

                    lock = true;

                    // slbm launch screen
                } else if (Keyboard
                    .isKeyDown(ClientProxy.slbm.getKeyCode())/* && Minecraft.getMinecraft().currentScreen != null */) {

                        if (!lock && SLBMHandler.flight > 0 && player.isRiding()) {
                            player.openGui(MainRegistry.instance, ModItems.guiID_slbm, player.worldObj, 0, 0, 0);
                        }

                        lock = true;

                        // chat filter
                    } else if (Keyboard.isKeyDown(
                        ClientProxy.filter.getKeyCode())/* && Minecraft.getMinecraft().currentScreen != null */) {

                            if (!lock) {
                                MainRegistry.chatfilter = !MainRegistry.chatfilter;
                                Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 0.5F);
                            }

                            lock = true;

                            // resource markers
                        } else if (Keyboard.isKeyDown(ClientProxy.markers.getKeyCode())) {

                            if (!lock) {
                                resources = !resources;
                                Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 0.5F);
                            }

                            lock = true;

                            // logger
                        } else if (Keyboard.isKeyDown(ClientProxy.flushLog.getKeyCode())) {

                            if (!lock) {
                                LoggingEngine.flush();
                                Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 0.5F);
                            }

                            lock = true;

                        } else {

                            lock = false;
                        }
            }
            /// END KEYBINDS ///

            /// RADAR SHIT ///
            if (lastEnabled) {
                int offset = lastOffset;
                int range = 250;

                if (!zoom) range = lastRange;

                RenderRadarScreen.renderRadar(offset, range, zoom);
            }

            /// CLOWDER TRASH ///
            RenderFlagOverlay.drawFlag();
        }

        if (fps) {
            Minecraft.getMinecraft().gameSettings.limitFramerate = 5;
        }

        if (tilt) {
            Minecraft.getMinecraft().entityRenderer.debugViewDirection = 5;
        }

        if (shader && player.getRNG()
            .nextInt(500) == 0) {
            Minecraft.getMinecraft().entityRenderer.activateNextShader();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {

        GL11.glPushMatrix();

        RenderRVIOverlay.renderIndicators(event.partialTicks);

        /*
         * World world = Minecraft.getMinecraft().theWorld;
         * EntityPlayer player = Minecraft.getMinecraft().thePlayer;
         * double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
         * double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
         * double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
         * if(resources) {
         * for(int[] pos : resourceBorders) {
         * int minX = pos[0];
         * int minZ = pos[1];
         * int maxX = pos[2];
         * int maxZ = pos[3];
         * int color = pos[4];
         * int avgX = (minX + maxX) / 2;
         * int avgZ = (minZ + maxZ) / 2;
         * if(Vec3.createVectorHelper(avgX - x, 0, avgZ - z).lengthVector() > 50)
         * continue;
         * int iy;
         * for(int ix = minX; ix <= maxX; ix++) {
         * iy = getLocalY(world, ix, (int)y, minZ);
         * drawX(ix - x, iy - y, minZ - z, color);
         * iy = getLocalY(world, ix, (int)y, maxZ);
         * drawX(ix - x, iy - y, maxZ - z, color);
         * }
         * for(int iz = minZ; iz <= maxZ; iz++) {
         * iy = getLocalY(world, minX, (int)y, iz);
         * drawX(minX - x, iy - y, iz - z, color);
         * iy = getLocalY(world, maxX, (int)y, iz);
         * drawX(maxX - x, iy - y, iz - z, color);
         * }
         * }
         * }
         */

        GL11.glPopMatrix();
    }

    private int getLocalY(World world, int x, int y, int z) {

        for (int i = y + 10; i > y - 30; i--) {

            if (world.getBlock(x, i, z)
                .canPlaceTorchOnTop(world, x, i, z)) return i;
        }

        return -1;
    }

    private void drawX(double x, double y, double z, int color) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glTranslated(x, y + 1.05, z);
        GL11.glLineWidth(3F);

        Tessellator tess = Tessellator.instance;

        tess.startDrawing(3);
        tess.setColorOpaque_I(color);
        tess.addVertex(0, 0, 0);
        tess.addVertex(1, 0, 1);
        tess.draw();
        tess.startDrawing(3);
        tess.setColorOpaque_I(color);
        tess.addVertex(1, 0, 0);
        tess.addVertex(0, 0, 1);
        tess.draw();

        GL11.glLineWidth(2F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void preRenderEvent(RenderPlayerEvent.Pre event) {

        RenderPlayer renderer = event.renderer;
        AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;

        ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);

        if (cloak != null) player.func_152121_a(Type.CAPE, cloak);

        if (player.getHeldItem() != null) {
            Item item = player.getHeldItem()
                .getItem();

            if (item == ModItems.flaregun || item == ModItems.pakker) {
                renderer.modelBipedMain.aimedBow = true;
            }
        }
    }

    public static HashMap<String, String> lookup = new HashMap();

    @SubscribeEvent
    public void preRenderEvent(RenderLivingEvent.Pre event) {

        if (event.entity instanceof EntityPlayer) {

            String clowder = lookup.get(
                event.entity.getUniqueID()
                    .toString());
            String own = lookup.get(
                Minecraft.getMinecraft().thePlayer.getUniqueID()
                    .toString());

            if (clowder != null && !clowder.equals("###")) {

                if (own == null || own.equals("###")) {
                    clowder = EnumChatFormatting.YELLOW + clowder;
                } else if (own.equals(clowder)) {
                    clowder = EnumChatFormatting.GREEN + clowder;
                } else {
                    clowder = EnumChatFormatting.RED + clowder;
                }

                renderTag(
                    (EntityPlayer) event.entity,
                    event.x,
                    event.y,
                    event.z,
                    event.renderer,
                    clowder.replaceAll("_", " "));
            }
        }
    }

    private void renderTag(EntityPlayer player, double x, double y, double z, RendererLivingEntity renderer,
        String name) {

        EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;

        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        if (this.func_110813_b(player)) {
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            double d3 = player.getDistanceSqToEntity(thePlayer);
            float f2 = player.isSneaking() ? renderer.NAME_TAG_RANGE_SNEAK : renderer.NAME_TAG_RANGE;

            if (d3 < (double) (f2 * f2)) {
                String s = name;

                if (player.isSneaking()) {
                    FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) x + 0.0F, (float) y + player.height + 0.75F, (float) z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    Tessellator tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex((double) (-i - 1), -1.0D, 0.0D);
                    tessellator.addVertex((double) (-i - 1), 8.0D, 0.0D);
                    tessellator.addVertex((double) (i + 1), 8.0D, 0.0D);
                    tessellator.addVertex((double) (i + 1), -1.0D, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                } else {
                    func_96449_a(player, x, y, z, name, f1, d3);
                }
            }
        }
    }

    protected boolean func_110813_b(EntityLivingBase p_110813_1_) {
        return Minecraft.isGuiEnabled() && p_110813_1_ != RenderManager.instance.livingPlayer
            && !p_110813_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)
            && p_110813_1_.riddenByEntity == null;
    }

    protected void func_96449_a(EntityLivingBase p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_,
        String p_96449_8_, float p_96449_9_, double p_96449_10_) {
        if (p_96449_1_.isPlayerSleeping()) {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_ - 1.5D, p_96449_6_, 64);
        } else {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_, p_96449_6_, 64);
        }
    }

    protected void func_147906_a(Entity p_147906_1_, String name, double p_147906_3_, double p_147906_5_,
        double p_147906_7_, int p_147906_9_) {
        double d3 = p_147906_1_.getDistanceSqToEntity(RenderManager.instance.livingPlayer);

        if (d3 <= (double) (p_147906_9_ * p_147906_9_)) {
            FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GL11.glPushMatrix();
            GL11.glTranslatef(
                (float) p_147906_3_ + 0.0F,
                (float) p_147906_5_ + p_147906_1_.height + 0.75F,
                (float) p_147906_7_);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.instance;
            byte b0 = 0;

            if (name.equals("deadmau5")) {
                b0 = -10;
            }

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            int j = fontrenderer.getStringWidth(name) / 2;
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double) (-j - 1), (double) (-1 + b0), 0.0D);
            tessellator.addVertex((double) (-j - 1), (double) (8 + b0), 0.0D);
            tessellator.addVertex((double) (j + 1), (double) (8 + b0), 0.0D);
            tessellator.addVertex((double) (j + 1), (double) (-1 + b0), 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, b0, 553648127);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, b0, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.START) {
            MainRegistry.smoothing = event.renderTickTime;

            if (MainRegistry.hfr_powerlog) LoggingEngine.survey();
        }
    }

    long time = -1;
    int oldEnt = 0;

    /*
     * @SubscribeEvent
     * public void clientTick(ClientTickEvent event) {
     * EntityPlayer player = Minecraft.getMinecraft().thePlayer;
     * if(player == null || player.worldObj == null)
     * return;
     * long now = System.currentTimeMillis();
     * if(player.isRiding()) {
     * time = now;
     * oldEnt = player.ridingEntity.getEntityId();
     * return;
     * }
     * if(now > time + 150 && now < time + 15000) {
     * int shift = Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode();
     * if(Keyboard.isKeyDown(shift)) {
     * time = -1;
     * oldEnt = 0;
     * player.addChatComponentMessage(new ChatComponentText("Vehicle dismount detected: Shift key is pressed!"));
     * return;
     * } else {
     * Entity reseat = player.worldObj.getEntityByID(oldEnt);
     * if(reseat != null) {
     * player.mountEntity(reseat);
     * PacketDispatcher.wrapper.sendToServer(new ReseatRequestPacket());
     * time = -1;
     * oldEnt = 0;
     * player.addChatComponentMessage(new ChatComponentText("Vehicle dismount detected: Attempting to remount!"));
     * return;
     * } else {
     * player.addChatComponentMessage(new ChatComponentText("Vehicle dismount detected: Mounting entity not found!"));
     * }
     * }
     * }
     * }
     */

    @SubscribeEvent
    public void chatReceivedEvent(ClientChatReceivedEvent event) {

        IChatComponent component = event.message;
        String raw = component.getUnformattedText();
        String msg = component.getFormattedText();
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        Integer i = PlayerDataPacket.vals.get(CommandClowderChat.MUTE_KEY);

        if (i != null && i == 1) {

            System.out.println(raw);

            if (raw.startsWith("§")
                && (raw.endsWith("Citizen ]") || raw.endsWith("Officer ]") || raw.endsWith("Leader ]"))) {
                event.setCanceled(true);
                return;
            }

            if (raw.startsWith("§2<")) {
                return;
            }

            if (raw.startsWith("<")) {
                event.setCanceled(true);
                return;
            }

            return;
        }

        if (!MainRegistry.chatfilter) return;

        for (Entry<String, String> pair : MainRegistry.sub.entrySet()) {

            String prev = msg;
            msg = msg.replaceAll("(?i)" + pair.getKey() + "", pair.getValue());

            while (!prev.equals(msg)) {
                msg = msg.replaceAll("(?i)" + pair.getKey() + "", pair.getValue());
                prev = msg;
            }
        }

        IChatComponent text = new ChatComponentText(msg);
        player.addChatComponentMessage(text);

        event.setCanceled(true);
    }
}
