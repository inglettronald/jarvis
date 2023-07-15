package moe.nea.jarvis.api;

import net.minecraft.client.util.math.MatrixStack;

public enum JarvisAnchor {
    TOP_LEFT(0, 0, 8),
    TOP_CENTER(0.5F, 0, 7),
    TOP_RIGHT(1, 0, 6),
    CENTER_LEFT(0, 0.5F, 5),
    CENTER_CENTER(0.5F, 0.5F, 4),
    CENTER_RIGHT(1F, 0.5F, 3),
    BOTTOM_LEFT(0, 1F, 2),
    BOTTOM_CENTER(0.5F, 1F, 1),
    BOTTOM_RIGHT(1F, 1F, 0),
    ;

    private final int opposite;
    private final float xPosition;
    private final float yPosition;

    JarvisAnchor(float xPosition, float yPosition, int opposite) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.opposite = opposite;
    }

    public JarvisAnchor getOpposite() {
        return values()[opposite];
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

    public Point translate(JarvisAnchor targetCoordinateSpace, double x, double y, double width, double height) {
        return new Point(
                x + width * (targetCoordinateSpace.xPosition - xPosition),
                y + height * (targetCoordinateSpace.yPosition - yPosition)
        );
    }

    public static JarvisAnchor byQuadrant(boolean isLeftHalf, boolean isTopHalf) {
        return isLeftHalf ? (isTopHalf ? TOP_LEFT : BOTTOM_LEFT) : (isTopHalf ? TOP_RIGHT : BOTTOM_RIGHT);
    }
}
