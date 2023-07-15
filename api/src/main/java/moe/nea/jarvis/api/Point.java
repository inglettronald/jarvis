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
