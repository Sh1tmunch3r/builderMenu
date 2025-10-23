package dank.builderui.util;

/**
 * Categories for organizing build types in the UI.
 */
public enum BuildCategory {
    BUILDINGS("Buildings"),
    AGRICULTURAL("Agricultural"),
    INFRASTRUCTURE("Infrastructure"),
    DECORATIVE("Decorative"),
    CUSTOM("Custom");
    
    private final String displayName;
    
    BuildCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
