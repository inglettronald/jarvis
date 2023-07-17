package moe.nea.jarvis.impl.test;

import moe.nea.jarvis.api.JarvisConstants;
import moe.nea.jarvis.api.JarvisHud;
import moe.nea.jarvis.api.JarvisPlugin;
import moe.nea.jarvis.api.JarvisScalable;
import moe.nea.jarvis.impl.JarvisContainer;
import moe.nea.jarvis.impl.JarvisUtil;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

public class TestPluginClass implements JarvisPlugin {
    JarvisHud hud = new JarvisScalable() {
        @Override
        public float getScale() {
            return (float) JarvisUtil.coerce(scale, 0.1F, 10F);
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
    public List<JarvisHud> getAllHuds() {
        return Arrays.asList(hud);
    }

    @Override
    public String getModId() {
        return JarvisConstants.MODID;
    }
}
