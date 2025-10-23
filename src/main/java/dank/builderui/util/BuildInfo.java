package dank.builderui.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about a specific build, including blocks needed and estimated time.
 */
public class BuildInfo {
    private final BuildType buildType;
    private final String description;
    private final Map<String, Integer> blocksNeeded;
    private final int estimatedSeconds;
    
    public BuildInfo(BuildType buildType, String description, int estimatedSeconds) {
        this.buildType = buildType;
        this.description = description;
        this.blocksNeeded = new HashMap<>();
        this.estimatedSeconds = estimatedSeconds;
    }
    
    public void addBlockRequirement(String blockName, int count) {
        blocksNeeded.put(blockName, count);
    }
    
    public BuildType getBuildType() {
        return buildType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Map<String, Integer> getBlocksNeeded() {
        return new HashMap<>(blocksNeeded);
    }
    
    public int getEstimatedSeconds() {
        return estimatedSeconds;
    }
    
    public int getTotalBlocks() {
        return blocksNeeded.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Returns a formatted string with the estimated time.
     */
    public String getFormattedTime() {
        if (estimatedSeconds < 60) {
            return estimatedSeconds + " seconds";
        } else {
            int minutes = estimatedSeconds / 60;
            int seconds = estimatedSeconds % 60;
            return String.format("%d min %d sec", minutes, seconds);
        }
    }
}
