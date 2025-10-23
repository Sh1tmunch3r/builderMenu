# Builder Menu - UI Navigation Guide

This guide provides a visual walkthrough of the enhanced Builder Menu UI.

## UI Flow Diagram

```
┌─────────────────────────────────────┐
│      Builder Menu - Categories      │
├─────────────────────────────────────┤
│                                     │
│         [  Buildings  ]             │
│         [ Agricultural ]            │
│         [Infrastructure]            │
│         [ Decorative  ]             │
│         [   Custom    ]             │
│                                     │
│       [ Undo Last Build ]           │
│           [ Close ]                 │
│                                     │
└─────────────────────────────────────┘
             │
             ├──→ Select Category (e.g., Buildings)
             │
             ▼
┌─────────────────────────────────────┐
│   Builder Menu - Buildings          │
├─────────────────────────────────────┤
│                                     │
│          [  House  ]                │
│          [ Mansion ]                │
│          [  Tower  ]                │
│          [ Castle  ]                │
│                                     │
│          [ ← Back ]                 │
│                                     │
└─────────────────────────────────────┘
             │
             ├──→ Select Build (e.g., House)
             │
             ▼
┌─────────────────────────────────────┐
│  Builder Menu - Configure           │
│    Configure your House             │
├─────────────────────────────────────┤
│                                     │
│    [ Material: Wood       ▼ ]      │
│    [ Size: Medium         ▼ ]      │
│    [ Style: Standard      ▼ ]      │
│    [ ↺ Rotate Left | Rotate Right ↻]│
│                                     │
│         [ ℹ Show Info ]             │
│         [ 👁 Preview ]              │
│                                     │
│          [ ✓ Build! ]               │
│          [ ← Back ]                 │
│                                     │
└─────────────────────────────────────┘
```

## Screen Details

### 1. Category Selection Screen

**Title**: "Builder Menu - Categories"

**Purpose**: Main entry point for selecting build category

**Elements**:
- 5 Category Buttons (Buildings, Agricultural, Infrastructure, Decorative, Custom)
- Undo Button (reverts last build)
- Close Button (exits menu)

**Interaction**:
- Click category → Navigate to build selection
- Click Undo → Revert last structure
- Click Close → Return to game

---

### 2. Build Selection Screen

**Title**: "Builder Menu - [Category Name]"

**Purpose**: Select specific structure type within category

**Elements**:
- Build Type Buttons (varies by category)
  - Buildings: House, Mansion, Tower, Castle
  - Agricultural: Farm
  - Infrastructure: Bridge, Road, Wall
  - Decorative: Fountain, Treehouse
  - Custom: Custom Structure
- Back Button (return to categories)

**Interaction**:
- Click build type → Navigate to configuration
- Click Back → Return to category selection

---

### 3. Configuration Screen

**Title**: "Builder Menu - Configure"
**Subtitle**: "Configure your [Build Type]"

**Purpose**: Customize build before construction

**Elements**:

#### Material Selector
```
[ Material: Wood ▼ ]
```
- Click to cycle through 9 materials
- Options: Wood, Stone, Brick, Glass, Sandstone, Dark Oak, Quartz, Prismarine, Concrete
- Updates immediately on click

#### Size Selector
```
[ Size: Medium ▼ ]
```
- Click to cycle through 4 sizes
- Options: Small (0.5x), Medium (1.0x), Large (1.5x), Extra Large (2.0x)
- Affects structure dimensions

#### Style Selector
```
[ Style: Standard ▼ ]
```
- Click to cycle through 5 styles
- Options: Standard, Modern, Medieval, Rustic, Futuristic
- Framework for future style variations

#### Rotation Controls
```
[ ↺ Rotate Left | Rotate Right ↻ ]
```
- Two buttons for rotation
- Rotates in 90° increments
- Four total orientations (0°, 90°, 180°, 270°)

#### Information Button
```
[ ℹ Show Info ]
```
- Displays build details in chat:
  - Structure description
  - Total blocks required
  - Estimated build time
  - Material breakdown

#### Preview Button
```
[ 👁 Preview ]
```
- Placeholder for future preview feature
- Will show ghost blocks before building

#### Build Button
```
[ ✓ Build! ]
```
- Large highlighted button
- Confirms and executes construction
- Closes menu after building

#### Back Button
```
[ ← Back ]
```
- Returns to build selection screen
- Preserves current configuration

**Interaction Flow**:
1. Adjust Material → Cycle through options
2. Adjust Size → Choose scale
3. Adjust Style → Select aesthetic
4. Rotate → Orient structure
5. Show Info → Review requirements
6. Preview → See placement (future)
7. Build! → Confirm and construct
8. Back → Cancel and return

---

## Button Dimensions

All buttons follow consistent sizing:
- **Width**: 140 pixels
- **Height**: 20 pixels
- **Spacing**: 4 pixels between buttons
- **Category Spacing**: 10 pixels between sections

Split buttons (like Rotate):
- **Width**: 69 pixels each (half width - 2px gap)
- **Height**: 20 pixels
- **Gap**: 4 pixels between buttons

---

## Color Scheme

