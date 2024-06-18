package org.Luka;

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

    private final int[] playerAKeys = {87, 83, 65, 68};
    private final int[] playerBKeys = {265, 264, 263, 262};


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
