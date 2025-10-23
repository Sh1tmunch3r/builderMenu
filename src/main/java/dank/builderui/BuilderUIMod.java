package dank.builderui;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuilderUIMod implements ModInitializer {
    public static final String MOD_ID = "builderui";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Builder UI Mod initialized!");
    }
}