Current implementation uses Minecraft's default:
- **Background**: Demo background texture
- **Text**: White (#FFFFFF) for titles
- **Subtitle**: Light gray (#AAAAAA)
- **Buttons**: Default Minecraft button style
- **Tooltips**: Yellow text on dark background

---

## Keyboard Controls

- **B** - Open Builder Menu (configurable)
- **ESC** - Close menu or go back
- **Mouse Scroll** - Scroll through lists (infrastructure ready)
- **Click** - Select options and buttons

---

## Chat Messages

### Build Info Example
```
§eHouse
§7A cozy house with walls, floor, and roof
§fTotal Blocks: §a86
§fEstimated Time: §a5 seconds
§fBlocks Needed:
  §7- §fPrimary Material: §a50
  §7- §fRoof Material: §a36
```

### Success Message
```
§aBuilding House...
```

### Undo Messages
```
§aUndo successful!
```
or
```
§cNothing to undo!
```

---

## Screen Transitions

### Navigation Path Examples

#### Building a House
```
Main Menu → Buildings → House → Configure → Build!
   (B)        (Click)    (Click)   (Adjust)   (Click)
```

#### Using Undo
```
Main Menu → Undo Last Build
   (B)        (Click)
```

#### Changing Mind
```
Main Menu → Buildings → House → Back → Back → Close
   (B)        (Click)    (Click)  (Click) (Click) (ESC)
```

---

## Layout Specifications

### Center Alignment
- All buttons centered horizontally
- X position: `screenWidth / 2 - buttonWidth / 2`
- Vertical spacing from top title: 60 pixels

### Title Position
- X: Center of screen (`screenWidth / 2`)
- Y: 20 pixels from top
- Font: Default Minecraft with shadow

### Subtitle Position
- X: Center of screen (`screenWidth / 2`)
- Y: 35 pixels from top
- Font: Default Minecraft with shadow
- Color: Light gray (#AAAAAA)

---

## Accessibility Features

### Current
- Clear text labels on all buttons
- Consistent button sizing
- Logical navigation hierarchy
- Back button always available
- Close button accessible from main menu

### Future (Planned)
- Tooltips on hover
- Keyboard shortcuts
- Search functionality
- Customizable UI colors
- Font size options
- Screen reader support

---

## UI States

### Normal State
- All buttons enabled
- Full interaction available

### Hover State
- Minecraft default hover effect
- Lighter button appearance

### Disabled State (Future)
- Grayed out buttons
- Tooltip explaining why disabled
- Example: "Not enough materials" in survival

---

## Responsive Design

### Small Screens
- Buttons maintain fixed size
- May require scrolling (infrastructure ready)
- Minimum recommended: 800x600

### Large Screens
- Buttons remain centered
- Extra space on sides
- Optimal viewing on any size

### Ultrawide
- UI elements centered
- No stretching
- Clean appearance

---

## Configuration Persistence

### Current Session
- Configuration persists within menu
- Resets when menu closed
- Changes apply immediately

### Future (Planned)
- Save last used configuration
- Per-build-type preferences
- Quick presets

---

## Examples of Full UI Text

### Main Menu
```
┌─────────────────────────────────────┐
│   Builder Menu - Categories         │
│                                     │
│         Buildings                   │
│         Agricultural                │
│         Infrastructure              │
│         Decorative                  │
│         Custom                      │
│                                     │
│       Undo Last Build               │
│           Close                     │
└─────────────────────────────────────┘
```

### Configuration Screen
```
┌─────────────────────────────────────┐
│    Builder Menu - Configure         │
│      Configure your Castle          │
│                                     │
│   Material: Prismarine              │
│   Size: Large                       │
│   Style: Medieval                   │
│   ↺ Rotate Left | Rotate Right ↻   │
│                                     │
│         ℹ Show Info                 │
│         👁 Preview                  │
│                                     │
│          ✓ Build!                   │
│          ← Back                     │
└─────────────────────────────────────┘
```

---

## Tips for Users

### First Time Use
1. Press **B** to open menu
2. Browse **Categories** to see options
3. Select a **Build Type** you like
4. **Configure** material and size
5. Click **Show Info** to see requirements
6. Click **Build!** when ready

### Making Changes
1. Use cycle buttons to try different materials
2. Size affects resource needs - start small
3. Rotation helps fit terrain
4. Check info before large builds
5. Undo is always available

### Best Practices
1. Review build info first
2. Choose appropriate material for style
3. Consider size for location
4. Use undo to experiment
5. Save favorites as custom structures (future)

---

## Future UI Enhancements

### Planned Features
- **Visual Preview**: Ghost blocks showing placement
- **Material Preview**: See structure with different materials
- **Favorites**: Quick access to common builds
- **Search Bar**: Find structures quickly
- **Filter Options**: Filter by category, size, material
- **Recent Builds**: Quick access to last built
- **Build Queue**: Queue multiple structures
- **Help Tooltips**: Hover for more information
- **Confirmation Dialog**: "Are you sure?" for large builds
- **Progress Bar**: Show build progress (large structures)

### UI Improvements
- Icons instead of/alongside text
- Animated transitions
- Custom textures for buttons
- Color-coded categories
- Preview thumbnails
- Material swatches
- Size comparison visuals

---

## Technical Notes

### Implementation
- Uses Minecraft's Screen class
- ButtonWidget for all buttons
- DrawContext for rendering
- Text rendering with shadows

### Performance
- Minimal render overhead
- Instant screen transitions
- Efficient button management
- No lag on slower systems

### Compatibility
- Works with other UI mods
- Respects Minecraft GUI scale
- Compatible with texture packs
- Multiplayer friendly

---

This guide will be updated as new UI features are added!
