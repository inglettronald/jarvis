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

package moe.nea.jarvis.fabric;

import moe.nea.jarvis.api.JarvisHud;
import moe.nea.jarvis.api.JarvisScalable;
import moe.nea.jarvis.impl.JarvisHudEditor;
import moe.nea.jarvis.impl.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Arrays;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Main implements ClientModInitializer {
    JarvisHud hud = new JarvisScalable() {
        @Override
        public float getScale() {
            return (float) Util.coerce(scale, 0.1F, 10F);
        }

        @Override
        public void setScale(float newScale) {
            scale = newScale;
        }

        float scale = 1F;
        double x;
        double y;

        @Override
        public double getX() {
            return x;
        }

        @Override
        public void setX(double newX) {
            x = newX;
        }

        @Override
        public double getY() {
            return y;
        }

        @Override
        public void setY(double newY) {
            y = newY;
        }

        @Override
        public Text getLabel() {
            return Text.literal("Test HUD Element");
        }

        @Override
        public int getContentHeight() {
            return 10000;
        }

        @Override
        public int getWidth() {
            return 200;
        }

        @Override
        public int getHeight() {
            return 300;
        }
    };

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(literal("jarvis").executes(context -> {
                MinecraftClient.getInstance().send(() -> {
                    MinecraftClient.getInstance().setScreen(new JarvisHudEditor(Arrays.asList(hud)));
                });
                return 0;
            }));
        });
    }
}
