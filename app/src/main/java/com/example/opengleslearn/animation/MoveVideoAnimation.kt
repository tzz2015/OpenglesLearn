package com.example.opengleslearn.animation

import android.opengl.Matrix
import com.example.opengleslearn.animation.base.BaseVideoAnimation
import com.example.opengleslearn.data.MoveAnimationType
import com.example.opengleslearn.util.Constants

class MoveVideoAnimation() : BaseVideoAnimation() {
    private var mType: Int = MoveAnimationType.LEFT

    constructor(isInt: Boolean, type: Int) : this() {
        mIsIntAnimation = isInt
        mType = type
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        var translateX = 0.0f
        var translateY = 0.0f
        val newProgress = if (mIsIntAnimation) progress else 1.0f - progress
        if (mIsIntAnimation) {
            when (mType) {
                MoveAnimationType.LEFT -> translateX = 1f - newProgress
                MoveAnimationType.RIGHT -> translateX = -1f + newProgress
                MoveAnimationType.TOP -> translateY = -1f + newProgress
                MoveAnimationType.BOTTOM -> translateY = 1f - newProgress
            }
        } else {
            when (mType) {
                MoveAnimationType.LEFT -> translateX = -1f + newProgress
                MoveAnimationType.RIGHT -> translateX = 1f - newProgress
                MoveAnimationType.TOP -> translateY = 1f - newProgress
                MoveAnimationType.BOTTOM -> translateY = -1f + newProgress
            }
        }
        translateX *= Constants.MAX_HORIZONTAL_OFFSET
        translateY *= Constants.MAX_VERTICAL_OFFSET
        Matrix.translateM(mProjectMatrix, 0, translateX * 2f, translateY * 2f, 0f)
    }
}