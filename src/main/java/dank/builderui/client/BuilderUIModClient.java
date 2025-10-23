package dank.builderui.client;

import dank.builderui.client.screen.BuilderUIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.minecraft.text.Text;

/**
 * Builder UI Mod - Client-side initialization
 * 
 * Handles client-only features:
 * - Keybinding registration for opening the builder menu
 * - UI screen management
 * - Client tick events
 * 
 * Default keybinding: B key (configurable in Minecraft controls)
 * 
 * @author dank
 * @version 0.2-1.20.1
 */
public class BuilderUIModClient implements ClientModInitializer {

    private static KeyBinding openMenuKey;

    /**
     * Initializes client-side components.
     * Registers the keybinding for opening the builder menu.
     */
    @Override
    public void onInitializeClient() {
        // Register keybinding for opening the builder menu
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.builderui.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.builderui.controls"
        ));

        // Register tick event to handle key presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new BuilderUIScreen(Text.of("Builder Menu")));
                }
            }
        });
    }
}
