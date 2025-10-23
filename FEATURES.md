# Builder UI - Enhanced Features Documentation

## Overview

This document provides detailed information about all the enhanced features added to the Builder UI mod.

## Table of Contents

1. [Build Types](#build-types)
2. [Material System](#material-system)
3. [Configuration System](#configuration-system)
4. [Undo System](#undo-system)
5. [Custom Structure Management](#custom-structure-management)
6. [User Interface](#user-interface)
7. [Build Information System](#build-information-system)

---

## Build Types

### Houses & Buildings

#### House
- **Description**: A cozy residential structure with walls, floor, and roof
- **Size**: 5x4x5 blocks (medium)
- **Features**: 
  - Four walls with configurable material
  - Flat roof with secondary material
  - Automatic door placement at front
  - Interior space for furnishing
- **Best Materials**: Wood, Stone, Brick
- **Use Cases**: Starter home, village buildings, quick shelter

#### Mansion
- **Description**: A large multi-floor mansion with decorative elements
- **Size**: 12x8x12 blocks (medium)
- **Features**:
  - Multiple floors (2 floors per 8 blocks height)
  - Alternating wall patterns with decorative blocks
  - Large entrance (2 blocks tall)
  - Decorative roof
- **Best Materials**: Brick, Quartz, Dark Oak
- **Use Cases**: Luxury homes, administrative buildings, palaces

#### Tower
- **Description**: A tall vertical structure
- **Size**: 1x10x1 blocks (medium)
- **Features**:
  - Simple vertical pillar
  - Customizable height via size setting
  - Ideal for lookout points
- **Best Materials**: Stone, Prismarine, Quartz
- **Use Cases**: Watchtowers, monuments, markers

#### Castle
- **Description**: A fortified structure with defensive features
- **Size**: 20x12x20 blocks (medium)
- **Features**:
  - Thick outer walls
  - Four corner towers (15 blocks tall)
  - Large entrance gate (3 blocks tall)
  - Interior courtyard
- **Best Materials**: Stone, Brick, Prismarine
- **Use Cases**: Fortresses, defensive structures, role-playing

### Agricultural Structures

#### Farm
- **Description**: A fenced agricultural area with farmland
- **Size**: 8x1x8 blocks (medium)
- **Features**:
  - Oak fence perimeter
  - Farmland blocks inside
  - Central water source
  - Ready for crop planting
- **Best Materials**: Material affects fence only
- **Use Cases**: Crop farming, animal pens, gardens

### Infrastructure

#### Bridge
- **Description**: A sturdy crossing structure with railings
- **Size**: 15x1x3 blocks (medium)
- **Features**:
  - Solid deck
  - Side railings for safety
  - Configurable length via size
- **Best Materials**: Wood, Stone, Concrete
- **Use Cases**: River crossings, ravine bridges, pathways

#### Road
- **Description**: A paved pathway
- **Size**: 20x1x3 blocks (medium)
- **Features**:
  - Flat surface
  - Configurable width and length
  - Straight path
- **Best Materials**: Stone, Concrete, Sandstone
- **Use Cases**: Pathways, town roads, connections

#### Wall
- **Description**: A defensive or decorative wall
- **Size**: 20x4x1 blocks (medium)
- **Features**:
  - Solid vertical barrier
  - Configurable height and length
  - Can be chained together
- **Best Materials**: Stone, Brick, Prismarine
- **Use Cases**: Fortifications, property boundaries, decoration

### Decorative Structures

#### Fountain
- **Description**: An ornamental water feature
- **Size**: 7x3x7 blocks diameter (medium)
- **Features**:
  - Circular base
  - Central pillar
  - Water at top
  - Symmetrical design
- **Best Materials**: Quartz, Stone, Brick
- **Use Cases**: Town squares, gardens, courtyards

#### Treehouse
- **Description**: A platform house built on a tree trunk
- **Size**: 4x8x4 blocks platform at height 8 (medium)
- **Features**:
  - Oak log trunk
  - Wooden platform
  - Small house on platform
  - Oak leaves canopy
- **Best Materials**: Wood types
- **Use Cases**: Unique homes, forest bases, adventure builds

---

## Material System

### Material Hierarchy

Each material provides three block types:

1. **Primary Block** - Main construction material (walls, floors, deck)
2. **Secondary Block** - Supporting structures (roofs, foundations, pillars)
3. **Decorative Block** - Accents and details (railings, trim, corners)

### Available Materials

#### Wood (Oak)
- Primary: Oak Planks
- Secondary: Oak Log
- Decorative: Oak Fence
- **Style**: Natural, rustic, lightweight
- **Best For**: Houses, treehouses, farms

#### Stone
- Primary: Stone
- Secondary: Cobblestone
- Decorative: Stone Bricks
- **Style**: Solid, traditional, durable
- **Best For**: Castles, walls, bridges

#### Brick
- Primary: Bricks
- Secondary: Red Sandstone
- Decorative: Brick Stairs
- **Style**: Classic, refined, elegant
- **Best For**: Mansions, fountains, houses

#### Glass
- Primary: Glass
- Secondary: White Stained Glass
- Decorative: Glass Pane
- **Style**: Modern, transparent, light
- **Best For**: Modern builds, greenhouses, windows

#### Sandstone
- Primary: Sandstone
- Secondary: Smooth Sandstone
- Decorative: Chiseled Sandstone
- **Style**: Desert, ancient, warm
- **Best For**: Desert builds, temples, roads

#### Dark Oak
- Primary: Dark Oak Planks
- Secondary: Dark Oak Log
- Decorative: Dark Oak Fence
- **Style**: Dark, sophisticated, contrast
- **Best For**: Modern mansions, upscale builds

#### Quartz
- Primary: Quartz Block
- Secondary: Quartz Pillar
- Decorative: Chiseled Quartz
- **Style**: Clean, bright, luxurious
- **Best For**: Modern builds, fountains, temples

#### Prismarine
- Primary: Prismarine
- Secondary: Prismarine Bricks
- Decorative: Dark Prismarine
- **Style**: Ocean, magical, animated
- **Best For**: Underwater builds, ocean themes

#### Concrete
- Primary: White Concrete
- Secondary: Light Gray Concrete
- Decorative: Gray Concrete
- **Style**: Modern, smooth, urban
- **Best For**: Modern architecture, roads, contemporary

---

## Configuration System

### Build Size

Controls the scale of structures:

- **Small** (0.5x): Compact builds, resource-efficient
- **Medium** (1.0x): Standard size, balanced
- **Large** (1.5x): Spacious builds, impressive
- **Extra Large** (2.0x): Massive builds, resource-intensive

### Build Style

Determines aesthetic and design patterns (framework for future expansion):

- **Standard**: Classic Minecraft aesthetics
- **Modern**: Clean lines, contemporary design
- **Medieval**: Historic, fortress-like
- **Rustic**: Country, traditional, natural
- **Futuristic**: Advanced, technological

### Rotation

- Rotate structures in 90° increments
- Four orientations: 0°, 90°, 180°, 270°
- Useful for aligning with terrain or existing structures

### Placement Offset

Default offsets from player position:
- **X Offset**: 2 blocks (adjustable)
- **Y Offset**: 0 blocks (adjustable)
- **Z Offset**: 2 blocks (adjustable)

---

## Undo System

### Features

- **Per-Player History**: Each player has independent undo stack
- **Capacity**: Stores up to 10 actions per player
- **State Recording**: Saves original BlockState for each modified position
- **Single Operation Restore**: Undoes entire structure at once

### Usage

1. Open Builder Menu
2. Click "Undo Last Build"
3. Most recent build is reverted
4. Original blocks are restored

### Technical Details

- Uses UUID-based player identification
- Stores HashMap of BlockPos → BlockState
- Automatically removes oldest when capacity exceeded
- Thread-safe operations

---

## Custom Structure Management

### Saving Structures

Custom structures can be saved for reuse:

1. Build or configure a structure
2. Use save function (API available)
3. Structure stored in `builderui_structures/` directory
4. Saved as JSON with metadata

### Loading Structures

1. Access custom structures from menu
2. Select saved structure
3. Configure and place like any other build

### File Format

Structures saved as JSON:
```json
{
  "name": "My Custom House",
  "width": 10,
  "height": 8,
  "depth": 10,
  "blockCount": 200
}
```

### Management

- **List**: View all saved structures
- **Load**: Import structure for building
- **Delete**: Remove saved structure
- **Export**: Share structures (future feature)

---

## User Interface

### Navigation Hierarchy

```
Main Menu (Categories)
├── Buildings
│   ├── House → Configuration
│   ├── Mansion → Configuration
│   ├── Tower → Configuration
│   └── Castle → Configuration
├── Agricultural
│   └── Farm → Configuration
├── Infrastructure
│   ├── Bridge → Configuration
│   ├── Road → Configuration
│   └── Wall → Configuration
├── Decorative
│   ├── Fountain → Configuration
│   └── Treehouse → Configuration
└── Custom
    └── Custom Structure → Configuration
```

### Screen Elements

#### Category Screen
- Category buttons for each group
- Undo button for recent builds
- Close button

#### Build Selection Screen
- Build type buttons
- Back navigation
- Tooltips on hover

#### Configuration Screen
- Material selector (cycle button)
- Size selector (cycle button)
- Style selector (cycle button)
- Rotation controls (left/right buttons)
- Info button (show details)
- Preview button (coming soon)
- Build button (confirm action)
- Back button (return to selection)

### Visual Features

- **Background**: Demo background texture
- **Title**: Dynamic based on current screen
- **Subtitle**: Context information
- **Tooltips**: Hover information (planned)
- **Scrolling**: For long lists (infrastructure ready)

---

## Build Information System

### Information Displayed

For each build type, shows:

1. **Name**: Structure display name
2. **Description**: What the structure looks like
3. **Total Blocks**: Sum of all required blocks
4. **Estimated Time**: Build duration estimate
5. **Block Requirements**: Detailed material breakdown

### Example Information

**House (Medium)**
- Description: "A cozy house with walls, floor, and roof"
- Total Blocks: 86
- Estimated Time: 5 seconds
- Requirements:
  - Primary Material: 50 blocks
  - Roof Material: 36 blocks

**Mansion (Large)**
- Description: "A large multi-floor mansion"
- Total Blocks: 975
- Estimated Time: 15 seconds
- Requirements:
  - Primary Material: 600 blocks
  - Secondary Material: 225 blocks
  - Decorative Material: 150 blocks

### Accessing Information

1. Configure your structure
2. Click "ℹ Show Info"
3. Information displayed in chat
4. Formatted with colors for readability

---

## Performance Considerations

### Optimization

- **Batch Operations**: Blocks placed in efficient loops
- **State Recording**: Only modified blocks stored
- **Memory Management**: Undo history limited
- **Client-Server**: Builds only execute server-side

### Best Practices

- Use Medium size for testing
- Clear undo history periodically
- Save important structures before undo
- Consider server performance for large builds

---

## Future Enhancements

### Planned Features

- **Visual Preview**: Ghost blocks showing structure placement
- **Material Preview**: See structure with different materials
- **Schematic Import**: Load external structure files
- **Blueprint Sharing**: Share designs with other players
- **Build Queue**: Queue multiple structures
- **Terrain Adaptation**: Auto-adjust to terrain
- **Resource Checking**: Verify materials in inventory (survival)
- **Cost Calculation**: Show exact item requirements
- **Favorites System**: Mark frequently used builds
- **Search Function**: Find structures quickly

### Community Requests

We welcome suggestions! Features are prioritized based on:
- Community interest
- Technical feasibility
- Compatibility concerns
- Performance impact

---

## Technical Architecture

### Class Overview

**Enums**
- BuildType: Structure definitions
- BuildCategory: Organization
- MaterialType: Block mappings
- BuildSize: Scale factors
- BuildStyle: Style variants

**Data Classes**
- BuildConfig: Configuration container
- BuildInfo: Information display
- StructureTemplate: Template storage

**Managers**
- StructureBuilder: Build execution
- UndoManager: History management
- CustomStructureManager: File I/O

**UI**
- BuilderUIScreen: Main interface
- Future: Preview screens, dialogs

### Extension Points

#### Adding New Builds

```java
// 1. Add to BuildType enum
LIGHTHOUSE("Lighthouse", BuildCategory.DECORATIVE)

// 2. Create build method
private static void buildLighthouseInternal(World world, BlockPos origin, 
                                            BuildConfig config, 
                                            Map<BlockPos, BlockState> originalStates) {
    // Implementation
}

// 3. Add to switch in build()
case LIGHTHOUSE -> buildLighthouseInternal(world, origin, config, originalStates);

// 4. Add BuildInfo
case LIGHTHOUSE -> {
    info = new BuildInfo(buildType, "A tall lighthouse with beacon", 20);
    info.addBlockRequirement("Primary Material", 150);
    info.addBlockRequirement("Glass", 30);
    info.addBlockRequirement("Beacon", 1);
}
```

#### Adding New Materials

```java
COPPER("Copper", Blocks.COPPER_BLOCK, 
       Blocks.OXIDIZED_COPPER, Blocks.LIGHTNING_ROD)
```

#### Adding New Categories

```java
MILITARY("Military")
// Then assign builds to category
```

---

## Credits & License

**Original Concept**: dank/Sh1tmunch3r  
**Enhanced Implementation**: Comprehensive feature additions  
**License**: MIT  

---

## Support & Contribution

For questions, issues, or contributions:
- GitHub: [Repository Link]
- Issues: Report bugs and suggest features
- Pull Requests: Contribute code improvements
- Documentation: Help improve guides

**Happy Building!** 🏗️
