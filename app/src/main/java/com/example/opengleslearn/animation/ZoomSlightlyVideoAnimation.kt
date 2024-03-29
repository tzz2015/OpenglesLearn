package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseVideoAnimation

/**
 * @description: 轻微放大  先慢后快
 * @author:  刘宇飞
 * @date :   2021/12/27 14:14
 */
class ZoomSlightlyVideoAnimation() : BaseVideoAnimation() {

    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        val progress = easeProgress(progress, false)
        Matrix.setIdentityM(mProjectMatrix, 0)
        val scale = if (mIsIntAnimation) -0.1f * (1.0f - progress) else 0.1f * progress
        Matrix.scaleM(mProjectMatrix, 0, 1.0f + scale, 1.0f + scale, 0f)
    }
}