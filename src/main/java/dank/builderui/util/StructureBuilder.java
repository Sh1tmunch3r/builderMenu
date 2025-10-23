package dank.builderui.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Enhanced structure builder with support for multiple build types and configurations.
 * Provides methods to build various structures with customizable materials and sizes.
 */
public class StructureBuilder {

    /**
     * Builds a structure based on the provided configuration.
     */
    public static void build(PlayerEntity player, BuildConfig config) {
        if (player == null) return;
        World world = player.getWorld();
        if (world.isClient) return;

        BlockPos origin = player.getBlockPos().add(config.getOffsetX(), config.getOffsetY(), config.getOffsetZ());
        Map<BlockPos, BlockState> originalStates = new HashMap<>();

        switch (config.getBuildType()) {
            case HOUSE -> buildHouseInternal(world, origin, config, originalStates);
            case TOWER -> buildTowerInternal(world, origin, config, originalStates);
            case MANSION -> buildMansionInternal(world, origin, config, originalStates);
            case FARM -> buildFarmInternal(world, origin, config, originalStates);
            case CASTLE -> buildCastleInternal(world, origin, config, originalStates);
            case BRIDGE -> buildBridgeInternal(world, origin, config, originalStates);
            case FOUNTAIN -> buildFountainInternal(world, origin, config, originalStates);
            case TREEHOUSE -> buildTreehouseInternal(world, origin, config, originalStates);
            case WALL -> buildWallInternal(world, origin, config, originalStates);
            case ROAD -> buildRoadInternal(world, origin, config, originalStates);
        }

        // Record action for undo
        UndoManager.recordAction(player.getUuid(), originalStates);
    }

    /**
     * Legacy method for backwards compatibility.
     */
    public static void buildHouse(PlayerEntity player) {
        BuildConfig config = new BuildConfig(BuildType.HOUSE);
        build(player, config);
    }

    /**
     * Legacy method for backwards compatibility.
     */
    public static void buildTower(PlayerEntity player) {
        BuildConfig config = new BuildConfig(BuildType.TOWER);
        build(player, config);
    }

    private static void buildHouseInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block primaryBlock = config.getMaterial().getPrimaryBlock();
        Block roofBlock = config.getMaterial().getSecondaryBlock();
        float scale = config.getSize().getScale();
        
        int width = (int)(5 * scale);
        int height = (int)(4 * scale);
        int depth = (int)(5 * scale);

