package moe.nea.jarvis.api;

import net.minecraft.client.gui.screen.Screen;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * Obtained through {@link JarvisPlugin#onInitialize(Jarvis)}
 */
public abstract class Jarvis {

    /**
     * @return a stream of all plugins registered with jarvis
     */
    public abstract Stream<JarvisPlugin> getAllPlugins();

    /**
     * @return a stream of all HUDs registered by all plugins
     */
    public abstract Stream<JarvisHud> getAllHuds();

    /**
     * Get a HUD editor screen. You need to manually tell minecraft to display this screen. By default, displays all HUDs
     * according to {@link #getAllHuds()}
     *
     * @param lastScreen the screen to return to once the screen is closed.
     */
    public abstract Screen getHudEditor(Screen lastScreen);

    /**
     * @param hudList the list of HUDs to display
     * @see #getHudEditor(Screen)
     */
    public abstract Screen getHudEditor(Screen lastScreen, List<JarvisHud> hudList);

    /**
     * @see  #getHudEditor(Screen)
     * @param hudFilter filter the HUDs returned by {@link #getAllHuds()} before displaying them.
     */
    public abstract Screen getHudEditor(Screen lastScreen, BiPredicate<JarvisPlugin, JarvisHud> hudFilter);

}
