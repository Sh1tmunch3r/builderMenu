package dank.builderui.util;

/**
 * Size options for building structures.
 */
public enum BuildSize {
    SMALL("Small", 0.5f),
    MEDIUM("Medium", 1.0f),
    LARGE("Large", 1.5f),
    EXTRA_LARGE("Extra Large", 2.0f);
    
    private final String displayName;
    private final float scale;
    
    BuildSize(String displayName, float scale) {
        this.displayName = displayName;
        this.scale = scale;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public float getScale() {
        return scale;
    }
}
