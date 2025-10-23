package dank.builderui.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dank.builderui.util.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

/**
 * Enhanced Builder UI Screen with categories, scrolling, tooltips, and configuration options.
 */
public class BuilderUIScreen extends Screen {

    private static final Identifier BG_TEXTURE =
            new Identifier("minecraft", "textures/gui/demo_background.png");
    
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 4;
    private static final int CATEGORY_SPACING = 10;
    
    private BuildCategory selectedCategory = null;
    private BuildType selectedBuildType = null;
    private BuildConfig currentConfig = null;
    private int scrollOffset = 0;
    private int maxScroll = 0;
    private List<ButtonWidget> buildButtons = new ArrayList<>();
    private String hoveredTooltip = null;

    public BuilderUIScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        buildButtons.clear();
        
        int centerX = this.width / 2;
        int startY = 60;
        
        if (selectedBuildType != null) {
            // Configuration screen
            initConfigScreen(centerX, startY);
        } else if (selectedCategory != null) {
            // Build selection screen for a category
            initBuildSelectionScreen(centerX, startY);
        } else {
            // Main category selection screen
            initCategoryScreen(centerX, startY);
        }
    }

    private void initCategoryScreen(int centerX, int startY) {
        int y = startY;
        
        // Add category buttons
        for (BuildCategory category : BuildCategory.values()) {
            ButtonWidget button = ButtonWidget.builder(
                Text.of(category.getDisplayName()),
                btn -> {
                    selectedCategory = category;
                    clearAndInit();
                }
            ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
            
            addDrawableChild(button);
            y += BUTTON_HEIGHT + BUTTON_SPACING;
        }
        
        // Add undo button
        y += CATEGORY_SPACING;
        ButtonWidget undoButton = ButtonWidget.builder(
            Text.of("Undo Last Build"),
            btn -> {
                if (UndoManager.canUndo(client.player.getUuid())) {
                    UndoManager.undo(client.player.getUuid(), client.player.getWorld());
                    client.player.sendMessage(Text.of("§aUndo successful!"), false);
                } else {
                    client.player.sendMessage(Text.of("§cNothing to undo!"), false);
                }
            }
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(undoButton);
        
        // Add close button
        y += BUTTON_HEIGHT + CATEGORY_SPACING;
        ButtonWidget closeButton = ButtonWidget.builder(
            Text.of("Close"),
            btn -> client.setScreen(null)
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(closeButton);
    }

    private void initBuildSelectionScreen(int centerX, int startY) {
        int y = startY;
        
        // Filter build types by category
        List<BuildType> buildsInCategory = new ArrayList<>();
        for (BuildType buildType : BuildType.values()) {
            if (buildType.getCategory() == selectedCategory) {
                buildsInCategory.add(buildType);
            }
        }
        
        // Add build type buttons
        for (BuildType buildType : buildsInCategory) {
            final BuildType type = buildType;
            ButtonWidget button = ButtonWidget.builder(
                Text.of(buildType.getDisplayName()),
                btn -> {
                    selectedBuildType = type;
                    currentConfig = new BuildConfig(type);
                    clearAndInit();
                }
            ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
            
            buildButtons.add(button);
            addDrawableChild(button);
            y += BUTTON_HEIGHT + BUTTON_SPACING;
        }
        
        // Add back button
        y += CATEGORY_SPACING;
        ButtonWidget backButton = ButtonWidget.builder(
            Text.of("← Back"),
            btn -> {
                selectedCategory = null;
                clearAndInit();
            }
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(backButton);
    }

    private void initConfigScreen(int centerX, int startY) {
        int y = startY;
        int halfWidth = BUTTON_WIDTH / 2 - 2;
        
        // Build type title
        y += 20;
        
        // Material selection
        ButtonWidget materialButton = ButtonWidget.builder(
            Text.of("Material: " + currentConfig.getMaterial().getDisplayName()),
            btn -> cycleMaterial()
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(materialButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Size selection
        ButtonWidget sizeButton = ButtonWidget.builder(
            Text.of("Size: " + currentConfig.getSize().getDisplayName()),
            btn -> cycleSize()
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(sizeButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Style selection
        ButtonWidget styleButton = ButtonWidget.builder(
            Text.of("Style: " + currentConfig.getStyle().getDisplayName()),
            btn -> cycleStyle()
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(styleButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Rotation buttons
        ButtonWidget rotateLeftButton = ButtonWidget.builder(
            Text.of("↺ Rotate Left"),
            btn -> {
                currentConfig.setRotation(currentConfig.getRotation() - 90);
                clearAndInit();
            }
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, halfWidth, BUTTON_HEIGHT).build();
        addDrawableChild(rotateLeftButton);
        
        ButtonWidget rotateRightButton = ButtonWidget.builder(
            Text.of("Rotate Right ↻"),
            btn -> {
                currentConfig.setRotation(currentConfig.getRotation() + 90);
                clearAndInit();
            }
        ).dimensions(centerX + 2, y, halfWidth, BUTTON_HEIGHT).build();
        addDrawableChild(rotateRightButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Show info button
        y += CATEGORY_SPACING;
        ButtonWidget infoButton = ButtonWidget.builder(
            Text.of("ℹ Show Info"),
            btn -> showBuildInfo()
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(infoButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Preview button
        ButtonWidget previewButton = ButtonWidget.builder(
            Text.of("👁 Preview"),
            btn -> {
                client.player.sendMessage(Text.of("§ePreview feature coming soon!"), false);
            }
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(previewButton);
        y += BUTTON_HEIGHT + CATEGORY_SPACING;
        
        // Build button (highlighted)
        ButtonWidget buildButton = ButtonWidget.builder(
            Text.of("✓ Build!"),
            btn -> confirmBuild()
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(buildButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;
        
        // Back button
        ButtonWidget backButton = ButtonWidget.builder(
            Text.of("← Back"),
            btn -> {
                selectedBuildType = null;
                currentConfig = null;
                clearAndInit();
            }
        ).dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        addDrawableChild(backButton);
    }

    private void cycleMaterial() {
        MaterialType[] materials = MaterialType.values();
        int currentIndex = currentConfig.getMaterial().ordinal();
        int nextIndex = (currentIndex + 1) % materials.length;
        currentConfig.setMaterial(materials[nextIndex]);
        clearAndInit();
    }

    private void cycleSize() {
        BuildSize[] sizes = BuildSize.values();
        int currentIndex = currentConfig.getSize().ordinal();
        int nextIndex = (currentIndex + 1) % sizes.length;
        currentConfig.setSize(sizes[nextIndex]);
        clearAndInit();
    }

    private void cycleStyle() {
        BuildStyle[] styles = BuildStyle.values();
        int currentIndex = currentConfig.getStyle().ordinal();
        int nextIndex = (currentIndex + 1) % styles.length;
        currentConfig.setStyle(styles[nextIndex]);
        clearAndInit();
    }

    private void showBuildInfo() {
        if (currentConfig != null) {
            BuildInfo info = StructureBuilder.getBuildInfo(
                currentConfig.getBuildType(), 
                currentConfig.getSize()
            );
            
            StringBuilder message = new StringBuilder();
            message.append("§e").append(info.getBuildType().getDisplayName()).append("\n");
            message.append("§7").append(info.getDescription()).append("\n");
            message.append("§fTotal Blocks: §a").append(info.getTotalBlocks()).append("\n");
            message.append("§fEstimated Time: §a").append(info.getFormattedTime()).append("\n");
            message.append("§fBlocks Needed:\n");
            
            for (Map.Entry<String, Integer> entry : info.getBlocksNeeded().entrySet()) {
                message.append("  §7- §f").append(entry.getKey()).append(": §a").append(entry.getValue()).append("\n");
            }
            
            client.player.sendMessage(Text.of(message.toString()), false);
        }
    }

    private void confirmBuild() {
        if (currentConfig != null && client.player != null) {
            // Show confirmation
            client.player.sendMessage(Text.of("§aBuilding " + currentConfig.getBuildType().getDisplayName() + "..."), false);
            
            // Execute build
            StructureBuilder.build(client.player, currentConfig);
            
            // Close screen
            client.setScreen(null);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render background
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        context.drawTexture(BG_TEXTURE, 0, 0, 0, 0, this.width, this.height, 248, 166);
        
        super.render(context, mouseX, mouseY, delta);
        
        // Render title
        String titleText = getScreenTitle();
        context.drawCenteredTextWithShadow(textRenderer, titleText, this.width / 2, 20, 0xFFFFFF);
        
        // Render subtitle if in config screen
        if (selectedBuildType != null && currentConfig != null) {
            String subtitle = "Configure your " + selectedBuildType.getDisplayName();
            context.drawCenteredTextWithShadow(textRenderer, subtitle, this.width / 2, 35, 0xAAAAAA);
        }
        
        // Render tooltip if hovering over something
        if (hoveredTooltip != null) {
            List<Text> tooltipLines = new ArrayList<>();
            tooltipLines.add(Text.of(hoveredTooltip));
            context.drawTooltip(textRenderer, tooltipLines, mouseX, mouseY);
        }
    }

    private String getScreenTitle() {
        if (selectedBuildType != null) {
            return "Builder Menu - Configure";
        } else if (selectedCategory != null) {
            return "Builder Menu - " + selectedCategory.getDisplayName();
        } else {
            return "Builder Menu - Categories";
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // Handle scrolling for long lists
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset - (int)(verticalAmount * 10)));
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

