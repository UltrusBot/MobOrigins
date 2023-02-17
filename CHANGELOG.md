# Mob Origins v1.10.0-beta
## Changes and General Additions
- **Slime Origin**
  - Can now dye themselves, by right clicking while holding a dye. This tints them, blending the color like leather armor.
  - Clicking on a cauldron with water will reset their color back to the slime green.
  - Summoned slimes will now be the same color as the player.
  - Summoned slimes no longer damage their owner.
- **Fox Origin**
  - Better Berries now uses the `#minecraft:fox_food` tag, so modded berries will be buffed as well. The power description updated to reflect this.
- **Strider Origin**
  - No weird desync when riding a strider origin.
  - You don't see the player sitting on you, unless you go into third person.
- Channeling Override Power Type change:
  - Added a hit_entity_action field that executes an entity action on the entity that was hit by the lightning bolt.
- Added built-in datapack that disables all origins from Mob Origins.
- The nearby_entities instead of having a entity_type field, has a bientity_condition field. This allows for more conditional checking of nearby entities. The player_box_multiplier field was also renamed to multiplier.

## Removals
- `moborigins:modify_block_slipperiness` removed in favor of `origins:modify_slipperiness`
- `moborigins:spiked` removed in favor of using a `origins:action_when_hit` with a chance to damage.
- Removed the Vexed & Hero of the Illagers status effects.


## New Power Types, Actions, and Conditions
- Added documentation which can be found [here](https://moborigins.readthedocs.io/en/latest/)

### Power Types
- [Add Experience To Resource](https://moborigins.ultrusmods.me/en/latest/https://moborigins.ultrusmods.me/en/latest/power_types/add_experience_to_resource)
- [Biome Model Color](https://moborigins.ultrusmods.me/en/latest/https://moborigins.ultrusmods.me/en/latest/power_types/biome_model_color)
- [Bouncy](https://moborigins.ultrusmods.me/en/latest/https://moborigins.ultrusmods.me/en/latest/power_types/bouncy)
- [Custom Sleep Block](https://moborigins.ultrusmods.me/en/latest/power_types/custom_sleep_block)
- [Dyeable Model Color](https://moborigins.ultrusmods.me/en/latest/power_types/dyeable_model_color)
- [Fall Sounds](https://moborigins.ultrusmods.me/en/latest/power_types/fall_sounds)
- [Fog](https://moborigins.ultrusmods.me/en/latest/power_types/fog)
- [Hostile Axolotls](https://moborigins.ultrusmods.me/en/latest/power_types/hostile_axolotls)
- [Illuminate](https://moborigins.ultrusmods.me/en/latest/power_types/illuminate)
- [Mimic Enchant](https://moborigins.ultrusmods.me/en/latest/power_types/mimic_enchant)
- [Modify Attack Distance Scaling Factor](https://moborigins.ultrusmods.me/en/latest/power_types/modify_attack_distance_scaling_factor)
- [Modify Villager Reputation](https://moborigins.ultrusmods.me/en/latest/power_types/modify_villager_reputation)
- [Walk On Powder Snow](https://moborigins.ultrusmods.me/en/latest/power_types/walk_on_powder_snow)

### Conditions
- Item
  - [Is Dye](https://moborigins.ultrusmods.me/en/latest/conditions/item/is_dye)
- Block
  - [Is Air](https://moborigins.ultrusmods.me/en/latest/conditions/block/is_air)
  - [Is Solid](https://moborigins.ultrusmods.me/en/latest/conditions/block/is_solid)
- Damage
  - [Is Magic](https://moborigins.ultrusmods.me/en/latest/conditions/damage/is_magic)
- Entity
  - [Has Item Cooldown](https://moborigins.ultrusmods.me/en/latest/conditions/entity/has_item_cooldown)
  - [In Raid Area](https://moborigins.ultrusmods.me/en/latest/conditions/entity/in_raid_area)
  - [Is First Person](https://moborigins.ultrusmods.me/en/latest/conditions/entity/is_first_person)
### Actions
- Bi-entity
  - [Set Angered At](https://moborigins.ultrusmods.me/en/latest/actions/bientity/set_angered_at)
- Block
  - [Decrement Cauldron Fluid](https://moborigins.ultrusmods.me/en/latest/actions/block/decrement_cauldron_fluid)
  - [Grow Block](https://moborigins.ultrusmods.me/en/latest/actions/block/grow_block)
- Entity
  - [Consume Dye](https://moborigins.ultrusmods.me/en/latest/actions/entity/consume_dye)