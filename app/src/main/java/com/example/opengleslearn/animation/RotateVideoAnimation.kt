package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseVideoAnimation

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class RotateVideoAnimation() : BaseVideoAnimation() {
    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.rotateM(mProjectMatrix, 0, -360 * 2f * progress, 0f, 0f, 1f)
        if (mIsIntAnimation) {
            Matrix.scaleM(mProjectMatrix, 0, progress, progress, 0f)
        } else {
            Matrix.scaleM(mProjectMatrix, 0, 1.0f - progress, 1.0f - progress, 0f)
        }
    }
}