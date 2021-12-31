package com.example.opengleslearn.animation.combination

import android.opengl.Matrix
import com.example.opengleslearn.animation.BaseAnimation

/**
 * @description: 左晃晃 右晃晃
 * @author:  刘宇飞
 * @date :   2021/12/31 11:06
 */
class ShakeAnimation(private val isLeft: Boolean) : BaseAnimation() {
    private var mCurrRotate: Float = 0f
    private var mCurrTransition: Float = 0f

    companion object {
        // 最大旋转角度
        const val MAX_ROTATE = 30f
        const val MAX_TRANSITION = 0.2f
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mModelMatrix, 0)
        // 根据进度旋转
        rotateByProgress(progress)
        // 根据进度进行平移操作
        transitionByProgress(progress)
    }


    private fun rotateByProgress(progress: Float) {

    }

    private fun transitionByProgress(progress: Float) {

    }


}