package org.Luka;

import static org.lwjgl.glfw.GLFW.*;

public class Configuration {
    private static Configuration instance;

    private Configuration() {

    }

    public static Configuration getInstance() {

        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }


    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final float MAX_WINDOW = 1.0f;
    private final float SPEED = 0.01f;

    private final int[] playerAKeys = {GLFW_KEY_W, GLFW_KEY_S,  GLFW_KEY_A, GLFW_KEY_D};
    private final int[] playerBKeys = {GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT};


    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public float getMAX_WINDOW() {
        return MAX_WINDOW;
    }

    public float getSPEED() {
        return SPEED;
    }

    public int[] getPlayerAKeys() {
        return playerAKeys;
    }

    public int[] getPlayerBKeys() {
        return playerBKeys;
    }

}
