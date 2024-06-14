package org.Luka;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;

public class Rectangle {

    private float xPos;
    private float yPos;
    private float width;
    private float height;
    private float r;
    private float g;
    private float b;
    private int vao;
    private int vbo;

    public Rectangle(float xPos, float yPos, float width, float height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.r = (float) Math.random() * 1;
        this.g = (float) Math.random() * 1;
        this.b = (float) Math.random() * 1;
        initializeBuffers();
    }

    private void initializeBuffers() {
        float[] vertices = {
                xPos - width,  yPos + height, 0.0f,  r, g, b,
                xPos - width, yPos - height, 0.0f,  r, g, b,
                xPos + width, yPos - height, 0.0f,  r, g, b,

                xPos - width,  yPos + height, 0.0f,  r, g, b,
                xPos + width, yPos - height, 0.0f,  r, g, b,
                xPos + width,  yPos + height, 0.0f,  r, g, b,
        };

        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void handleKeyboardInput(long window, String player) {
        if (player.equals("left")) {
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
                yPos = Math.min(yPos + 0.01f, 1.0f - height); // Move up
            }
            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
                yPos = Math.max(yPos - 0.01f, -1.0f + height); // Move down
            }
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                xPos = Math.max(xPos - 0.01f, -1.0f + width); // Move left
            }
            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                xPos = Math.min(xPos + 0.01f, 1.0f - width); // Move right
            }
        }
        if (player.equals("right")) {
            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
                yPos = Math.min(yPos + 0.01f, 1.0f - height); // Move up
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
                yPos = Math.max(yPos - 0.01f, -1.0f + height); // Move down
            }
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
                xPos = Math.max(xPos - 0.01f, -1.0f + width); // Move left
            }
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
                xPos = Math.min(xPos + 0.01f, 1.0f - width); // Move right
            }
        }
    }

    public void render(Shader shaderInstance) {
        float[] vertices = {
                xPos - width,  yPos + height, 0.0f,  r, g, b,
                xPos - width, yPos - height, 0.0f,  r, g, b,
                xPos + width, yPos - height, 0.0f,  r, g, b,

                xPos - width,  yPos + height, 0.0f,  r, g, b,
                xPos + width, yPos - height, 0.0f,  r, g, b,
                xPos + width,  yPos + height, 0.0f,  r, g, b,
        };

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        glUseProgram(shaderInstance.getShaderProgram());
        glBindVertexArray(vao);

        glDrawArrays(GL_TRIANGLES, 0, 6);

        glBindVertexArray(0);
    }

    public boolean isCollidingWith(Rectangle other) {
        return xPos - width < other.xPos + other.width &&
                xPos + width > other.xPos - other.width &&
                yPos - height < other.yPos + other.height &&
                yPos + height > other.yPos - other.height;
    }
}
