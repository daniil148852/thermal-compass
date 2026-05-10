package com.example.thermalcompass;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class ThermalCompassItem extends CompassItem {
    public ThermalCompassItem(Settings settings) {
        super(settings);
    }

    public static void tickClient(PlayerEntity player) {
        if (player.getWorld().isClient && player.getMainHandStack().getItem() instanceof ThermalCompassItem) {
            findNearestHeatSource(player);
        }
    }

    private static void findNearestHeatSource(PlayerEntity player) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();
        int radius = 40;
        double closestDist = Double.MAX_VALUE;
        boolean found = false;

        for (int x = -radius; x <= radius; x += 2) {  // optimized a bit
            for (int y = -20; y <= 20; y += 2) {
                for (int z = -radius; z <= radius; z += 2) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (world.getBlockState(pos).getBlock() == net.minecraft.block.Blocks.LAVA
                        || world.getBlockState(pos).getBlock() == net.minecraft.block.Blocks.MAGMA_BLOCK) {
                        double dist = playerPos.getSquaredDistance(pos);
                        if (dist < closestDist) {
                            closestDist = dist;
                            found = true;
                        }
                    }
                }
            }
        }

        if (found) {
            int distance = (int) Math.sqrt(closestDist);
            int heatLevel = Math.max(0, Math.min(8, 10 - (distance / 5)));
            StringBuilder indicator = new StringBuilder();
            for (int i = 0; i < heatLevel; i++) {
                indicator.append("🔥");
            }
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(
                Text.literal("§6" + indicator.toString() + " §f" + distance + " blocks"), false);
        } else {
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.literal(""), false);
        }
    }
}