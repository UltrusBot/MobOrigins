tag @s add eldercurse
execute as @p[tag=!eldercurse,distance=..5] run particle minecraft:elder_guardian ~ ~ ~ ~ ~ ~ 0 1 normal
execute as @p[tag=!eldercurse,distance=..5] run effect give @s minecraft:mining_fatigue 30 1 false
execute as @p[tag=!eldercurse,distance=..5] run playsound minecraft:entity.elder_guardian.curse hostile @s ~ ~ ~ 1 1
tag @s remove eldercurse