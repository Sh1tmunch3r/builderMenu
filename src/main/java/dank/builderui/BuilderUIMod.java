package dank.builderui;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder UI Mod - Server-side initialization
 * 
 * A comprehensive Minecraft mod that provides an intuitive builder menu
 * for constructing various structures with customizable materials, sizes, and styles.
 * 
 * Features:
 * - 11 different structure types across 5 categories
 * - 9 material options with primary, secondary, and decorative blocks
 * - 4 size scales from small to extra large
 * - 5 architectural styles
 * - Undo functionality for build mistakes
 * - Custom structure saving and loading
 * - Detailed build information and previews
 * 
 * @author dank
 * @version 0.2-1.20.1
 */
public class BuilderUIMod implements ModInitializer {
    public static final String MOD_ID = "builderui";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Builder UI Mod initialized!");
        LOGGER.info("Press 'B' to open the Builder Menu!");
    }
}
