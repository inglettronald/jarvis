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

public record Point(double x, double y) {
    public double squaredDistanceTo(Point other) {
        double distX = other.x - x;
        double distY = other.y - y;
        return distX * distX + distY + distY;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(squaredDistanceTo(other));
    }
}
