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
    private float zPos = 0.0f;
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
                xPos - width,  yPos + height, zPos,  r, g, b,
                xPos - width, yPos - height, zPos,  r, g, b,
                xPos + width, yPos - height, zPos,  r, g, b,

                xPos - width,  yPos + height, zPos,  r, g, b,
                xPos + width, yPos - height, zPos,  r, g, b,
                xPos + width,  yPos + height, zPos,  r, g, b,
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
        if (player.equals("left")) movePlayer(Configuration.getInstance().getPlayerAKeys(), window);
        else movePlayer(Configuration.getInstance().getPlayerBKeys(), window);
    }

    public void movePlayer(int[] keys, long window) {
        float SPEED = Configuration.getInstance().getSPEED();
        float MAX_WINDOW = Configuration.getInstance().getMAX_WINDOW();

        if (glfwGetKey(window, keys[0]) == GLFW_PRESS) {
            yPos = Math.min(yPos + SPEED, MAX_WINDOW - height);
        }
        if (glfwGetKey(window, keys[1]) == GLFW_PRESS) {
            yPos = Math.max(yPos - SPEED, -MAX_WINDOW + height);
        }
        if (glfwGetKey(window, keys[2]) == GLFW_PRESS) {
            xPos = Math.max(xPos - SPEED, -MAX_WINDOW + width);
        }
        if (glfwGetKey(window, keys[3]) == GLFW_PRESS) {
            xPos = Math.min(xPos + SPEED, MAX_WINDOW - width);
        }
    }

    public void render(Shader shaderInstance) {
        float[] vertices = {
                xPos - width,  yPos + height, zPos,  r, g, b,
                xPos - width, yPos - height, zPos,  r, g, b,
                xPos + width, yPos - height, zPos,  r, g, b,

                xPos - width,  yPos + height, zPos,  r, g, b,
                xPos + width, yPos - height, zPos,  r, g, b,
                xPos + width,  yPos + height, zPos,  r, g, b,
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
