package dank.builderui.util;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureBuilder {

    public static void buildHouse(PlayerEntity player) {
        if (player == null) return;
        World world = player.getWorld();

        // Only run this on the server side
        if (world.isClient) return;

        BlockPos origin = player.getBlockPos().add(2, 0, 2);

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                for (int y = 0; y < 4; y++) {
                    BlockPos pos = origin.add(x, y, z);
                    boolean wall = x == 0 || x == 4 || z == 0 || z == 4;
                    if (wall || y == 0) {
                        world.setBlockState(pos, Blocks.OAK_PLANKS.getDefaultState());
                    }
                }
            }
        }

        // Simple roof
        for (int x = -1; x < 6; x++) {
            for (int z = -1; z < 6; z++) {
                world.setBlockState(origin.add(x, 4, z), Blocks.BRICKS.getDefaultState());
            }
        }
    }

    public static void buildTower(PlayerEntity player) {
        if (player == null) return;
        World world = player.getWorld();
        if (world.isClient) return;

        BlockPos base = player.getBlockPos().add(2, 0, 2);
        for (int y = 0; y < 10; y++) {
            world.setBlockState(base.up(y), Blocks.STONE_BRICKS.getDefaultState());
        }
    }
}
