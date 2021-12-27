package com.example.opengleslearn.animation

import android.opengl.Matrix

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class CenterOpenAnimation : BaseAnimation() {
    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.scaleM(mProjectMatrix, 0, progress, 1.0f, 0f)
    }
}