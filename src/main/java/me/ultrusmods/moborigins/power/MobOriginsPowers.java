package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import me.ultrusmods.moborigins.MobOriginsMod;

/** {DOCS} {IGNORE}
 *
 */

/**
 * Defines the hardcoded powers for Mob Origins.
 */
public class MobOriginsPowers {
    public static final PowerType<Power> STRONGER_SNOWBALLS = new PowerTypeReference<>(MobOriginsMod.id("stronger_snowballs"));
    public static final PowerType<Power> PILLAGER_ALIGNED = new PowerTypeReference<>(MobOriginsMod.id("pillager_aligned"));
    public static final PowerType<Power> BETTER_POTIONS = new PowerTypeReference<>(MobOriginsMod.id("better_potions")); //TODO: Unhardcode
    public static final PowerType<Power> RIDEABLE_CREATURE = new PowerTypeReference<>(MobOriginsMod.id("rideable_creature_riding"));
    public static final PowerType<Power> QUEEN_BEE = new PowerTypeReference<>(MobOriginsMod.id("queen_bee"));
    public static final PowerType<Power> ITEM_COLLECTOR = new PowerTypeReference<>(MobOriginsMod.id("item_collector"));
    public static final PowerType<Power> CAREFUL_GATHERER = new PowerTypeReference<>(MobOriginsMod.id("careful_gatherer"));
}
