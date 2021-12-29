package com.example.opengleslearn.animation

import android.opengl.Matrix
import android.util.Log
import com.example.opengleslearn.util.AnimationInterpolator
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description: Zoom Out缩小
 * @author:  刘宇飞
 * @date :   2021/12/27 14:14
 */
class ZoomOutAnimation() : BaseAnimation() {

    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        mAlpha = if (mIsIntAnimation) {
            0.5f + 0.5f * AnimationInterpolator.easeOutQuart(progress)
        } else {
            1.0f - 0.5f * AnimationInterpolator.easeInQuart(progress)
        }
        val scale = if (mIsIntAnimation) 1.3f - 0.3f * progress else 1.0f - 0.4f * progress
        Matrix3DUtils.preScaleM(mProjectMatrix, scale, scale, 0f)
    }
}