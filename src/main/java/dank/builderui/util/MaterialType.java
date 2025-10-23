package dank.builderui.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * Enum representing material types for building structures.
 * Each material has a primary block, secondary block, and decorative block.
 */
public enum MaterialType {
    WOOD("Wood", Blocks.OAK_PLANKS, Blocks.OAK_LOG, Blocks.OAK_FENCE),
    STONE("Stone", Blocks.STONE, Blocks.COBBLESTONE, Blocks.STONE_BRICKS),
    BRICK("Brick", Blocks.BRICKS, Blocks.RED_SANDSTONE, Blocks.BRICK_STAIRS),
    GLASS("Glass", Blocks.GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.GLASS_PANE),
    SANDSTONE("Sandstone", Blocks.SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.CHISELED_SANDSTONE),
    DARK_OAK("Dark Oak", Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_FENCE),
    QUARTZ("Quartz", Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.CHISELED_QUARTZ_BLOCK),
    PRISMARINE("Prismarine", Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE),
    CONCRETE("Concrete", Blocks.WHITE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.GRAY_CONCRETE);
    
    private final String displayName;
    private final Block primaryBlock;
    private final Block secondaryBlock;
    private final Block decorativeBlock;
    
    MaterialType(String displayName, Block primaryBlock, Block secondaryBlock, Block decorativeBlock) {
        this.displayName = displayName;
        this.primaryBlock = primaryBlock;
        this.secondaryBlock = secondaryBlock;
        this.decorativeBlock = decorativeBlock;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public Block getPrimaryBlock() {
        return primaryBlock;
    }
    
    public Block getSecondaryBlock() {
        return secondaryBlock;
    }
    
    public Block getDecorativeBlock() {
        return decorativeBlock;
    }
}
