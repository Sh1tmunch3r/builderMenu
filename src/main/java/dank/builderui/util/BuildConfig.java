package dank.builderui.util;

/**
 * Configuration class for building structures.
 * Contains settings for material, size, style, orientation, and placement.
 */
public class BuildConfig {
    private BuildType buildType;
    private MaterialType material;
    private BuildSize size;
    private BuildStyle style;
    private int rotation; // 0, 90, 180, 270 degrees
    private int offsetX;
    private int offsetY;
    private int offsetZ;
    
    public BuildConfig(BuildType buildType) {
        this.buildType = buildType;
        this.material = MaterialType.WOOD;
        this.size = BuildSize.MEDIUM;
        this.style = BuildStyle.STANDARD;
        this.rotation = 0;
        this.offsetX = 2;
        this.offsetY = 0;
        this.offsetZ = 2;
    }
    
    // Getters and setters
    public BuildType getBuildType() {
        return buildType;
    }
    
    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }
    
    public MaterialType getMaterial() {
        return material;
    }
    
    public void setMaterial(MaterialType material) {
        this.material = material;
    }
    
    public BuildSize getSize() {
        return size;
    }
    
    public void setSize(BuildSize size) {
        this.size = size;
    }
    
    public BuildStyle getStyle() {
        return style;
    }
    
    public void setStyle(BuildStyle style) {
        this.style = style;
    }
    
    public int getRotation() {
        return rotation;
    }
    
    public void setRotation(int rotation) {
        this.rotation = rotation % 360;
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    public int getOffsetZ() {
        return offsetZ;
    }
    
    public void setOffsetZ(int offsetZ) {
        this.offsetZ = offsetZ;
    }
    
    /**
     * Creates a copy of this configuration.
     */
    public BuildConfig copy() {
        BuildConfig copy = new BuildConfig(this.buildType);
        copy.material = this.material;
        copy.size = this.size;
        copy.style = this.style;
        copy.rotation = this.rotation;
        copy.offsetX = this.offsetX;
        copy.offsetY = this.offsetY;
        copy.offsetZ = this.offsetZ;
        return copy;
    }
}
