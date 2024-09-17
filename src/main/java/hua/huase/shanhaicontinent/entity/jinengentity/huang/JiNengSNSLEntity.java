package hua.huase.shanhaicontinent.entity.jinengentity.huang;

import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class JiNengSNSLEntity extends ThrowableItemProjectile {

    public JiNengSNSLEntity(EntityType<JiNengSNSLEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getDefaultItem() {

        return Items.SNOWBALL;
    }



    public void handleEntityEvent(byte b) {
        if (b == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHitEntity(Entity entity) {
        if(this.getOwner() instanceof ServerPlayer serverPlayer){
            entity.hurt(this.damageSources().thrown(this, serverPlayer), AttrubuteAPI.getWugong((serverPlayer)));
        }
    }

    private int livingtime = 15;

    public void tick(){
        super.tick();
        if(!this.level().isClientSide && this.level().getGameTime()%10 == 0){
            for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(1))) {
                this.onHitEntity(entity);
                this.explode();
            }
        }
        if(livingtime<=0){
            this.discard();
        }

        livingtime--;
    }

    private int energy = 15;

    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
        this.explode();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            if(energy<=0){
                this.discard();
            }
            energy--;
        }

    }

    public boolean isExploade = false;
    protected void explode() {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 5.0F, isExploade? Level.ExplosionInteraction.MOB:Level.ExplosionInteraction.NONE);
        }else {
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }
}
