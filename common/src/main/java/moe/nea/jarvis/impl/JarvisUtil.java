package moe.nea.jarvis.impl;

import java.awt.*;

public class JarvisUtil {

    public static boolean isTest = Boolean.getBoolean("jarvis.test");

    public static double coerce(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

    public static int lerp(int start, int end, double progress) {
        return (int) ((end - start) * progress + start);
    }

    public static double lerp(double start, double end, double progress) {
        return (end - start) * progress + start;
    }

    public static Color lerpColor(Color startC, Color endC, double progress) {
        return new Color(
                lerp(startC.getRed(), endC.getRed(), progress),
                lerp(startC.getGreen(), endC.getGreen(), progress),
                lerp(startC.getBlue(), endC.getBlue(), progress),
                lerp(startC.getAlpha(), endC.getAlpha(), progress)
        );
    }
}
