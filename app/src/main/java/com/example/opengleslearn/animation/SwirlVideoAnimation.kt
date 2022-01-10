package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseVideoAnimation
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class SwirlVideoAnimation() : BaseVideoAnimation() {
    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mModelMatrix, 0)
        val rotate = if (mIsIntAnimation) 90f - 90 * progress else -90f * progress

        val newCenter = FloatArray(2)
        newCenter[0] = 0.5f
        newCenter[1] = 0.5f

        Matrix3DUtils.preTranslateM(mModelMatrix, -newCenter[0], -newCenter[1], 0f)
        Matrix3DUtils.preRotateM(mModelMatrix, rotate, 0f, 0f, 1f)
        Matrix3DUtils.preTranslateM(mModelMatrix, newCenter[0], newCenter[1], 0f)

    }
}