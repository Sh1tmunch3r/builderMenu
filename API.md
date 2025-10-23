# Builder UI Mod - Developer API Documentation

This document provides technical details for developers who want to extend or integrate with the Builder UI mod.

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Core APIs](#core-apis)
3. [Adding New Build Types](#adding-new-build-types)
4. [Adding New Materials](#adding-new-materials)
5. [Custom Structure Integration](#custom-structure-integration)
6. [UI Extension](#ui-extension)
7. [Event Hooks](#event-hooks)

---

## Architecture Overview

### Package Structure

```
dank.builderui/
├── BuilderUIMod.java              # Server-side entry point
├── client/
│   ├── BuilderUIModClient.java   # Client-side entry point
│   └── screen/
│       └── BuilderUIScreen.java  # Main UI screen
└── util/
    ├── BuildType.java            # Build type definitions
    ├── BuildCategory.java        # Category organization
    ├── MaterialType.java         # Material definitions
    ├── BuildSize.java            # Size options
    ├── BuildStyle.java           # Style options
    ├── BuildConfig.java          # Configuration container
    ├── BuildInfo.java            # Build information
    ├── StructureBuilder.java     # Main builder logic
    ├── StructureTemplate.java    # Template storage
    ├── UndoManager.java          # Undo functionality
    └── CustomStructureManager.java # Custom structures
```

### Key Components

- **Enums**: Define available options (types, materials, sizes, styles)
- **Data Classes**: Store configuration and information
- **Managers**: Handle business logic (building, undo, persistence)
- **UI**: User interface and interaction

---

## Core APIs

### StructureBuilder API

Main class for executing builds.

#### Basic Building

```java
import dank.builderui.util.*;

// Simple build with defaults
BuildConfig config = new BuildConfig(BuildType.HOUSE);
StructureBuilder.build(player, config);

// Custom configuration
BuildConfig config = new BuildConfig(BuildType.CASTLE);
config.setMaterial(MaterialType.STONE);
config.setSize(BuildSize.LARGE);
config.setStyle(BuildStyle.MEDIEVAL);
config.setRotation(90);
config.setOffsetX(5);
config.setOffsetY(0);
config.setOffsetZ(5);
StructureBuilder.build(player, config);
```

#### Getting Build Information

```java
BuildInfo info = StructureBuilder.getBuildInfo(BuildType.MANSION, BuildSize.MEDIUM);
System.out.println("Description: " + info.getDescription());
System.out.println("Total blocks: " + info.getTotalBlocks());
System.out.println("Time: " + info.getFormattedTime());

Map<String, Integer> blocks = info.getBlocksNeeded();
for (Map.Entry<String, Integer> entry : blocks.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

### UndoManager API

Manage build history and undo operations.

#### Recording Actions

```java
import dank.builderui.util.UndoManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

Map<BlockPos, BlockState> originalStates = new HashMap<>();

// Before modifying blocks, record original states
BlockPos pos = new BlockPos(x, y, z);
originalStates.put(pos, world.getBlockState(pos));

// After modifications, record action
UndoManager.recordAction(player.getUuid(), originalStates);
```

#### Performing Undo

```java
import dank.builderui.util.UndoManager;

// Check if undo is available
if (UndoManager.canUndo(player.getUuid())) {
    // Perform undo
    boolean success = UndoManager.undo(player.getUuid(), world);
    if (success) {
        player.sendMessage(Text.of("Undo successful!"), false);
    }
}

// Clear history for player
UndoManager.clearHistory(player.getUuid());
```

### CustomStructureManager API

Save and load custom structures.

#### Saving Structures

```java
import dank.builderui.util.*;

StructureTemplate template = new StructureTemplate("MyHouse", 10, 8, 10);

// Add blocks to template
template.setBlock(0, 0, 0, Blocks.STONE.getDefaultState());
template.setBlock(1, 0, 0, Blocks.STONE.getDefaultState());
// ... more blocks

// Save to disk
boolean success = CustomStructureManager.saveStructure(template);
```

#### Loading Structures

```java
import dank.builderui.util.*;

// List all saved structures
List<String> structures = CustomStructureManager.listStructures();
for (String name : structures) {
    System.out.println("Saved structure: " + name);
}

// Load a specific structure
StructureTemplate template = CustomStructureManager.loadStructure("MyHouse");
if (template != null) {
    // Use template
    Map<BlockPos, BlockState> blocks = template.getBlocks();
}

// Delete a structure
CustomStructureManager.deleteStructure("MyHouse");
```

---

## Adding New Build Types

### Step-by-Step Guide

#### 1. Add to BuildType Enum

```java
// In BuildType.java
public enum BuildType {
    // ... existing types
    LIGHTHOUSE("Lighthouse", BuildCategory.DECORATIVE),
    MINE_ENTRANCE("Mine Entrance", BuildCategory.INFRASTRUCTURE);
    
    // ... rest of enum
}
```

#### 2. Add to BuildCategory (if needed)

```java
// In BuildCategory.java
public enum BuildCategory {
    // ... existing categories
    INDUSTRIAL("Industrial");
    
    // ... rest of enum
}
```

#### 3. Create Build Method

```java
// In StructureBuilder.java
private static void buildLighthouseInternal(World world, BlockPos origin, 
                                            BuildConfig config, 
                                            Map<BlockPos, BlockState> originalStates) {
    Block primaryBlock = config.getMaterial().getPrimaryBlock();
    Block glassBlock = Blocks.GLASS;
    float scale = config.getSize().getScale();
    
    int baseRadius = (int)(3 * scale);
    int height = (int)(20 * scale);
    
    // Build cylindrical tower
    for (int y = 0; y < height; y++) {
        for (int x = -baseRadius; x <= baseRadius; x++) {
            for (int z = -baseRadius; z <= baseRadius; z++) {
                if (x * x + z * z <= baseRadius * baseRadius) {
                    BlockPos pos = origin.add(x, y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    
                    // Walls on perimeter
                    if (x * x + z * z >= (baseRadius - 1) * (baseRadius - 1)) {
                        world.setBlockState(pos, primaryBlock.getDefaultState());
                    }
                }
            }
        }
    }
    
    // Add glass top
    int topStart = height - 3;
    for (int y = topStart; y < height; y++) {
        for (int x = -baseRadius; x <= baseRadius; x++) {
            for (int z = -baseRadius; z <= baseRadius; z++) {
                if (x * x + z * z <= baseRadius * baseRadius) {
                    BlockPos pos = origin.add(x, y, z);
                    if (x * x + z * z >= (baseRadius - 1) * (baseRadius - 1)) {
                        originalStates.put(pos, world.getBlockState(pos));
                        world.setBlockState(pos, glassBlock.getDefaultState());
                    }
                }
            }
        }
    }
    
    // Add beacon on top
    BlockPos beaconPos = origin.up(height);
    originalStates.put(beaconPos, world.getBlockState(beaconPos));
    world.setBlockState(beaconPos, Blocks.SEA_LANTERN.getDefaultState());
}
```

#### 4. Add to Switch Statement

```java
// In StructureBuilder.build() method
switch (config.getBuildType()) {
    // ... existing cases
    case LIGHTHOUSE -> buildLighthouseInternal(world, origin, config, originalStates);
    case MINE_ENTRANCE -> buildMineEntranceInternal(world, origin, config, originalStates);
}
```

#### 5. Add BuildInfo

```java
// In StructureBuilder.getBuildInfo() method
switch (buildType) {
    // ... existing cases
    case LIGHTHOUSE -> {
        info = new BuildInfo(buildType, "A tall lighthouse with beacon light", 30);
        info.addBlockRequirement("Primary Material", (int)(200 * size.getScale()));
        info.addBlockRequirement("Glass", (int)(50 * size.getScale()));
        info.addBlockRequirement("Beacon", 1);
    }
    case MINE_ENTRANCE -> {
        info = new BuildInfo(buildType, "An entrance to underground mines", 10);
        info.addBlockRequirement("Primary Material", (int)(100 * size.getScale()));
        info.addBlockRequirement("Rails", (int)(20 * size.getScale()));
    }
}
```

### Build Method Best Practices

1. **Use Configuration**: Always respect `config` parameters
2. **Record States**: Store original blocks in `originalStates` map
3. **Scale Properly**: Multiply dimensions by `scale` factor
4. **Material Flexibility**: Use material blocks from config
5. **Bounds Checking**: Ensure positions are valid
6. **Performance**: Use efficient loops and batch operations

---

## Adding New Materials

### Step-by-Step Guide

#### Add to MaterialType Enum

```java
// In MaterialType.java
public enum MaterialType {
    // ... existing materials
    COPPER("Copper", 
           Blocks.COPPER_BLOCK, 
           Blocks.OXIDIZED_COPPER, 
           Blocks.LIGHTNING_ROD),
    
    AMETHYST("Amethyst", 
             Blocks.AMETHYST_BLOCK, 
             Blocks.BUDDING_AMETHYST, 
             Blocks.AMETHYST_CLUSTER),
    
    BAMBOO("Bamboo", 
           Blocks.BAMBOO_PLANKS, 
           Blocks.BAMBOO_BLOCK, 
           Blocks.BAMBOO_FENCE);
    
    // ... rest of enum
}
```

### Material Definition Guidelines

1. **Primary Block**: Main construction material (walls, floors)
2. **Secondary Block**: Supporting structures (roofs, pillars)
3. **Decorative Block**: Accents and details (railings, trim)

4. **Compatibility**: Use blocks available in target Minecraft version
5. **Theme Coherence**: Blocks should complement each other
6. **Availability**: Consider resource availability in survival

---

## Custom Structure Integration

### Creating Structure Templates Programmatically

```java
import dank.builderui.util.*;

public class MyStructureFactory {
    
    public static StructureTemplate createCustomTower() {
        StructureTemplate template = new StructureTemplate("Custom Tower", 5, 20, 5);
        
        // Build structure block by block
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    boolean isWall = x == 0 || x == 4 || z == 0 || z == 4;
                    if (isWall) {
                        template.setBlock(x, y, z, Blocks.STONE_BRICKS.getDefaultState());
                    }
                }
            }
        }
        
        return template;
    }
    
    public static void buildCustomStructure(PlayerEntity player, StructureTemplate template) {
        // Apply material transformation
        MaterialType material = MaterialType.QUARTZ;
        StructureTemplate materializedTemplate = template.withMaterial(material);
        
        // Apply scaling
        BuildSize size = BuildSize.LARGE;
        StructureTemplate scaledTemplate = materializedTemplate.withScale(size.getScale());
        
        // Build at player location
        World world = player.getWorld();
        BlockPos origin = player.getBlockPos().add(3, 0, 3);
        Map<BlockPos, BlockState> originalStates = new HashMap<>();
        
        for (Map.Entry<BlockPos, BlockState> entry : scaledTemplate.getBlocks().entrySet()) {
            BlockPos relativePos = entry.getKey();
            BlockPos worldPos = origin.add(relativePos);
            
            originalStates.put(worldPos, world.getBlockState(worldPos));
            world.setBlockState(worldPos, entry.getValue());
        }
        
        // Record for undo
        UndoManager.recordAction(player.getUuid(), originalStates);
    }
}
```

### Template Composition

```java
public static StructureTemplate combineTemplates(StructureTemplate base, 
                                                  StructureTemplate addon, 
                                                  int offsetX, int offsetY, int offsetZ) {
    StructureTemplate combined = new StructureTemplate(
        base.getName() + " + " + addon.getName(),
        base.getWidth() + addon.getWidth(),
        Math.max(base.getHeight(), addon.getHeight()),
        base.getDepth() + addon.getDepth()
    );
    
    // Copy base blocks
    for (Map.Entry<BlockPos, BlockState> entry : base.getBlocks().entrySet()) {
        BlockPos pos = entry.getKey();
        combined.setBlock(pos.getX(), pos.getY(), pos.getZ(), entry.getValue());
    }
    
    // Copy addon blocks with offset
    for (Map.Entry<BlockPos, BlockState> entry : addon.getBlocks().entrySet()) {
        BlockPos pos = entry.getKey();
        combined.setBlock(
            pos.getX() + offsetX,
            pos.getY() + offsetY,
            pos.getZ() + offsetZ,
            entry.getValue()
        );
    }
    
    return combined;
}
```

---

## UI Extension

### Adding Custom Configuration Options

To add new configuration options, you need to:

1. **Add field to BuildConfig**
2. **Add UI controls in BuilderUIScreen**
3. **Use configuration in build methods**

#### Example: Adding "Decorated" Option

```java
// 1. In BuildConfig.java
private boolean decorated = true;

public boolean isDecorated() {
    return decorated;
}

public void setDecorated(boolean decorated) {
    this.decorated = decorated;
}

// 2. In BuilderUIScreen.initConfigScreen()
ButtonWidget decoratedButton = ButtonWidget.builder(
    Text.of("Decorated: " + (currentConfig.isDecorated() ? "Yes" : "No")),
    btn -> {
        currentConfig.setDecorated(!currentConfig.isDecorated());
        clearAndInit();
    }
).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
addDrawableChild(decoratedButton);

// 3. In StructureBuilder build methods
if (config.isDecorated()) {
    // Add decorative elements
}
```

### Creating Custom UI Screens

```java
import dank.builderui.client.screen.BuilderUIScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CustomBuilderScreen extends Screen {
    private final Screen parent;
    private final BuildConfig config;
    
    public CustomBuilderScreen(Screen parent, BuildConfig config) {
        super(Text.of("Custom Builder"));
        this.parent = parent;
        this.config = config;
    }
    
    @Override
    protected void init() {
        // Add custom widgets
        addDrawableChild(ButtonWidget.builder(
            Text.of("Advanced Options"),
            btn -> {
                // Open advanced options
            }
        ).dimensions(width / 2 - 75, height / 2, 150, 20).build());
        
        addDrawableChild(ButtonWidget.builder(
            Text.of("Back"),
            btn -> client.setScreen(parent)
        ).dimensions(width / 2 - 75, height / 2 + 30, 150, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 40, 0xFFFFFF);
    }
}
```

---

## Event Hooks

### Future Event System (Planned)

The following events are planned for future versions:

#### Build Events

```java
// Before build starts
public interface PreBuildEvent {
    boolean onPreBuild(PlayerEntity player, BuildConfig config, BlockPos origin);
}

// After build completes
public interface PostBuildEvent {
    void onPostBuild(PlayerEntity player, BuildConfig config, BlockPos origin, int blocksPlaced);
}

// Build cancelled
public interface BuildCancelledEvent {
    void onBuildCancelled(PlayerEntity player, BuildConfig config);
}
```

#### Undo Events

```java
// Before undo
public interface PreUndoEvent {
    boolean onPreUndo(PlayerEntity player, Map<BlockPos, BlockState> statesToRestore);
}

// After undo
public interface PostUndoEvent {
    void onPostUndo(PlayerEntity player, int blocksRestored);
}
```

### Current Workaround: Method Wrapping

Until events are implemented, wrap methods:

```java
public class BuildEventHandler {
    
    public static void buildWithLogging(PlayerEntity player, BuildConfig config) {
        System.out.println("Building " + config.getBuildType() + " for " + player.getName());
        
        long startTime = System.currentTimeMillis();
        StructureBuilder.build(player, config);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Build completed in " + (endTime - startTime) + "ms");
    }
}
```

---

## Best Practices

### Performance

1. **Batch Block Updates**: Use efficient loops
2. **Limit Undo History**: Default is 10 actions
3. **Async Operations**: Consider async for large builds (future)
4. **Memory Management**: Clear unused templates

### Compatibility

1. **Version Checking**: Check Minecraft version for block availability
2. **Mod Integration**: Check for other mods' blocks
3. **Graceful Degradation**: Fallback to vanilla blocks if needed

### Code Quality

1. **Documentation**: Comment complex logic
2. **Null Checks**: Always validate player and world
3. **Error Handling**: Catch and log exceptions
4. **Testing**: Test with different scales and materials

---

## Examples

### Complete Custom Build Example

```java
package com.example.custombuilds;

import dank.builderui.util.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class CustomPyramid {
    
    public static void buildPyramid(PlayerEntity player) {
        BuildConfig config = new BuildConfig(BuildType.CUSTOM);
        config.setMaterial(MaterialType.SANDSTONE);
        config.setSize(BuildSize.LARGE);
        
        World world = player.getWorld();
        if (world.isClient) return;
        
        BlockPos origin = player.getBlockPos().add(5, 0, 5);
        Map<BlockPos, BlockState> originalStates = new HashMap<>();
        
        Block block = config.getMaterial().getPrimaryBlock();
        int baseSize = (int)(15 * config.getSize().getScale());
        
        // Build pyramid layer by layer
        for (int y = 0; y < baseSize / 2; y++) {
            int layerSize = baseSize - (y * 2);
            
            for (int x = y; x < y + layerSize; x++) {
                for (int z = y; z < y + layerSize; z++) {
                    BlockPos pos = origin.add(x, y, z);
                    originalStates.put(pos, world.getBlockState(pos));
                    world.setBlockState(pos, block.getDefaultState());
                }
            }
        }
        
        // Add entrance
        for (int y = 0; y < 3; y++) {
            BlockPos doorPos = origin.add(baseSize / 2, y, 0);
            originalStates.put(doorPos, world.getBlockState(doorPos));
            world.setBlockState(doorPos, Blocks.AIR.getDefaultState());
        }
        
        // Record for undo
        UndoManager.recordAction(player.getUuid(), originalStates);
        
        player.sendMessage(Text.of("§aPyramid built successfully!"), false);
    }
}
```

### Integration Example

```java
package com.example.integration;

import dank.builderui.util.*;

public class BuilderAPIDemo {
    
    public static void demonstrateAPI() {
        // Create configuration
        BuildConfig config = new BuildConfig(BuildType.HOUSE);
        config.setMaterial(MaterialType.BRICK);
        config.setSize(BuildSize.MEDIUM);
        config.setStyle(BuildStyle.MEDIEVAL);
        
        // Get build information
        BuildInfo info = StructureBuilder.getBuildInfo(
            config.getBuildType(),
            config.getSize()
        );
        
        System.out.println("Building: " + info.getBuildType().getDisplayName());
        System.out.println("Description: " + info.getDescription());
        System.out.println("Total blocks: " + info.getTotalBlocks());
        System.out.println("Estimated time: " + info.getFormattedTime());
        
        // List materials needed
        System.out.println("\nMaterials needed:");
        for (Map.Entry<String, Integer> entry : info.getBlocksNeeded().entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        // Build (in actual game context with player)
        // StructureBuilder.build(player, config);
    }
}
```

---

## Troubleshooting

### Common Issues

**Issue**: Blocks not placing
- **Solution**: Ensure code runs on server side (`!world.isClient`)

**Issue**: Undo not working
- **Solution**: Verify original states are recorded before modification

**Issue**: Custom structure not saving
- **Solution**: Check write permissions for `builderui_structures/` directory

**Issue**: Material not applying
- **Solution**: Ensure material blocks are used from `config.getMaterial()`

---

## Version Compatibility

### Minecraft 1.20.1
- All features fully supported
- Fabric Loader 0.16.10+
- Fabric API 0.92.6+

### Future Versions
- Code designed for forward compatibility
- Block availability may vary
- Check `Block` class for deprecated blocks

---

## Contributing

### Pull Request Guidelines

1. Follow existing code style
2. Add JavaDoc comments
3. Include examples in API.md
4. Test with multiple materials and sizes
5. Update CHANGELOG.md

### Testing Checklist

- [ ] All build types work with all materials
- [ ] Undo/redo functions correctly
- [ ] UI is responsive and clear
- [ ] No console errors
- [ ] Works in multiplayer
- [ ] Performance acceptable for large builds

---

## License

MIT License - Feel free to extend and modify

## Support

For API questions and support:
- GitHub Issues: Bug reports and feature requests
- Discussions: General questions and ideas
- Pull Requests: Code contributions

---

**Happy Developing!** 🔧
