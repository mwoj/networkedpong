package org.networkedpong.client;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Renderer2D {
    public static void renderLine(float x1, float y1, float x2, float y2, float thickness) {
        GL.createCapabilities();
        GL11.glLineWidth(thickness);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
    }

    public static void renderRect(float x, float y, float width, float height) {
        GL.createCapabilities();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();
    }

    public static void renderCircle(float x, float y, float radius, int segments) {
        GL.createCapabilities();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(x, y);
        for (int i = 0; i <= segments; i++) {
            float angle = i * 2.0f * (float) Math.PI / segments;
            float dx = (float) Math.cos(angle) * radius;
            float dy = (float) Math.sin(angle) * radius;
            GL11.glVertex2f(x + dx, y + dy);
        }
        GL11.glEnd();
    }
}
