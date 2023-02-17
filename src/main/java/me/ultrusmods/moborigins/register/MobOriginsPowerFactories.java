package me.ultrusmods.moborigins.register;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.power.*;
import net.minecraft.util.registry.Registry;

public class MobOriginsPowerFactories {

    public static void register() {
        // TODO: VariableMinMaxResource?
        register(ActionOnBreedAnimalPower::createFactory);
        register(ActionOnEntityTamePower::createFactory);
        register(AddExperienceToResourcePower::createFactory);
        register(CustomSleepPower::createFactory);
        register(DyeableModelColorPower::createFactory);
        register(FogPower::createFactory);
//        register(HealthBossBarPower::createFactory);
//        register(ResourceBossBarPower::createFactory);
        register(IlluminatePower::createFactory);
        register(MimicEnchantPower::createFactory);
        register(PowderSnowPower::createFactory);
        register(RemoveMobHostilityPower::createFactory);
        register(RiptideOverridePower::createFactory);
        register(ChannelingOverridePower::createFactory);
        register(TotemChancePower::createFactory);
        register(ModifyAttackDistanceScalingFactorPower::createFactory);
        register(BiomeModelColorPower::createFactory);
        register(FallSoundPower::createFactory);
//        register(SlidingPower::createFactory);
        register(ModifyReputationPower::createFactory);
        register(BouncePower::createFactory);
        register(SnowTrailPower::createFactory);
        register(() -> Power.createSimpleFactory(HostileAxolotlsPower::new, MobOriginsMod.id("hostile_axolotls")));
    }

    private static void register(PowerFactorySupplier<?> supplier) {
        register(supplier.createFactory());
    }

    private static void register(PowerFactory<?> factory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, factory.getSerializerId(), factory);
    }
}
