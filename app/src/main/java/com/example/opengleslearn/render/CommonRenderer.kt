package com.example.opengleslearn.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView

abstract class CommonRenderer : GLSurfaceView.Renderer {
    protected var mProgram: Int = 0

    open fun onDestroy() {
        if (mProgram > 0) {
            GLES20.glDeleteProgram(mProgram)
        }
    }
}