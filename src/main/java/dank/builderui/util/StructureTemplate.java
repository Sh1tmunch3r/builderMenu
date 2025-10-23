package dank.builderui.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a template for a structure that can be built.
 * Contains relative block positions and their corresponding block states.
 */
public class StructureTemplate {
    private final String name;
    private final Map<BlockPos, BlockState> blocks;
    private final int width;
    private final int height;
    private final int depth;
    
    public StructureTemplate(String name, int width, int height, int depth) {
        this.name = name;
        this.blocks = new HashMap<>();
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    
    /**
     * Adds a block to the template at the specified relative position.
     */
    public void setBlock(int x, int y, int z, BlockState state) {
        blocks.put(new BlockPos(x, y, z), state);
    }
    
    /**
     * Gets all blocks in the template.
     */
    public Map<BlockPos, BlockState> getBlocks() {
        return new HashMap<>(blocks);
    }
    
    /**
     * Returns a copy of this template with all blocks replaced with the specified material.
     */
    public StructureTemplate withMaterial(MaterialType material) {
        StructureTemplate copy = new StructureTemplate(this.name, this.width, this.height, this.depth);
        
        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockPos pos = entry.getKey();
            // Simple material replacement - in practice, this would be more sophisticated
            Block newBlock = material.getPrimaryBlock();
            copy.setBlock(pos.getX(), pos.getY(), pos.getZ(), newBlock.getDefaultState());
        }
        
        return copy;
    }
    
    /**
     * Returns a scaled version of this template.
     */
    public StructureTemplate withScale(float scale) {
        int newWidth = Math.max(1, (int)(width * scale));
        int newHeight = Math.max(1, (int)(height * scale));
        int newDepth = Math.max(1, (int)(depth * scale));
        
        StructureTemplate scaled = new StructureTemplate(this.name, newWidth, newHeight, newDepth);
        
        // Scale the blocks - this is a simplified version
        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockPos pos = entry.getKey();
            int newX = (int)(pos.getX() * scale);
            int newY = (int)(pos.getY() * scale);
            int newZ = (int)(pos.getZ() * scale);
            scaled.setBlock(newX, newY, newZ, entry.getValue());
        }
        
        return scaled;
    }
    
    public String getName() {
        return name;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public int getBlockCount() {
        return blocks.size();
    }
}
