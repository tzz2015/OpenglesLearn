package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseVideoAnimation
import com.example.opengleslearn.util.Constants
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description: Spin L向左转入 Spin R向右转入
 * @author:  刘宇飞
 * @date :   2021/12/27 9:38
 */
class RotateInVideoAnimation(private val isLeft: Boolean) : BaseVideoAnimation() {


    override fun setProgress(progress: Float) {
        val progress = easeProgress(progress, true)
        Matrix.setIdentityM(mProjectMatrix, 0)
        var rotate = 90f * (1.0f - progress)
        var moveX = -0.45f * Constants.MAX_HORIZONTAL_OFFSET * (1.0f - progress)
        if (!isLeft) {
            rotate = -rotate
            moveX = -moveX
        }
        Matrix3DUtils.preTranslateM(
            mProjectMatrix,
            moveX,
            0f,
            0f
        )
        val newCenter = FloatArray(2)
        Matrix3DUtils.mapPoint(mProjectMatrix, floatArrayOf(0f, 0f), newCenter)
        Matrix3DUtils.preTranslateM(mProjectMatrix, -newCenter[0], -newCenter[1], 0f)
        Matrix3DUtils.preRotateM(mProjectMatrix, rotate, 0f, 0f, 1f)
        Matrix3DUtils.preTranslateM(mProjectMatrix, newCenter[0], newCenter[1], 0f)

    }
}