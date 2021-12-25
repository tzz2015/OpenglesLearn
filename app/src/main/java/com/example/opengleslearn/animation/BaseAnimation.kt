package com.example.opengleslearn.animation

abstract class BaseAnimation : IAnimation {

    override fun setAlpha(alpha: Float) {

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