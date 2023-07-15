/*
 * This file is part of Jarvis Common Config Index (Jarvis).
 *
 * Copyright (C) 2023 Linnea Gräf
 *
 * Jarvis is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Jarvis is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Jarvis.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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
        return getWidth() * getScale();
    }

    /**
     * @return the effective scale of this, which includes the global minecraft window scale.
     */
    default float getEffectiveScale() {
        return (float) (getScale() * MinecraftClient.getInstance().getWindow().getScaleFactor());
    }

}
