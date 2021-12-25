package com.example.opengleslearn.animation

import android.opengl.Matrix

class MoveAnimation(private val type: Int) : BaseAnimation() {

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        var translateX = 0.0f
        var translateY = 0.0f
        when (type) {
            MoveAnimationType.LEFT -> translateX = -1f + progress
            MoveAnimationType.RIGHT -> translateX = 1f - progress
            MoveAnimationType.TOP -> translateY = 1f - progress
            MoveAnimationType.BOTTOM -> translateY = -1f + progress
        }
        Matrix.translateM(mProjectMatrix, 0, translateX * 2f, translateY * 2f, 0f)
    }
}