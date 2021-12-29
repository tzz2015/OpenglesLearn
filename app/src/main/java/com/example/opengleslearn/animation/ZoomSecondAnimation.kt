package com.example.opengleslearn.animation

import android.opengl.Matrix

/**
 * @description: Zoom In2放大I 先快后慢
 * @author:  刘宇飞
 * @date :   2021/12/27 14:14
 */
class ZoomSecondAnimation() : BaseAnimation() {

    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        val progress = easeProgress(progress, false)
        Matrix.setIdentityM(mProjectMatrix, 0)

        mAlpha = if (mIsIntAnimation) {
            if (progress <= 0.2) progress * 5f else 1.0f
        } else {
            if (progress >= 0.8f) 1.0f - 0.6f * (progress - 0.8f) * 5f else 1.0f
        }

        val scale = if (mIsIntAnimation) 0.7f + 0.3f * progress else 1.0f + 0.3f * progress
        Matrix.scaleM(mProjectMatrix, 0, scale, scale, 0f)
    }
}