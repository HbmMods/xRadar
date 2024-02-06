package com.hfr.entity.missile;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

import com.hfr.entity.logic.EntityBlast;
import com.hfr.main.MainRegistry;

public class EntityMissileStrong extends EntityMissileBaseSimple {

    public EntityMissileStrong(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    public EntityMissileStrong(World world, float x, float y, float z, int a, int b) {
        super(world, x, y, z, a, b);
    }

    @Override
    public void onImpact() {
        // ExplosionLarge.explode(worldObj, posX, posY, posZ, 25.0F, true, true, true);
        EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
        worldObj.newExplosion(scapegoat, posX, posY, posZ, 25F, false, true);
        worldObj.spawnEntityInWorld(
            EntityBlast
                .statFac(worldObj, posX, posY, posZ, MainRegistry.t2blast, MainRegistry.t2Damage, 10, 10, false));
    }

    @Override
    public void onForceImpact() {
        super.onForceImpact();
        worldObj.spawnEntityInWorld(
            EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t2blast, MainRegistry.t2Damage, 0, 0, false));
    }

    @Override
    public int getMissileType() {
        return 1;
    }

    @Override
    public boolean getIsBreaching() {
        return false;
    }

}
