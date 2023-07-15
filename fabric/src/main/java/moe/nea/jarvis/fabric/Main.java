package moe.nea.jarvis.fabric;

import moe.nea.jarvis.api.JarvisHud;
import moe.nea.jarvis.impl.JarvisHudEditor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Arrays;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Main implements ClientModInitializer {
    JarvisHud hud = new JarvisHud() {
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
