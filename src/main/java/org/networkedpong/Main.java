package org.networkedpong;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.networkedpong.client.Renderer2D;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11C.*;

public class Main {
    private long windowHandle;

    private final int windowWidth = 1280;
    private final int windowHeight = 720;

    public void run() {
        try {
            init();
            loop();
            glfwDestroyWindow(windowHandle);
        } finally {
            glfwTerminate();
        }
    }

    private void init() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Create a window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        windowHandle = glfwCreateWindow(windowWidth, windowHeight, "Networked Pong", 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        // Enable v-sync
        glfwSwapInterval(1);

        // Required to initialize OpenGL.
        createCapabilities();

        // Set the viewport
        glViewport(0, 0, windowWidth, windowHeight);

        // Show the window
        glfwShowWindow(windowHandle);
    }

    private void loop() {
        // Set the clear color to white
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Enable blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Set up projection matrix
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, windowWidth, 0.0, windowHeight, -1.0, 1.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // Player position
        float playerRectX = 100.0f;
        float playerRectY = 100.0f;

        // Rectangle size
        float rectWidth = 20.0f;
        float rectHeight = 120.0f;

        float topBounds = 720;
        float bottomBounds = 0;

        // Start the render loop
        while (!glfwWindowShouldClose(windowHandle)) {
            // Clear the screen to the clear color
            glClear(GL_COLOR_BUFFER_BIT);

            // Handle keyboard input
            float nextY = playerRectY + (isWKeyDown() ? 5.0f : (isSKeyDown() ? -5.0f : 0));

            // Update Player position
            playerRectY = Math.max(bottomBounds, Math.min(nextY, topBounds - rectHeight));

            // TODO
            streamClientPositionToServer();

            // Render the Border
            glColor3f(0.0f, 0.0f, 0.0f);
            Renderer2D.renderLine(windowWidth / 2, 0, windowWidth / 2, windowHeight, 2f);

            // Render the Player
            Renderer2D.renderRect(playerRectX, playerRectY, rectWidth, rectHeight);

            // TODO: Render the Score

            // Swap the front and back buffers
            glfwSwapBuffers(windowHandle);

            // Poll for window events
            glfwPollEvents();
        }
    }

    private boolean isWKeyDown() {
        return isKeyDown(GLFW_KEY_W);
    }

    private boolean isSKeyDown() {
        return isKeyDown(GLFW_KEY_S);
    }

    private boolean isKeyDown(int key) {
        return glfwGetKey(windowHandle, key) == GLFW.GLFW_PRESS;
    }

    private void streamClientPositionToServer() {}

    public static void main(String[] args) {
        new Main().run();
    }
}