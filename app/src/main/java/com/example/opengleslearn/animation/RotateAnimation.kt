package com.example.opengleslearn.animation

import android.opengl.Matrix

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class RotateAnimation : BaseAnimation() {
    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.rotateM(mProjectMatrix, 0, -360 * 2f * progress, 0f, 0f, 1f)
        Matrix.scaleM(mProjectMatrix, 0, progress, progress, 0f)
    }
}