package hua.huase.shanhaicontinent.entity.jinengentity.hacdiemtula;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class Jineng_HDTL_1_Entity extends ThrowableItemProjectile {
    private int Nianxian;
    private int orbitTick = 0;
    private boolean shoot = false;

    public Jineng_HDTL_1_Entity(EntityType<? extends Jineng_HDTL_1_Entity> type, Level level, int nianxian) {
        super(type, level);
        this.Nianxian = nianxian;
    }

    public Jineng_HDTL_1_Entity(EntityType<? extends Jineng_HDTL_1_Entity> type, Level level) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {

        if (this.getOwner() instanceof ServerPlayer player) {

            int nianxian = Nianxian;

            float damage = nianxian;

            result.getEntity().hurt(this.damageSources().playerAttack(player), damage);
        }

        level().explode(this,
                this.getX(),
                this.getY(),
                this.getZ(),
                1.0F,
                Level.ExplosionInteraction.NONE);

        this.discard();
    }

    @Override
public void tick() {
    super.tick();

    Entity owner = this.getOwner();

    if (owner instanceof Player player && !shoot) {

        orbitTick++;

        // Keep hitbox centered above the player before launch.
        this.setPos(player.getX(), player.getEyeY() + 0.7D, player.getZ());

        // sau 10 tick bắn ra
        if (orbitTick > 10) {

            shoot = true;

            this.shootFromRotation(
                    player,
                    player.getXRot(),
                    player.getYRot(),
                    0,
                    1.6F,
                    0
            );
        }

        return;
    }

    if (level().isClientSide) {
        level().addParticle(
                ParticleTypes.ENCHANT,
                this.getX(),
                this.getY(),
                this.getZ(),
                0,0,0
        );
    }

    if (this.tickCount > 60) {
        this.discard();
    }
}
}