        // Build walls and floor
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < height; y++) {
                    BlockPos pos = origin.add(x, y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    
                    boolean wall = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                    if (wall || y == 0) {
                        world.setBlockState(pos, primaryBlock.getDefaultState());
                    }
                }
            }
        }

        // Add door
        BlockPos doorPos = origin.add(width / 2, 1, 0);
        originalStates.put(doorPos, world.getBlockState(doorPos));
        world.setBlockState(doorPos, Blocks.AIR.getDefaultState());

        // Build roof
        for (int x = -1; x <= width; x++) {
            for (int z = -1; z <= depth; z++) {
                BlockPos roofPos = origin.add(x, height, z);
                originalStates.put(roofPos, world.getBlockState(roofPos));
                world.setBlockState(roofPos, roofBlock.getDefaultState());
            }
        }
    }

    private static void buildTowerInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block block = config.getMaterial().getPrimaryBlock();
        float scale = config.getSize().getScale();
        int height = (int)(10 * scale);

        for (int y = 0; y < height; y++) {
            BlockPos pos = origin.up(y);
            originalStates.put(pos, world.getBlockState(pos));
            world.setBlockState(pos, block.getDefaultState());
        }
    }

    private static void buildMansionInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block primaryBlock = config.getMaterial().getPrimaryBlock();
        Block secondaryBlock = config.getMaterial().getSecondaryBlock();
        Block decorativeBlock = config.getMaterial().getDecorativeBlock();
        float scale = config.getSize().getScale();
        
        int width = (int)(12 * scale);
        int height = (int)(8 * scale);
        int depth = (int)(12 * scale);

        // Build main structure with multiple floors
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < height; y++) {
                    BlockPos pos = origin.add(x, y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    
                    boolean wall = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                    boolean floor = y % 4 == 0;
                    
                    if (wall) {
                        // Alternate primary and decorative blocks for walls
                        Block wallBlock = (x + z + y) % 2 == 0 ? primaryBlock : decorativeBlock;
                        world.setBlockState(pos, wallBlock.getDefaultState());
                    } else if (floor) {
                        world.setBlockState(pos, secondaryBlock.getDefaultState());
                    }
                }
            }
        }

        // Add entrance
        for (int i = 0; i < 2; i++) {
            BlockPos doorPos = origin.add(width / 2, i + 1, 0);
            originalStates.put(doorPos, world.getBlockState(doorPos));
            world.setBlockState(doorPos, Blocks.AIR.getDefaultState());
        }

        // Add decorative roof
        for (int x = -1; x <= width; x++) {
            for (int z = -1; z <= depth; z++) {
                BlockPos roofPos = origin.add(x, height, z);
                originalStates.put(roofPos, world.getBlockState(roofPos));
                world.setBlockState(roofPos, decorativeBlock.getDefaultState());
            }
        }
    }

    private static void buildFarmInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block fenceBlock = Blocks.OAK_FENCE;
        float scale = config.getSize().getScale();
        
        int width = (int)(8 * scale);
        int depth = (int)(8 * scale);

        // Build fence perimeter
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                if (x == 0 || x == width - 1 || z == 0 || z == depth - 1) {
                    BlockPos pos = origin.add(x, 0, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    world.setBlockState(pos, fenceBlock.getDefaultState());
                }
            }
        }

        // Add farmland inside
        for (int x = 1; x < width - 1; x++) {
            for (int z = 1; z < depth - 1; z++) {
                BlockPos pos = origin.add(x, 0, z);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            }
        }

        // Add water source in center
        BlockPos waterPos = origin.add(width / 2, 0, depth / 2);
        originalStates.put(waterPos, world.getBlockState(waterPos));
        world.setBlockState(waterPos, Blocks.WATER.getDefaultState());
    }

    private static void buildCastleInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block primaryBlock = config.getMaterial().getPrimaryBlock();
        Block secondaryBlock = config.getMaterial().getSecondaryBlock();
        float scale = config.getSize().getScale();
        
        int width = (int)(20 * scale);
        int height = (int)(12 * scale);
        int depth = (int)(20 * scale);

        // Build outer walls
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < height; y++) {
                    BlockPos pos = origin.add(x, y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    
                    boolean outerWall = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                    if (outerWall || y == 0) {
                        world.setBlockState(pos, primaryBlock.getDefaultState());
                    }
                }
            }
        }

        // Build corner towers
        int towerHeight = (int)(15 * scale);
        int[][] corners = {{0, 0}, {width - 1, 0}, {0, depth - 1}, {width - 1, depth - 1}};
        
        for (int[] corner : corners) {
            for (int y = 0; y < towerHeight; y++) {
                BlockPos pos = origin.add(corner[0], y, corner[1]);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, secondaryBlock.getDefaultState());
            }
        }

        // Add entrance
        for (int i = 0; i < 3; i++) {
            BlockPos doorPos = origin.add(width / 2, i + 1, 0);
            originalStates.put(doorPos, world.getBlockState(doorPos));
            world.setBlockState(doorPos, Blocks.AIR.getDefaultState());
        }
    }

    private static void buildBridgeInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block primaryBlock = config.getMaterial().getPrimaryBlock();
        Block railBlock = config.getMaterial().getDecorativeBlock();
        float scale = config.getSize().getScale();
        
        int length = (int)(15 * scale);
        int width = (int)(3 * scale);

        // Build bridge deck
        for (int x = 0; x < length; x++) {
            for (int z = 0; z < width; z++) {
                BlockPos pos = origin.add(x, 0, z);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, primaryBlock.getDefaultState());
                
                // Add railings on sides
                if (z == 0 || z == width - 1) {
                    BlockPos railPos = origin.add(x, 1, z);
                    originalStates.put(railPos, world.getBlockState(railPos));
                    world.setBlockState(railPos, railBlock.getDefaultState());
                }
            }
        }
    }

    private static void buildFountainInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block block = config.getMaterial().getPrimaryBlock();
        float scale = config.getSize().getScale();
        
        int radius = (int)(3 * scale);
        int height = (int)(2 * scale);

        // Build circular base
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    BlockPos pos = origin.add(x, 0, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    world.setBlockState(pos, block.getDefaultState());
                }
            }
        }

        // Build center pillar with water
        for (int y = 0; y <= height; y++) {
            BlockPos pos = origin.up(y);
            originalStates.put(pos, world.getBlockState(pos));
            if (y == height) {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
            } else {
                world.setBlockState(pos, block.getDefaultState());
            }
        }
    }

    private static void buildTreehouseInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block logBlock = Blocks.OAK_LOG;
        Block plankBlock = config.getMaterial().getPrimaryBlock();
        Block leavesBlock = Blocks.OAK_LEAVES;
        float scale = config.getSize().getScale();
        
        int trunkHeight = (int)(8 * scale);
        int houseSize = (int)(4 * scale);

        // Build tree trunk
        for (int y = 0; y < trunkHeight; y++) {
            BlockPos pos = origin.up(y);
            originalStates.put(pos, world.getBlockState(pos));
            world.setBlockState(pos, logBlock.getDefaultState());
        }

        // Build platform
        for (int x = -houseSize / 2; x <= houseSize / 2; x++) {
            for (int z = -houseSize / 2; z <= houseSize / 2; z++) {
                BlockPos pos = origin.add(x, trunkHeight, z);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, plankBlock.getDefaultState());
            }
        }

        // Build small house on platform
        for (int x = -houseSize / 2; x <= houseSize / 2; x++) {
            for (int z = -houseSize / 2; z <= houseSize / 2; z++) {
                for (int y = 1; y < 3; y++) {
                    BlockPos pos = origin.add(x, trunkHeight + y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    
                    boolean wall = x == -houseSize / 2 || x == houseSize / 2 || z == -houseSize / 2 || z == houseSize / 2;
                    if (wall) {
                        world.setBlockState(pos, plankBlock.getDefaultState());
                    }
                }
            }
        }

        // Add leaves around top
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = 0; y < 2; y++) {
                    if (x * x + z * z <= 5) {
                        BlockPos pos = origin.add(x, trunkHeight + 3 + y, z);
                        originalStates.put(pos, world.getBlockState(pos));
                        world.setBlockState(pos, leavesBlock.getDefaultState());
                    }
                }
            }
        }
    }

    private static void buildWallInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block block = config.getMaterial().getPrimaryBlock();
        float scale = config.getSize().getScale();
        
        int length = (int)(20 * scale);
        int height = (int)(4 * scale);

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                BlockPos pos = origin.add(x, y, 0);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, block.getDefaultState());
            }
        }
    }

    private static void buildRoadInternal(World world, BlockPos origin, BuildConfig config, Map<BlockPos, BlockState> originalStates) {
        Block block = config.getMaterial().getPrimaryBlock();
        float scale = config.getSize().getScale();
        
        int length = (int)(20 * scale);
        int width = (int)(3 * scale);

        for (int x = 0; x < length; x++) {
            for (int z = 0; z < width; z++) {
                BlockPos pos = origin.add(x, 0, z);
                originalStates.put(pos, world.getBlockState(pos));
                world.setBlockState(pos, block.getDefaultState());
            }
        }
    }

    /**
     * Gets information about a specific build type.
     */
    public static BuildInfo getBuildInfo(BuildType buildType, BuildSize size) {
        BuildInfo info;
        switch (buildType) {
            case HOUSE -> {
                info = new BuildInfo(buildType, "A cozy house with walls, floor, and roof", 5);
                info.addBlockRequirement("Primary Material", (int)(50 * size.getScale()));
                info.addBlockRequirement("Roof Material", (int)(36 * size.getScale()));
            }
            case TOWER -> {
                info = new BuildInfo(buildType, "A tall tower reaching to the sky", 3);
                info.addBlockRequirement("Primary Material", (int)(10 * size.getScale()));
            }
            case MANSION -> {
                info = new BuildInfo(buildType, "A large multi-floor mansion", 15);
                info.addBlockRequirement("Primary Material", (int)(400 * size.getScale()));
                info.addBlockRequirement("Secondary Material", (int)(150 * size.getScale()));
                info.addBlockRequirement("Decorative Material", (int)(100 * size.getScale()));
            }
            case FARM -> {
                info = new BuildInfo(buildType, "A fenced farm with farmland and water", 8);
                info.addBlockRequirement("Fence", (int)(32 * size.getScale()));
                info.addBlockRequirement("Farmland", (int)(36 * size.getScale()));
                info.addBlockRequirement("Water", 1);
            }
            case CASTLE -> {
                info = new BuildInfo(buildType, "A fortified castle with towers", 25);
                info.addBlockRequirement("Primary Material", (int)(600 * size.getScale()));
                info.addBlockRequirement("Tower Material", (int)(200 * size.getScale()));
            }
            case BRIDGE -> {
                info = new BuildInfo(buildType, "A sturdy bridge with railings", 6);
                info.addBlockRequirement("Primary Material", (int)(45 * size.getScale()));
                info.addBlockRequirement("Railing Material", (int)(30 * size.getScale()));
            }
            case FOUNTAIN -> {
                info = new BuildInfo(buildType, "A decorative fountain with water", 4);
                info.addBlockRequirement("Primary Material", (int)(30 * size.getScale()));
                info.addBlockRequirement("Water", 1);
            }
            case TREEHOUSE -> {
                info = new BuildInfo(buildType, "A house built in a tree", 10);
                info.addBlockRequirement("Wood Logs", (int)(8 * size.getScale()));
                info.addBlockRequirement("Planks", (int)(40 * size.getScale()));
                info.addBlockRequirement("Leaves", (int)(20 * size.getScale()));
            }
            case WALL -> {
                info = new BuildInfo(buildType, "A defensive wall", 5);
                info.addBlockRequirement("Primary Material", (int)(80 * size.getScale()));
            }
            case ROAD -> {
                info = new BuildInfo(buildType, "A paved road", 4);
                info.addBlockRequirement("Primary Material", (int)(60 * size.getScale()));
            }
            default -> {
                info = new BuildInfo(buildType, "Custom structure", 10);
            }
        }
        return info;
    }
}
