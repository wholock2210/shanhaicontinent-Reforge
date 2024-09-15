package hua.huase.shanhaicontinent.entity.protectionbox;

import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.network.PlayMessages;

public class ProtectionBox extends Entity {

	public int lifeTime;

	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;

	private final BoundingBox sbb;

	public ProtectionBox(EntityType<?> type, Level world) {
		super(type, world);
		this.sizeX = this.sizeY = this.sizeZ = 2;
		this.sbb = null;
		this.lifeTime = 60;
	}



	public ProtectionBox(Level world, BoundingBox sbb) {
		super(EntityInit.PROTECTION_BOX.get(), world);

		this.sbb = sbb;

		this.moveTo(sbb.minX(), sbb.minY(), sbb.minZ(), 0.0F, 0.0F);

		this.sizeX = sbb.getXSpan();
		this.sizeY = sbb.getYSpan();
		this.sizeZ = sbb.getZSpan();

		this.dimensions = EntityDimensions.fixed(Math.max(this.sizeX, this.sizeZ), this.sizeY);

		this.lifeTime = 60;
	}

	public ProtectionBox(PlayMessages.SpawnEntity spawnEntity, Level level) {
		super(EntityInit.PROTECTION_BOX.get(), level);
		this.sizeX = this.sizeY = this.sizeZ = 0;
		this.sbb = null;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.lifeTime <= 1) {
			this.discard();
		} else {
			this.lifeTime--;
		}
	}

	public boolean matches(BoundingBox sbb) {
		return this.sbb.minX() == sbb.minX() && this.sbb.minY() == sbb.minY() && this.sbb.minZ() == sbb.minZ()
				&& this.sbb.maxX() == sbb.maxX() && this.sbb.maxY() == sbb.maxY() && this.sbb.maxZ() == sbb.maxZ();
	}

	public void resetLifetime() {
		this.lifeTime = 60;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}
}
