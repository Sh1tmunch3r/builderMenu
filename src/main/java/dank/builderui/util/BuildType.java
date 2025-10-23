package dank.builderui.util;

/**
 * Enum representing all available build types in the Builder Menu.
 * Each build type has a display name and category for organization.
 */
public enum BuildType {
    // Basic Buildings
    HOUSE("House", BuildCategory.BUILDINGS),
    MANSION("Mansion", BuildCategory.BUILDINGS),
    TOWER("Tower", BuildCategory.BUILDINGS),
    CASTLE("Castle", BuildCategory.BUILDINGS),
    
    // Agricultural
    FARM("Farm", BuildCategory.AGRICULTURAL),
    
    // Infrastructure
    BRIDGE("Bridge", BuildCategory.INFRASTRUCTURE),
    ROAD("Road", BuildCategory.INFRASTRUCTURE),
    WALL("Wall", BuildCategory.INFRASTRUCTURE),
    
    // Decorative
    FOUNTAIN("Fountain", BuildCategory.DECORATIVE),
    TREEHOUSE("Treehouse", BuildCategory.DECORATIVE),
    
    // Custom
    CUSTOM("Custom Structure", BuildCategory.CUSTOM);
    
    private final String displayName;
    private final BuildCategory category;
    
    BuildType(String displayName, BuildCategory category) {
        this.displayName = displayName;
        this.category = category;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public BuildCategory getCategory() {
        return category;
    }
}
