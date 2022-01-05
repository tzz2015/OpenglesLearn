package com.example.opengleslearn.animation.base

import android.opengl.Matrix
import com.example.opengleslearn.data.IAnimation

abstract class BaseAnimation() : IAnimation {
    val mProjectMatrix = FloatArray(16)
    val mModelMatrix = FloatArray(16)
    var mAlpha: Float = 1.0f
    var mBlurSize: Float = 0f
    var mIsIntAnimation: Boolean = false
    val TAG = javaClass.name


    init {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.setIdentityM(mModelMatrix, 0)
    }

    override fun setAlpha(alpha: Float) {
        mAlpha = alpha
    }

    /**
     * 重置
     */
    fun reset() {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.setIdentityM(mModelMatrix, 0)
        mAlpha = 1f
        mBlurSize = 0f
    }


    /**
     * 先快后慢或者先慢后快
     */
    override fun easeProgress(progress: Float, speedUp: Boolean): Float {
        var tempProgress = progress
        return if (speedUp) {
            tempProgress * tempProgress * tempProgress * tempProgress
        } else {
            tempProgress--
            1.0f - tempProgress * tempProgress * tempProgress * tempProgress
        }
    }
}