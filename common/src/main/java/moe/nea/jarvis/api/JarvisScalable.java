package moe.nea.jarvis.api;

import net.minecraft.client.MinecraftClient;

/**
 * Implement this in addition to {@link JarvisHud}
 */
public interface JarvisScalable extends JarvisHud {
    /**
     * @return the local scale of this hud element.
     */
    float getScale();

    /**
     * Set the local scale of this hud element.
     *
     * @param newScale the new scale of this hud element
     */
    void setScale(float newScale);

    @Override
    default double getEffectiveHeight() {
        return getHeight() * getScale();
    }

    @Override
    default double getEffectiveWidth() {
        return getHeight() * getScale();
    }

    /**
     * @return the effective scale of this, which includes the global minecraft window scale.
     */
    default float getEffectiveScale() {
        return (float) (getScale() * MinecraftClient.getInstance().getWindow().getScaleFactor());
    }

}
