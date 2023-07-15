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

package moe.nea.jarvis.impl;

import java.awt.*;

public class Util {
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
