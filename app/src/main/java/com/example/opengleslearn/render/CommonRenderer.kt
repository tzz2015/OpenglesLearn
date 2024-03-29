package com.example.opengleslearn.render

import android.opengl.GLES10
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.opengleslearn.data.AnimationShapeType
import javax.microedition.khronos.opengles.GL10

abstract class CommonRenderer : GLSurfaceView.Renderer {
    protected var mProgram: Int = 0
    protected var mViewWidth: Int = 0
    protected var mViewHeight: Int = 0

    /**
     * 顶点矩阵
     */
    open fun setMvpMatrix(matrix: FloatArray) {

    }

    /**
     * 纹理矩阵
     */
    open fun setStMatrix(matrix: FloatArray) {

    }


    /**
     * 透明度
     */
    open fun setAlpha(alpha: Float) {

    }

    /**
     * 设置脚本类型
     */
    open fun setShapeType(type: Int) {

    }

    open fun getShapeType(): Int {
        return AnimationShapeType.DEFEAT
    }

    open fun setProgress(progress:Float){

    }

    open fun setBlurSize(blurSize:Float){

    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES10.glViewport(0, 0, width, height)
        mViewWidth = width
        mViewHeight = height
    }

    open fun onDestroy() {
        if (mProgram > 0) {
            GLES20.glDeleteProgram(mProgram)
        }
    }
}