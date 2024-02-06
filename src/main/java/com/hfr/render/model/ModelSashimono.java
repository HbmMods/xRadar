package com.hfr.render.model; // Path where the model is located

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.ClowderFlag;
import com.hfr.tmt.ModelCustomArmor;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelSashimono extends ModelCustomArmor // Same as Filename
{

    int textureX = 64;
    int textureY = 128;

    public ModelSashimono() // Same as Filename
    {
        bodyModel = new ModelRendererTurbo[4];

        initbodyModel_1();
    }

    private void initbodyModel_1() {
        bodyModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 87
        bodyModel[1] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 88
        bodyModel[2] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 89
        bodyModel[3] = new ModelRendererTurbo(this, 0, 8, 16, 12); // Box 90

        bodyModel[0].addShapeBox(
            -0.5F,
            -31.5F,
            4.5F,
            1,
            40,
            1,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F); // Box 87
        bodyModel[0].setRotationPoint(0F, 0F, 0F);

        bodyModel[1].addShapeBox(
            -0.5F,
            -31.5F,
            5.5F,
            1,
            1,
            12,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F,
            0F); // Box 88
        bodyModel[1].setRotationPoint(0F, 0F, 0F);

        bodyModel[2].addShapeBox(
            -0.5F,
            0F,
            1.5F,
            1,
            1,
            4,
            0F,
            3.3F,
            0F,
            0.3F,
            3.3F,
            0F,
            0.3F,
            0.3F,
            0F,
            0.3F,
            0.3F,
            0F,
            0.3F,
            3.3F,
            0F,
            0.3F,
            3.3F,
            0F,
            0.3F,
            0.3F,
            0F,
            0.3F,
            0.3F,
            0F,
            0.3F); // Box 89
        bodyModel[2].setRotationPoint(0F, 0F, 0F);

        bodyModel[3].addBox(0F, -17.5F, -30.5F, 0, 12, 16);
        bodyModel[3].setRotationPoint(0F, 0F, 0F);
    }

    EntityPlayer ref;

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        if (entity instanceof EntityPlayer) {
            ref = (EntityPlayer) entity;
        } else {
            ref = null;
        }

        super.render(entity, f, f1, f2, f3, f4, f5);

        ref = null;
    }

    @Override
    public void render(ModelRendererTurbo[] models, ModelRenderer bodyPart, float f5, float scale) {

        if (models.length == 0) return;

        // initbodyModel_1();

        setBodyPart(models, bodyPart, scale);
        // rotate everything and render all parts except the flag
        for (int i = 0; i < models.length; i++) {
            models[i].rotateAngleX = bodyPart.rotateAngleX;
            models[i].rotateAngleY = bodyPart.rotateAngleY;
            models[i].rotateAngleZ = bodyPart.rotateAngleZ;

            if (i != 3) models[i].render(f5);
        }

        if (ref == null) return;

        ItemStack banner = ref.inventory.armorInventory[0];

        if (banner == null || !banner.hasTagCompound()) return;

        ClowderFlag flag = ClowderFlag.values()[banner.stackTagCompound.getInteger("flag")];
        int color = banner.stackTagCompound.getInteger("color");

        if (flag == ClowderFlag.NONE || flag == null) {
            flag = ClowderFlag.TRICOLOR;
        }

        int r = ((color & 0xFF0000) >> 16) / 2;
        int g = ((color & 0xFF00) >> 8) / 2;
        int b = (color & 0xFF) / 2;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        Minecraft.getMinecraft().renderEngine.bindTexture(flag.getFlag());
        GL11.glColor3b((byte) r, (byte) g, (byte) b);
        models[3].rotateAngleX += (-Math.PI / 2D);
        models[3].render(f5);
        models[3].rotateAngleX -= (-Math.PI / 2D);

        Minecraft.getMinecraft().renderEngine.bindTexture(flag.getFlagOverlay());
        GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
        models[3].rotateAngleX += (-Math.PI / 2D);
        models[3].render(f5);
        models[3].rotateAngleX -= (-Math.PI / 2D);
    }
}
