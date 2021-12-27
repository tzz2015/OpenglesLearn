package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class SwirlAnimation() : BaseAnimation() {
    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        val rotate = if (mIsIntAnimation) 90f - 90 * progress else 90f * progress

        Matrix.rotateM(mProjectMatrix, 0, rotate, 0f, 0f, 1f)


    }
}