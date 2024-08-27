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

public class JiNengKPBSEntity extends ThrowableItemProjectile {

    public JiNengKPBSEntity(EntityType<JiNengKPBSEntity> entityType, Level level) {
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

    protected void onHitEntity(EntityHitResult p_37404_) {
        super.onHitEntity(p_37404_);
        Entity entity = p_37404_.getEntity();
        if(this.getOwner() instanceof ServerPlayer serverPlayer){
            entity.hurt(this.damageSources().thrown(this, serverPlayer), AttrubuteAPI.getWugong((serverPlayer)));
        }
    }


    private int livingtime = 15;

    public void tick(){
        super.tick();
        if(livingtime<=0){
            this.discard();
        }

        livingtime--;
    }

    private int energy = 10;

    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
//        this.explode();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            if(energy<=0){
                this.discard();
            }
            energy--;
        }

    }

    protected void explode() {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.9F, Level.ExplosionInteraction.TNT);
        }else {
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

}
