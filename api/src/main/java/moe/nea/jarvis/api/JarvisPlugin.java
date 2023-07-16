package moe.nea.jarvis.api;

import java.util.Collections;
import java.util.List;

public interface JarvisPlugin {
    /**
     * Get all HUDs that are made available by this plugin.
     * This includes all disabled HUDs. The returned list of HUDs
     * should not change at any point during the course of the game.
     * If values have to change it might be required to
     */
    default List<JarvisHud> getAllHuds() {
        return Collections.emptyList();
    }

    String getModId();

    default void onInitialize(Jarvis jarvis) {
    }
}
