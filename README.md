# Builder UI Mod - Enhanced Edition

A comprehensive Minecraft Fabric mod that provides an intuitive, feature-rich builder menu for constructing various structures with ease.

## Features

### 🏗️ Diverse Build Types

The mod now includes **11 different structure types** organized into categories:

#### Buildings
- **House** - A cozy wooden house with walls, floor, and roof
- **Mansion** - A large multi-floor mansion with decorative elements
- **Tower** - A tall tower reaching to the sky
- **Castle** - A fortified castle with corner towers

#### Agricultural
- **Farm** - A fenced farm area with farmland and water source

#### Infrastructure
- **Bridge** - A sturdy bridge with railings
- **Road** - A paved road for travel
- **Wall** - A defensive wall structure

#### Decorative
- **Fountain** - A decorative fountain with water feature
- **Treehouse** - A house built in a tree with platform

#### Custom
- **Custom Structure** - Build and save your own structure templates

### 🎨 Material Selection

Choose from **9 different materials** for your builds:
- **Wood** (Oak Planks, Oak Log, Oak Fence)
- **Stone** (Stone, Cobblestone, Stone Bricks)
- **Brick** (Bricks, Red Sandstone, Brick Stairs)
- **Glass** (Glass, White Stained Glass, Glass Pane)
- **Sandstone** (Sandstone, Smooth Sandstone, Chiseled Sandstone)
- **Dark Oak** (Dark Oak Planks, Dark Oak Log, Dark Oak Fence)
- **Quartz** (Quartz Block, Quartz Pillar, Chiseled Quartz)
- **Prismarine** (Prismarine, Prismarine Bricks, Dark Prismarine)
- **Concrete** (White, Light Gray, Gray Concrete)

### 📏 Size Options

Scale your builds with **4 size options**:
- **Small** (0.5x scale)
- **Medium** (1.0x scale - default)
- **Large** (1.5x scale)
- **Extra Large** (2.0x scale)

### 🎭 Style Variations

Choose from **5 architectural styles**:
- **Standard** - Classic Minecraft building style
- **Modern** - Contemporary designs
- **Medieval** - Historic fortress aesthetics
- **Rustic** - Country and traditional designs
- **Futuristic** - Advanced technological themes

### 🔄 Build Configuration

- **Rotation Control** - Rotate structures in 90° increments
- **Placement Offset** - Adjust X, Y, Z position offsets
- **Material Customization** - Select primary, secondary, and decorative blocks

### ℹ️ Build Information

Before building, view detailed information:
- **Description** - What the structure looks like
- **Total Blocks** - How many blocks will be placed
- **Estimated Time** - Build duration estimate
- **Block Requirements** - Detailed breakdown of materials needed

### ⏮️ Undo Functionality

Made a mistake? Use the **Undo** feature to:
- Revert the last build action
- Restore original blocks
- Support for up to 10 undo actions per player

### 💾 Custom Structures

Save and load your own structures:
- **Save Custom Builds** - Store your favorite designs
- **Load Saved Structures** - Reuse structures across worlds
- **Manage Templates** - List and delete saved structures
- **JSON Storage** - Structures saved in `builderui_structures/` directory

### 👁️ Preview System (Coming Soon)

- Visualize structures before building
- Ghost block preview
- Material preview in real-time

## Usage

### Opening the Builder Menu

Press the **B key** (configurable) to open the Builder Menu.

### Navigation Flow

1. **Category Selection** - Choose a build category
2. **Build Selection** - Pick a specific structure type
3. **Configuration** - Customize material, size, style, and rotation
4. **Build Information** - Review requirements and estimates
5. **Confirmation** - Execute the build or go back to adjust

### Keyboard Controls

- **B** - Open/Close Builder Menu
- **ESC** - Close menu or go back
- **Mouse Scroll** - Scroll through long lists (when applicable)

### Button Functions

#### Main Menu
- **Category Buttons** - Navigate to build types in that category
- **Undo Last Build** - Revert the most recent construction
- **Close** - Exit the builder menu

#### Configuration Screen
- **Material Button** - Cycle through available materials
- **Size Button** - Change build scale
- **Style Button** - Switch architectural styles
- **Rotate Left/Right** - Adjust orientation
- **Show Info** - Display detailed build information in chat
- **Preview** - See a ghost preview (coming soon)
- **Build!** - Confirm and construct the structure
- **Back** - Return to build selection

