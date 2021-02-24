package io.github.ultrusbot.moborigins.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.ChunkRandom;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class OriginSlimeEntity extends MobEntity implements Monster {
    private static final TrackedData<Integer> SLIME_SIZE;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    public float targetStretch;
    public float stretch;
    public float lastStretch;
    private boolean onGroundLastTick;

    public OriginSlimeEntity(EntityType<? extends OriginSlimeEntity> entityType, World world) {
        super(EntityRegistry.ORIGIN_SLIME, world);
        this.moveControl = new OriginSlimeEntity.SlimeMoveControl(this);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new OriginSlimeEntity.SwimmingGoal(this));
        this.goalSelector.add(2, new OriginSlimeEntity.FaceTowardTargetGoal(this));
        this.goalSelector.add(3, new OriginSlimeEntity.RandomLookGoal(this));
        this.goalSelector.add(5, new OriginSlimeEntity.MoveGoal(this));
        this.goalSelector.add(6, new OriginSlimeFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.add(1, new OriginSlimeTrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new OriginSlimeAttackWithOwnerGoal(this));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SLIME_SIZE, 1);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    public void setSize(int size, boolean heal) {
        this.dataTracker.set(SLIME_SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double)(size * size));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)size));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double)size);
        if (heal) {
            this.setHealth(this.getMaxHealth());
        }

        this.experiencePoints = 0;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.world.isClient) {
            this.setOwner(player);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public int getSize() {
        return (Integer)this.dataTracker.get(SLIME_SIZE);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Size", this.getSize() - 1);
        tag.putBoolean("wasOnGround", this.onGroundLastTick);
        if (this.getOwnerUuid() != null) {
            tag.putUuid("Owner", this.getOwnerUuid());
        }

    }

    public void readCustomDataFromTag(CompoundTag tag) {
        int i = tag.getInt("Size");
        if (i < 0) {
            i = 0;
        }

        this.setSize(i + 1, false);
        super.readCustomDataFromTag(tag);
        this.onGroundLastTick = tag.getBoolean("wasOnGround");
        UUID uUID2;
        if (tag.containsUuid("Owner")) {
            uUID2 = tag.getUuid("Owner");
        } else {
            String string = tag.getString("Owner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID2 != null) {
            try {
                this.setOwnerUuid(uUID2);
            } catch (Throwable ignored) {
            }
        }
    }
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setOwnerUuid(player.getUuid());
    }
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfEntity = (WolfEntity)target;
                return !wolfEntity.isTamed() || wolfEntity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof HorseBaseEntity && ((HorseBaseEntity)target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
            }
        } else {
            return false;
        }
    }
    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
    public boolean canTarget(LivingEntity target) {
        return this.isOwner(target) ? false : super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Nullable
    public UUID getOwnerUuid() {
        return (UUID)((Optional)this.dataTracker.get(OWNER_UUID)).orElse((Object)null);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
        }

        super.onDeath(source);
    }

    public boolean isSmall() {
        return this.getSize() <= 1;
    }

    protected ParticleEffect getParticles() {
        return ParticleTypes.ITEM_SLIME;
    }

    protected boolean isDisallowedInPeaceful() {
        return this.getSize() > 0;
    }

    public void tick() {
        this.stretch += (this.targetStretch - this.stretch) * 0.5F;
        this.lastStretch = this.stretch;
        super.tick();
        if (this.onGround && !this.onGroundLastTick) {
            int i = this.getSize();

            for(int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 6.2831855F;
                float g = this.random.nextFloat() * 0.5F + 0.5F;
                float h = MathHelper.sin(f) * (float)i * 0.5F * g;
                float k = MathHelper.cos(f) * (float)i * 0.5F * g;
                this.world.addParticle(this.getParticles(), this.getX() + (double)h, this.getY(), this.getZ() + (double)k, 0.0D, 0.0D, 0.0D);
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetStretch = -0.5F;
        } else if (!this.onGround && this.onGroundLastTick) {
            this.targetStretch = 1.0F;
        }

        this.onGroundLastTick = this.onGround;
        this.updateStretch();
    }

    protected void updateStretch() {
        this.targetStretch *= 0.6F;
    }

    protected int getTicksUntilNextJump() {
        return this.random.nextInt(20) + 10;
    }

    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (SLIME_SIZE.equals(data)) {
            this.calculateDimensions();
            this.yaw = this.headYaw;
            this.bodyYaw = this.headYaw;
            if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
                this.onSwimmingStart();
            }
        }

        super.onTrackedDataSet(data);
    }

    public EntityType<? extends OriginSlimeEntity> getType() {
        return (EntityType<? extends OriginSlimeEntity>) super.getType();
    }

    public void remove() {
        int i = this.getSize();
        if (!this.world.isClient && i > 1 && this.isDead()) {
            Text text = this.getCustomName();
            boolean bl = this.isAiDisabled();
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);

            for(int l = 0; l < k; ++l) {
                float g = ((float)(l % 2) - 0.5F) * f;
                float h = ((float)(l / 2) - 0.5F) * f;
                OriginSlimeEntity slimeEntity = (OriginSlimeEntity)this.getType().create(this.world);
                if (this.isPersistent()) {
                    slimeEntity.setPersistent();
                }

                slimeEntity.setCustomName(text);
                slimeEntity.setAiDisabled(bl);
                slimeEntity.setOwnerUuid(getOwnerUuid());
                slimeEntity.setInvulnerable(this.isInvulnerable());
                slimeEntity.setSize(j, true);
                slimeEntity.refreshPositionAndAngles(this.getX() + (double)g, this.getY() + 0.5D, this.getZ() + (double)h, this.random.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(slimeEntity);
            }
        }

        super.remove();
    }

    public void pushAwayFrom(Entity entity) {
        super.pushAwayFrom(entity);

        if (canAttackWithOwner((LivingEntity) entity, getOwner()) && this.canAttack() && !(entity instanceof OriginSlimeEntity)) {
            this.damage((LivingEntity)entity);
        }

    }

    public void onPlayerCollision(PlayerEntity player) {
        if (this.canAttack()) {
            this.damage(player);
        }

    }


    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.squaredDistanceTo(target) < 0.6D * (double)i * 0.6D * (double)i && this.canSee(target) && target.damage(DamageSource.mob(this), this.getDamageAmount())) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dealDamage(this, target);
            }
        }

    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.625F * dimensions.height;
    }

    protected boolean canAttack() {
        return true;
    }

    protected float getDamageAmount() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return this.isSmall() ? SoundEvents.ENTITY_SLIME_HURT_SMALL : SoundEvents.ENTITY_SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isSmall() ? SoundEvents.ENTITY_SLIME_DEATH_SMALL : SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isSmall() ? SoundEvents.ENTITY_SLIME_SQUISH_SMALL : SoundEvents.ENTITY_SLIME_SQUISH;
    }

    protected Identifier getLootTableId() {
        return this.getSize() == 1 ? this.getType().getLootTableId() : LootTables.EMPTY;
    }

    public static boolean canSpawn(EntityType<OriginSlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            if (Objects.equals(world.method_31081(pos), Optional.of(BiomeKeys.SWAMP)) && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
                return canMobSpawn(type, world, spawnReason, pos, random);
            }

            if (!(world instanceof StructureWorldAccess)) {
                return false;
            }

            ChunkPos chunkPos = new ChunkPos(pos);
            boolean bl = ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, ((StructureWorldAccess)world).getSeed(), 987234911L).nextInt(10) == 0;
            if (random.nextInt(10) == 0 && bl && pos.getY() < 40) {
                return canMobSpawn(type, world, spawnReason, pos, random);
            }
        }

        return false;
    }

    protected float getSoundVolume() {
        return 0.4F * (float)this.getSize();
    }

    public int getLookPitchSpeed() {
        return 0;
    }

    protected boolean makesJumpSound() {
        return this.getSize() > 0;
    }

    protected void jump() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x, (double)this.getJumpVelocity(), vec3d.z);
        this.velocityDirty = true;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        int i = this.random.nextInt(3);
        if (i < 2 && this.random.nextFloat() < 0.5F * difficulty.getClampedLocalDifficulty()) {
            ++i;
        }

        int j = 1 << i;
        this.setSize(j, true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    private float getJumpSoundPitch() {
        float f = this.isSmall() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    protected SoundEvent getJumpSound() {
        return this.isSmall() ? SoundEvents.ENTITY_SLIME_JUMP_SMALL : SoundEvents.ENTITY_SLIME_JUMP;
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.255F * (float)this.getSize());
    }

    static {
        SLIME_SIZE = DataTracker.registerData(OriginSlimeEntity.class, TrackedDataHandlerRegistry.INTEGER);
        OWNER_UUID = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }

    static class MoveGoal extends Goal {
        private final OriginSlimeEntity slime;

        public MoveGoal(OriginSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        }

        public boolean canStart() {
            return !this.slime.hasVehicle();
        }

        public void tick() {
            ((OriginSlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).move(1.0D);
        }
    }

    static class SwimmingGoal extends Goal {
        private final OriginSlimeEntity slime;

        public SwimmingGoal(OriginSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
            slime.getNavigation().setCanSwim(true);
        }

        public boolean canStart() {
            return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof OriginSlimeEntity.SlimeMoveControl;
        }

        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().setActive();
            }

            ((OriginSlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).move(1.2D);
        }
    }

    static class RandomLookGoal extends Goal {
        private final OriginSlimeEntity slime;
        private float targetYaw;
        private int timer;

        public RandomLookGoal(OriginSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        public boolean canStart() {
            return this.slime.getTarget() == null && (this.slime.onGround || this.slime.isTouchingWater() || this.slime.isInLava() || this.slime.hasStatusEffect(StatusEffects.LEVITATION)) && this.slime.getMoveControl() instanceof OriginSlimeEntity.SlimeMoveControl;
        }

        public void tick() {
            if (--this.timer <= 0) {
                this.timer = 40 + this.slime.getRandom().nextInt(60);
                this.targetYaw = (float)this.slime.getRandom().nextInt(360);
            }

            ((OriginSlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).look(this.targetYaw, false);
        }
    }

    static class FaceTowardTargetGoal extends Goal {
        private final OriginSlimeEntity slime;
        private int ticksLeft;

        public FaceTowardTargetGoal(OriginSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.slime.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                return livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.invulnerable ? false : this.slime.getMoveControl() instanceof OriginSlimeEntity.SlimeMoveControl;
            }
        }

        public void start() {
            this.ticksLeft = 300;
            super.start();
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.slime.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.invulnerable) {
                return false;
            } else {
                return --this.ticksLeft > 0;
            }
        }

        public void tick() {
            this.slime.lookAtEntity(this.slime.getTarget(), 10.0F, 10.0F);
            ((OriginSlimeEntity.SlimeMoveControl)this.slime.getMoveControl()).look(this.slime.yaw, this.slime.canAttack());
        }
    }

    static class SlimeMoveControl extends MoveControl {
        private float targetYaw;
        private int ticksUntilJump;
        private final OriginSlimeEntity slime;
        private boolean jumpOften;

        public SlimeMoveControl(OriginSlimeEntity slime) {
            super(slime);
            this.slime = slime;
            this.targetYaw = 180.0F * slime.yaw / 3.1415927F;
        }

        public void look(float targetYaw, boolean jumpOften) {
            this.targetYaw = targetYaw;
            this.jumpOften = jumpOften;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = MoveControl.State.MOVE_TO;
        }

        public void tick() {
            this.entity.yaw = this.changeAngle(this.entity.yaw, this.targetYaw, 90.0F);
            this.entity.headYaw = this.entity.yaw;
            this.entity.bodyYaw = this.entity.yaw;
            if (this.state != MoveControl.State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = MoveControl.State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.slime.getTicksUntilNextJump();
                        if (this.jumpOften) {
                            this.ticksUntilJump /= 3;
                        }

                        this.slime.getJumpControl().setActive();
                        if (this.slime.makesJumpSound()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getJumpSoundPitch());
                        }
                    } else {
                        this.slime.sidewaysSpeed = 0.0F;
                        this.slime.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }

            }
        }
    }
}
