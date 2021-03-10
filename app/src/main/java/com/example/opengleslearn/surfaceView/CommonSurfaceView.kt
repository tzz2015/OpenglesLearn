package com.example.opengleslearn.surfaceView

import android.content.Context
import android.opengl.GLSurfaceView

/**
　　* @Description:
　　* @author 刘宇飞
　　* @date  2021/2/28 13:18
　　*/
class CommonSurfaceView(context: Context, renderer: Renderer) : GLSurfaceView(context) {
    private val mRender: Renderer = renderer
    private val mContext: Context = context

    init {
        setEGLContextClientVersion(2)
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0)
        setRenderer(mRender)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

}