## Architecture

### Core Classes

#### Utility Classes (`dank.builderui.util`)

- **`BuildType`** - Enum defining all available structure types
- **`BuildCategory`** - Enum for organizing builds into categories
- **`MaterialType`** - Enum with material options and block mappings
- **`BuildSize`** - Enum for size scaling options
- **`BuildStyle`** - Enum for architectural style variations
- **`BuildConfig`** - Configuration container for build parameters
- **`BuildInfo`** - Information about block requirements and estimates
- **`StructureBuilder`** - Main builder with methods for each structure type
- **`StructureTemplate`** - Template storage for custom structures
- **`UndoManager`** - Manages undo history for build actions
- **`CustomStructureManager`** - Handles saving/loading custom structures

#### UI Classes (`dank.builderui.client.screen`)

- **`BuilderUIScreen`** - Main UI with category navigation, configuration, and tooltips

#### Entry Points

- **`BuilderUIMod`** - Server-side mod initialization
- **`BuilderUIModClient`** - Client-side initialization and keybinding

### Design Principles

- **Modular Design** - Each build type is self-contained
- **Extensibility** - Easy to add new structures, materials, or styles
- **Player-Friendly** - Intuitive UI with clear information
- **Performance** - Efficient block placement with state tracking
- **Compatibility** - Works with vanilla Minecraft blocks

## Technical Details

### Build System

Each structure implements:
1. **Origin Calculation** - Based on player position and offsets
2. **State Recording** - Saves original blocks for undo
3. **Block Placement** - Constructs structure with configured materials
4. **Undo Registration** - Records action in undo history

### Material System

Materials use a three-tier system:
- **Primary Block** - Main building material
- **Secondary Block** - Supporting structures (floors, roofs)
- **Decorative Block** - Accents and details

### Undo System

- Maintains per-player undo history
- Stores original BlockState for each modified position
- Limits history to prevent memory issues (10 actions)
- Restores blocks in a single operation

### Custom Structure Storage

Structures are saved as JSON files:
```
builderui_structures/
├── my_custom_house.json
├── awesome_tower.json
└── epic_castle.json
```

## Configuration

### Key Bindings

Modify in Minecraft's controls menu:
- **Open Builder Menu** - Default: `B`

### File Locations

- **Custom Structures** - `./builderui_structures/`
- **Mod Configuration** - Standard Fabric config location

## Requirements

- **Minecraft** - 1.20.1
- **Fabric Loader** - 0.16.10
- **Fabric API** - 0.92.6+1.20.1

## Installation

1. Install Fabric Loader for Minecraft 1.20.1
2. Download and place Fabric API in your mods folder
3. Download this mod and place in your mods folder
4. Launch Minecraft with the Fabric profile

## Future Enhancements

- [ ] Visual preview with ghost blocks
- [ ] Schematic import/export
- [ ] Structure rotation preview
- [ ] Multi-page structure browser
- [ ] Search and filter functionality
- [ ] Favorite structures
- [ ] Build queue system
- [ ] Material requirement checking
- [ ] Cost calculation (creative/survival)
- [ ] Terrain adaptation options
- [ ] Blueprint sharing (multiplayer)

## Contributing

Contributions are welcome! The codebase is designed to be modular and extensible:

### Adding a New Build Type

1. Add enum value to `BuildType`
2. Create build method in `StructureBuilder`
3. Add case in `build()` switch statement
4. Create `BuildInfo` entry in `getBuildInfo()`

### Adding a New Material

1. Add enum value to `MaterialType` with block mappings
2. Material will automatically appear in UI

### Adding a New Style

1. Add enum value to `BuildStyle`
2. Implement style variations in structure methods

## License

MIT License - See LICENSE.txt for details

## Credits

- **Original Mod** - dank/Sh1tmunch3r
- **Enhanced Version** - Comprehensive feature additions and improvements

## Support

For issues, suggestions, or contributions:
- **GitHub Repository**: [Sh1tmunch3r/builderMenu](https://github.com/Sh1tmunch3r/builderMenu)
- **Issues**: Report bugs and request features through GitHub Issues
- **Pull Requests**: Contribute improvements and fixes

---

**Enjoy building amazing structures with ease!** 🏗️✨
