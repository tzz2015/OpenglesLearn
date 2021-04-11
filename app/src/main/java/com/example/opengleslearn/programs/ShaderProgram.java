/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.opengleslearn.programs;

import android.content.Context;

import com.example.opengleslearn.util.ShaderHelper;
import com.example.opengleslearn.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;


abstract class ShaderProgram {
    // Uniform constants
    static final String U_MATRIX = "u_Matrix";
    static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    static final String A_POSITION = "a_Position";
    static final String A_COLOR = "a_Color";
    static final String U_COLOR = "u_Color";
    static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // 粒子构建需要
    static final String U_TIME = "u_Time";
    static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

    // Shader program
    final int program;
    ShaderProgram(Context context, int vertexShaderResourceId,
                  int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(
            TextResourceReader.readTextFileFromResource(
                context, vertexShaderResourceId),
            TextResourceReader.readTextFileFromResource(
                context, fragmentShaderResourceId));
    }        

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }
}
