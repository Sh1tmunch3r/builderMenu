package dank.builderui.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dank.builderui.util.StructureBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BuilderUIScreen extends Screen {

    private static final Identifier BG_TEXTURE =
            new Identifier("minecraft", "textures/gui/demo_background.png");

    public BuilderUIScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        addDrawableChild(ButtonWidget.builder(Text.of("Build House"), button -> {
            StructureBuilder.buildHouse(client.player);
            client.setScreen(null);
        }).dimensions(cx - 75, cy - 30, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.of("Build Tower"), button -> {
            StructureBuilder.buildTower(client.player);
            client.setScreen(null);
        }).dimensions(cx - 75, cy, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.of("Cancel"), button -> {
            client.setScreen(null);
        }).dimensions(cx - 75, cy + 30, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        context.drawTexture(BG_TEXTURE, 0, 0, 0, 0, this.width, this.height, 248, 166);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
