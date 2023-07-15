package moe.nea.jarvis.impl;

import moe.nea.jarvis.api.JarvisAnchor;
import moe.nea.jarvis.api.JarvisHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class JarvisHudEditor extends Screen {
    private final List<JarvisHud> huds;
    private final Map<JarvisHud, BinaryInterpolator> hoverProgress = new IdentityHashMap<>();
    private JarvisHud grabbedHud;
    private double grabbedHudAnchorX, grabbedHudAnchorY;

    public JarvisHudEditor(List<JarvisHud> huds) {
        super(Text.translatable("jarvis.editor"));
        this.huds = huds;
        for (JarvisHud hud : huds) {
            hoverProgress.put(hud, new BinaryInterpolator(200));
        }
    }

    private boolean isOverlayHovered(JarvisHud hud, double mouseX, double mouseY) {
        int absoluteX = hud.getAbsoluteX();
        int absoluteY = hud.getAbsoluteY();
        return absoluteX < mouseX && mouseX < absoluteX + hud.getEffectiveWidth()
                && absoluteY < mouseY && mouseY < absoluteY + hud.getEffectiveHeight();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(client.textRenderer, Text.translatable("jarvis.editor.title"), width / 2, 20, -1);
        boolean hasHoveredAny = grabbedHud != null;
        for (JarvisHud hud : huds) {
            context.getMatrices().push();
            hud.applyTransformations(context.getMatrices());
            hud.getAnchor().transformTo(JarvisAnchor.TOP_LEFT, context.getMatrices(), hud.getWidth(), hud.getHeight());
            boolean hovered = grabbedHud == hud;
            if (!hasHoveredAny && isOverlayHovered(hud, mouseX, mouseY)) {
                hovered = true;
                hasHoveredAny = true;
            }
            BinaryInterpolator hoverInterpolator = hoverProgress.get(hud);
            hoverInterpolator.lerpTo(hovered ? 1 : 0);
            fillFadeOut(context, hud.getWidth(), hud.getHeight(), hud.getFadeOutPercentage());
            context.drawBorder(0, 0, hud.getWidth(), hud.getHeight(),
                    hoverInterpolator.lerp(new Color(0xFF343738, true), new Color(0xFF85858A, true)).getRGB()
            );
            context.drawCenteredTextWithShadow(client.textRenderer, hud.getLabel(), hud.getWidth() / 2, hud.getHeight() / 2, -1);
            context.getMatrices().pop();
        }
    }

    public void fillFadeOut(DrawContext drawContext, int width, int height, float opaquePercentage) {
        if (opaquePercentage >= 1F) {
            drawContext.fill(0, 0, width, height, 0x80000000);
            return;
        }
        VertexConsumer vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderLayer.getGui());
        float translucentStart = opaquePercentage * height;
        float translucentEnd = height;
        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
        vertexConsumer.vertex(matrix4f, 0, 0, 0).color(0x0, 0x0, 0x0, 0x80).next();
        vertexConsumer.vertex(matrix4f, 0, translucentStart, 0).color(0x0, 0x0, 0x0, 0x80).next();
        vertexConsumer.vertex(matrix4f, width, translucentStart, 0).color(0x0, 0x0, 0x0, 0x80).next();
        vertexConsumer.vertex(matrix4f, width, 0, 0).color(0x0, 0x0, 0x0, 0x80).next();
        vertexConsumer.vertex(matrix4f, 0, translucentStart, 0).color(0x0, 0x0, 0x0, 0x80).next();
        vertexConsumer.vertex(matrix4f, 0, translucentEnd, 0).color(0x0, 0x0, 0x0, 0).next();
        vertexConsumer.vertex(matrix4f, width, translucentEnd, 0).color(0x0, 0x0, 0x0, 0).next();
        vertexConsumer.vertex(matrix4f, width, translucentStart, 0).color(0x0, 0x0, 0x0, 0x80).next();
        drawContext.draw();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            tryGrabOverlay(mouseX, mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            tryReleaseOverlay();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        tryMoveGrabbedOverlay(mouseX, mouseY);
        super.mouseMoved(mouseX, mouseY);
    }

    public void tryGrabOverlay(double mouseX, double mouseY) {
        for (JarvisHud hud : huds) {
            if (isOverlayHovered(hud, mouseX, mouseY)) {
                grabbedHud = hud;
                grabbedHudAnchorX = mouseX - hud.getAbsoluteX();
                grabbedHudAnchorY = mouseY - hud.getAbsoluteY();
            }
        }
    }

    public void tryMoveGrabbedOverlay(double mouseX, double mouseY) {
        JarvisHud grabbedHud = this.grabbedHud;
        if (grabbedHud == null) return;
        double x = mouseX - grabbedHudAnchorX;
        double y = mouseY - grabbedHudAnchorY;
        Pair<Double, Double> inTopLeftSpace = grabbedHud.getAnchor().translate(JarvisAnchor.TOP_LEFT, x, y, grabbedHud.getEffectiveWidth(), grabbedHud.getEffectiveHeight());
        Pair<Double, Double> inOriginalSpace = JarvisAnchor.TOP_LEFT.translate(grabbedHud.getAnchor(),
                Util.coerce(inTopLeftSpace.getLeft(), 0, client.getWindow().getScaledWidth() - grabbedHud.getEffectiveWidth()),
                Util.coerce(inTopLeftSpace.getRight(), 0, client.getWindow().getScaledHeight() - grabbedHud.getEffectiveHeight()),
                grabbedHud.getEffectiveWidth(),
                grabbedHud.getEffectiveHeight()
        );
        grabbedHud.setX(inOriginalSpace.getLeft() / (client.getWindow().getScaledWidth() - grabbedHud.getEffectiveWidth()));
        grabbedHud.setY(inOriginalSpace.getRight() / (client.getWindow().getScaledHeight() - grabbedHud.getEffectiveHeight()));
    }

    public void tryReleaseOverlay() {
        grabbedHud = null;
    }
}
