package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.util.AnimationInterpolator
import com.example.opengleslearn.util.Constants
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description: Spin U向上转出
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class SpinOutAnimation : BaseAnimation() {


    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mModelMatrix, 0)
        mBlurSize = -3f * progress
        val rotate = 80f * progress
        val newCenter = FloatArray(2)
        newCenter[1] = 0.5f
        Matrix3DUtils.preTranslateM(mModelMatrix, -newCenter[0], -newCenter[1], 0f)
        Matrix3DUtils.preRotateM(mModelMatrix, rotate, 0f, 0f, 1f)
        Matrix3DUtils.preTranslateM(mModelMatrix, newCenter[0], newCenter[1], 0f)
    }
}