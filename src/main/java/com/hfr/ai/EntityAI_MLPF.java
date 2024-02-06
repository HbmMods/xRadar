package com.hfr.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public class EntityAI_MLPF extends EntityAIBase {

    private Class targetClass;
    private EntityCreature mover;
    private int range;
    private int distance;
    private static final int vertical = 10;
    private double speed;

    public EntityAI_MLPF(EntityCreature entity, Class targetClass, int range, double speed, int distance) {
        this.mover = entity;
        this.targetClass = targetClass;
        this.range = range;
        this.speed = speed;
        this.distance = distance;
    }

    @Override
    public boolean shouldExecute() {
        return !mover.hasPath();
    }

    public void startExecuting() {

        if (mover.getEntityToAttack() == null)
            mover.setTarget(mover.worldObj.getClosestVulnerablePlayerToEntity(mover, range));

        if (mover.getEntityToAttack() != null) getPathEntityToEntityPartial(
            mover.worldObj,
            mover,
            mover.getEntityToAttack(),
            distance,
            true,
            true,
            false,
            true);
    }

    @Override
    public boolean continueExecuting() {
        return !mover.hasPath();
    }

    @Override
    public void resetTask() {}

    public static PathEntity getPathEntityToEntityPartial(World world, Entity fromEntity, Entity toEntity,
        float maxDist, boolean allowDoors, boolean allowBlocked, boolean allowWater, boolean canDrown) {
        world.theProfiler.startSection("pathfind");
        int startX = MathHelper.floor_double(fromEntity.posX);
        int startY = MathHelper.floor_double(fromEntity.posY + 1.0D);
        int startZ = MathHelper.floor_double(fromEntity.posZ);
        int maxDistEff = (int) (maxDist + 16.0F);
        int minX = startX - maxDistEff;
        int minY = startY - maxDistEff;
        int minZ = startZ - maxDistEff;
        int maxX = startX + maxDistEff;
        int maxY = startY + maxDistEff;
        int maxZ = startZ + maxDistEff;
        ChunkCache chunkcache = new ChunkCache(world, minX, minY, minZ, maxX, maxY, maxZ, 0);

        Vec3 vec = Vec3.createVectorHelper(
            toEntity.posX - fromEntity.posX,
            toEntity.posY - fromEntity.posY,
            toEntity.posZ - fromEntity.posZ);
        vec = vec.normalize();
        vec.xCoord *= maxDist;
        vec.yCoord *= maxDist;
        vec.zCoord *= maxDist;

        int x = (int) Math.floor(fromEntity.posX + vec.xCoord);
        int y = (int) Math.floor(fromEntity.posY + vec.yCoord);
        int z = (int) Math.floor(fromEntity.posZ + vec.zCoord);

        // this part will adjust the end of the path so it's actually on the ground, it being unreachable causes mobs to
        // slow down
        boolean solid = false;

        for (int i = y; i > y - 10; i--) {
            if (!world.getBlock(x, i, z)
                .getMaterial()
                .blocksMovement() && world.getBlock(x, i - 1, z)
                    .isNormalCube()) {
                solid = true;
                y = i;
                break;
            }

        }

        if (!solid) for (int i = y; i < y + 10; i++) {
            if (!world.getBlock(x, i, z)
                .getMaterial()
                .blocksMovement() && world.getBlock(x, i - 1, z)
                    .isNormalCube()) {
                solid = true;
                y = i;
                break;
            }
        }

        // PathEntity pathentity = (new PathFinder(chunkcache, allowDoors, allowBlocked, allowWater,
        // canDrown)).createEntityPathTo(fromEntity, toEntity, maxDist);
        PathEntity pathentity = (new PathFinder(chunkcache, allowDoors, allowBlocked, allowWater, canDrown))
            .createEntityPathTo(fromEntity, x, y, z, maxDist);
        world.theProfiler.endSection();
        return pathentity;
    }
}
