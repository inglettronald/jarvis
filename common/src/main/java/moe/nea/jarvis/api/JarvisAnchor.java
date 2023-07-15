package moe.nea.jarvis.api;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Pair;

public enum JarvisAnchor {
    TOP_LEFT(0, 0),
    TOP_CENTER(0.5F, 0),
    TOP_RIGHT(1, 0),
    CENTER_LEFT(0, 0.5F),
    CENTER_CENTER(0.5F, 0.5F),
    CENTER_RIGHT(1F, 0.5F),
    BOTTOM_LEFT(0, 1F),
    BOTTOM_CENTER(0.5F, 1F),
    BOTTOM_RIGHT(1F, 1F),
    ;

    private final float xPosition;
    private final float yPosition;

    JarvisAnchor(float xPosition, float yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public float getRelativeXPosition() {
        return xPosition;
    }

    public float getRelativeYPosition() {
        return yPosition;
    }

    public void transformTo(JarvisAnchor targetCoordinateSpace, MatrixStack matrixStack, double width, double height) {
        matrixStack.translate(
                width * (targetCoordinateSpace.xPosition - xPosition),
                height * (targetCoordinateSpace.yPosition - yPosition),
                0.0
        );
    }

    public Pair<Double, Double> translate(JarvisAnchor targetCoordinateSpace, double x, double y, double width, double height) {
        return new Pair<>(
                x + width * (targetCoordinateSpace.xPosition - xPosition),
                y + height * (targetCoordinateSpace.yPosition - yPosition)
        );
    }
}
