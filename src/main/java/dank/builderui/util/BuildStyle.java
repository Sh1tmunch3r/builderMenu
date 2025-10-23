package dank.builderui.util;

/**
 * Style options for building structures.
 */
public enum BuildStyle {
    STANDARD("Standard"),
    MODERN("Modern"),
    MEDIEVAL("Medieval"),
    RUSTIC("Rustic"),
    FUTURISTIC("Futuristic");
    
    private final String displayName;
    
    BuildStyle(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
