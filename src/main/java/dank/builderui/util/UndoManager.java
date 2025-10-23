package dank.builderui.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Manages undo functionality for structure building.
 * Stores the original block states before building so they can be restored.
 */
public class UndoManager {
    private static final int MAX_UNDO_HISTORY = 10;
    private static final Map<UUID, List<BuildAction>> undoHistory = new HashMap<>();
    
    /**
     * Records a build action for potential undo.
     */
    public static void recordAction(UUID playerId, Map<BlockPos, BlockState> originalStates) {
        List<BuildAction> history = undoHistory.computeIfAbsent(playerId, k -> new ArrayList<>());
        
        BuildAction action = new BuildAction(originalStates, System.currentTimeMillis());
        history.add(action);
        
        // Keep only the last MAX_UNDO_HISTORY actions
        if (history.size() > MAX_UNDO_HISTORY) {
            history.remove(0);
        }
    }
    
    /**
     * Undoes the last build action for the specified player.
     * Returns true if undo was successful, false if there's nothing to undo.
     */
    public static boolean undo(UUID playerId, World world) {
        List<BuildAction> history = undoHistory.get(playerId);
        if (history == null || history.isEmpty()) {
            return false;
        }
        
        BuildAction lastAction = history.remove(history.size() - 1);
        lastAction.restore(world);
        return true;
    }
    
    /**
     * Clears the undo history for a specific player.
     */
    public static void clearHistory(UUID playerId) {
        undoHistory.remove(playerId);
    }
    
    /**
     * Returns true if the player has actions that can be undone.
     */
    public static boolean canUndo(UUID playerId) {
        List<BuildAction> history = undoHistory.get(playerId);
        return history != null && !history.isEmpty();
    }
    
    /**
     * Represents a single build action that can be undone.
     */
    private static class BuildAction {
        private final Map<BlockPos, BlockState> originalStates;
        private final long timestamp;
        
        BuildAction(Map<BlockPos, BlockState> originalStates, long timestamp) {
            this.originalStates = new HashMap<>(originalStates);
            this.timestamp = timestamp;
        }
        
        void restore(World world) {
            for (Map.Entry<BlockPos, BlockState> entry : originalStates.entrySet()) {
                world.setBlockState(entry.getKey(), entry.getValue());
            }
        }
    }
}
