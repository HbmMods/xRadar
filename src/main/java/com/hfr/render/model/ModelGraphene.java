package com.hfr.render.model;

import com.hfr.tmt.ModelCustomArmor;
import com.hfr.tmt.ModelRendererTurbo;

import net.minecraft.client.model.ModelBase;

public class ModelGraphene extends ModelCustomArmor {
	
    int textureX;
    int textureY;
    
    public ModelGraphene() {
        this.textureX = 150;
        this.textureY = 16;
        (this.bodyModel = new ModelRendererTurbo[8])[0] = new ModelRendererTurbo((ModelBase)this, 1, 1, this.textureX, this.textureY);
        this.bodyModel[1] = new ModelRendererTurbo((ModelBase)this, 33, 1, this.textureX, this.textureY);
        this.bodyModel[2] = new ModelRendererTurbo((ModelBase)this, 49, 1, this.textureX, this.textureY);
        this.bodyModel[3] = new ModelRendererTurbo((ModelBase)this, 65, 1, this.textureX, this.textureY);
        this.bodyModel[4] = new ModelRendererTurbo((ModelBase)this, 81, 1, this.textureX, this.textureY);
        this.bodyModel[5] = new ModelRendererTurbo((ModelBase)this, 97, 1, this.textureX, this.textureY);
        this.bodyModel[6] = new ModelRendererTurbo((ModelBase)this, 113, 1, this.textureX, this.textureY);
        this.bodyModel[7] = new ModelRendererTurbo((ModelBase)this, 129, 1, this.textureX, this.textureY);
        this.bodyModel[0].addShapeBox(-4.0f, 3.0f, -2.0f, 8, 8, 4, 0.0f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f);
        this.bodyModel[0].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[1].addShapeBox(-4.0f, 0.0f, -2.0f, 2, 3, 4, 0.0f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.0f, 0.2f, 1.0f, 0.0f, 0.2f, 1.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f);
        this.bodyModel[1].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[2].addShapeBox(2.0f, 0.0f, -2.0f, 2, 3, 4, 0.0f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 1.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f, 1.0f, 0.0f, 0.2f);
        this.bodyModel[2].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[3].addShapeBox(-3.0f, 0.0f, -2.0f, 1, 5, 4, 0.0f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f);
        this.bodyModel[3].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[4].addShapeBox(2.0f, 0.0f, -2.0f, 1, 5, 4, 0.0f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, 0.3f);
        this.bodyModel[4].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[5].addShapeBox(-3.0f, 5.3f, -2.0f, 1, 6, 4, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.3f, 1.3f, 0.0f, 0.3f, -1.5f, 0.0f, 0.3f, -1.5f, 0.0f, 0.3f, 1.3f, 0.0f, 0.3f);
        this.bodyModel[5].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[6].addShapeBox(3.3f, 5.3f, -2.0f, 1, 6, 4, 0.0f, 1.3f, 0.0f, 0.3f, -1.3f, 0.0f, 0.3f, -1.3f, 0.0f, 0.3f, 1.3f, 0.0f, 0.3f, -0.3f, 0.0f, 0.3f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.3f, -0.3f, 0.0f, 0.3f);
        this.bodyModel[6].setPosition(0.0f, 0.0f, 0.0f);
        this.bodyModel[7].addShapeBox(-2.5f, 4.5f, -2.0f, 5, 1, 4, 0.0f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f, 0.35f, 0.0f, 0.35f);
        this.bodyModel[7].setPosition(0.0f, 0.0f, 0.0f);
    }
}
