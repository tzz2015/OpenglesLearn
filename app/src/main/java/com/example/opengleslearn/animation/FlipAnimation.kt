package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseAnimation

/**
 * @description:Flip镜像翻转
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class FlipAnimation() : BaseAnimation() {
    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        val flip = if (mIsIntAnimation) progress else 1.0f - 0.92f * progress
        Matrix.scaleM(mProjectMatrix, 0, flip, 1.0f, 0f)
    }
}