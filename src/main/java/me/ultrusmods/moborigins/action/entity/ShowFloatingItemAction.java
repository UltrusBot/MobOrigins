package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/** {DOCS}
    NAME: Show Floating Item
    DESC: Shows a floating item to the player.
    PARAMS:
        - {item_stack} {ItemStack} {https://origins.readthedocs.io/en/latest/types/data_types/item_stack/} { }  {The item stack to show.}
    EXAMPLE:
{
  "type": "origins:action_when_damage_taken",
  "entity_action": {
    "type": "moborigins:show_floating_item",
    "item_stack": {
      "item": "minecraft:creeper_head"
    }
  },
  "cooldown": 1
}
    POWER_DESC: Shows a floating creeper head to the player when they take damage.
 */
public class ShowFloatingItemAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("show_floating_item"), new SerializableData()
                .add("item_stack", SerializableDataTypes.ITEM_STACK),
                ((instance, entity) -> {
                    if (entity.getWorld().isClient() && entity instanceof PlayerEntity playerEntity && playerEntity.getUuid() == MinecraftClient.getInstance().player.getUuid()) {
                        MinecraftClient.getInstance().gameRenderer.showFloatingItem(instance.get("item_stack"));
                    } else if (entity instanceof ServerPlayerEntity playerEntity) {
                        ServerPlayNetworking.send(playerEntity, MobOriginsMod.id("floating_item"), PacketByteBufs.create().writeItemStack(instance.get("item_stack")));
                    }
                }));
    }
}
