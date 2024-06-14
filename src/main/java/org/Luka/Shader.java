package org.Luka;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class Shader {

    private static Shader instance;
    private static int shaderProgram;

    private Shader() {
        createShaderProgram();
    }

    public static Shader getInstance() {
        if (instance == null) {
            instance = new Shader();
        }
        return instance;
    }

    private static void createShaderProgram() {
        String vertexShaderSource = "#version 330 core\n" +
                "layout(location = 0) in vec3 position;\n" +
                "layout(location = 1) in vec3 color;\n" +
                "out vec3 vertexColor;\n" +
                "void main() {\n" +
                "    gl_Position = vec4(position, 1.0);\n" +
                "    vertexColor = color;\n" +
                "}\n";
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        checkCompileErrors(vertexShader, "VERTEX");

        String fragmentShaderSource = "#version 330 core\n" +
                "in vec3 vertexColor;\n" +
                "out vec4 fragColor;\n" +
                "void main() {\n" +
                "    fragColor = vec4(vertexColor, 1.0);\n" +
                "}\n";
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        checkCompileErrors(fragmentShader, "FRAGMENT");

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkCompileErrors(shaderProgram, "PROGRAM");

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private static void checkCompileErrors(int shader, String type) {
        int success;
        if (type.equals("PROGRAM")) {
            success = glGetProgrami(shader, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetProgramInfoLog(shader);
                System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
            }
        } else {
            success = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetShaderInfoLog(shader);
                System.err.println("ERROR::SHADER::" + type + "::COMPILATION_FAILED\n" + infoLog);
            }
        }
    }

    public int getShaderProgram() {
        return shaderProgram;
    }
}

