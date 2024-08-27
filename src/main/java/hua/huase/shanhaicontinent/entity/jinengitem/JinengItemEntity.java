package hua.huase.shanhaicontinent.entity.jinengitem;

import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JinengItemEntity extends Entity {
    private  long exiistTime;
    private  Player player;
    private  ItemStack itemStack;

    private float f;
    private float f2;
    private int posint;

    private static final EntityDataAccessor<Float> F =
            SynchedEntityData.defineId(JinengItemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> F2 =
            SynchedEntityData.defineId(JinengItemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> POSINT =
            SynchedEntityData.defineId(JinengItemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PLAYERID =
            SynchedEntityData.defineId(JinengItemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> ITEMSTACK =
            SynchedEntityData.defineId(JinengItemEntity.class, EntityDataSerializers.ITEM_STACK);
    public JinengItemEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    public static void createEntity( Player player, int posint, ItemStack stack) {
        JinengItemEntity jinengItemEntity = new JinengItemEntity(EntityInit.JINENGITEM.get(), player.level());

//        jinengItemEntity.setXRot(player.getXRot());
//        jinengItemEntity.setYRot(player.getYRot());

        jinengItemEntity.f = (float) (-Math.sin(((posint%3-1)*45+player.getYRot()) * 0.017453292F)*2f);
        jinengItemEntity.f2 = (float) (Math.cos(((posint%3-1)*45+player.getYRot()) * 0.017453292F)*2f);
        jinengItemEntity.posint=posint;
        jinengItemEntity.player = player;
        jinengItemEntity.itemStack=stack;

        jinengItemEntity.entityData.set(F,(float) (-Math.sin(((posint%3-1)*45+player.getYRot()) * 0.017453292F)*2f));
        jinengItemEntity.entityData.set(F2,(float) (Math.cos(((posint%3-1)*45+player.getYRot()) * 0.017453292F)*2f));
        jinengItemEntity.entityData.set(POSINT,posint);
        jinengItemEntity.entityData.set(PLAYERID,player.getId());
        jinengItemEntity.entityData.set(ITEMSTACK,stack);

        jinengItemEntity.setPos(player.getX()+jinengItemEntity.f-0.5,player.getY()+posint/3,player.getZ()+jinengItemEntity.f2-0.5);




        player.level().addFreshEntity(jinengItemEntity);
    }

//    protected MovementEmission getMovementEmission() {
//        return MovementEmission.NONE;
//    }
    @Override
    public void tick() {

//        this.xo = this.getX();
//        this.yo = this.getY();
//        this.zo = this.getZ();
        if(!this.level().isClientSide){
            if(player != null){
                this.setPos(player.getX()+f-0.5,player.getY()+posint/3,player.getZ()+f2-0.5);
            }else {
                this.discard();
            }
            if(exiistTime>=200){
                this.discard();
            }
        }else {
            Entity entity = this.level().getEntity(this.entityData.get(PLAYERID));
            if(entity != null){

                this.setPos(entity.getX()+this.entityData.get(F)-0.5,entity.getY()+this.entityData.get(POSINT)/3,entity.getZ()+this.entityData.get(F2)-0.5);

            }
        }

        exiistTime++;
        super.tick();
    }


    @Override
    public boolean isPickable() {
        return true;
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else{
            if (!this.level().isClientSide) {
                ItemStack itemStack1 = this.itemStack;
                boolean flag = player.getInventory().add(itemStack1);
                if(flag){
                    player.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    player.containerMenu.broadcastChanges();
                }else {
                    ItemEntity itementity = player.drop(itemStack1, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(player.getUUID());
                    }
                }
                this.discard();
                return InteractionResult.CONSUME;

            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(F, 1f);
        this.entityData.define(F2, 1f);
        this.entityData.define(POSINT, 1);
        this.entityData.define(PLAYERID, 1);
        this.entityData.define(ITEMSTACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.entityData.set(F, compoundTag.getFloat("f"));
        this.entityData.set(F2, compoundTag.getFloat("f2"));
        this.entityData.set(POSINT, compoundTag.getInt("posint"));
        this.entityData.set(PLAYERID, compoundTag.getInt("playerid"));
        this.entityData.set(ITEMSTACK, ItemStack.of((CompoundTag) compoundTag.get("itemstack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("f",this.entityData.get(F));
        compoundTag.putFloat("f2",this.entityData.get(F2));
        compoundTag.putInt("posint",this.entityData.get(POSINT));
        compoundTag.putInt("playerid",this.entityData.get(PLAYERID));
        compoundTag.put("itemstack",this.entityData.get(ITEMSTACK).serializeNBT());
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public ItemStack getItem() {
        return this.entityData.get(ITEMSTACK);
    }
}
