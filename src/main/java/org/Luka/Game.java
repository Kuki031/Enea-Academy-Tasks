package org.Luka;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;


public class Game {

    private static Game instance;
    private Game() { }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private long window;

    private Rectangle rect1;
    private Rectangle rect2;
    private Counter counter;

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(Configuration.getInstance().getWIDTH(), Configuration.getInstance().getHEIGHT(), "Java Game", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        rect1 = new Rectangle(-0.5f, 0.0f, 0.10f, 0.15f);
        rect2 = new Rectangle(0.5f, 0.0f, 0.10f, 0.15f);
        counter = new Counter(0, "Number of collisions");
    }


    private void loop() {
        Shader shaderProgram = Shader.getInstance();
        boolean wasColliding = false;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            rect1.render(shaderProgram);
            rect2.render(shaderProgram);

            rect1.handleKeyboardInput(window, "left");
            rect2.handleKeyboardInput(window, "right");

            boolean isColliding = rect1.isCollidingWith(rect2);
            if (isColliding) {
                glClearColor(0.9f, 0.0f, 0.0f, 0.0f);
                
                if (!wasColliding) {
                    counter.increment();
                    System.out.println(counter.getLabel() + ": " + counter.getCount());
                    wasColliding = true;
                }
            } else {
                glClearColor(0.0f, 0.5f, 1.0f, 1.0f);
                wasColliding = false;
            }


            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        Game.getInstance().run();
    }
}
