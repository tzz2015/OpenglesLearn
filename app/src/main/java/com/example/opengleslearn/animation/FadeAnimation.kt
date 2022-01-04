package com.example.opengleslearn.animation

import com.example.opengleslearn.animation.base.BaseAnimation

/**
 * @description: 渐显和渐隐
 * @author:  刘宇飞
 * @date :   2021/12/27 13:51
 */
class FadeAnimation() : BaseAnimation() {

    constructor(isInt: Boolean) : this() {
        mIsIntAnimation = isInt
    }

    override fun setProgress(progress: Float) {
        mAlpha = if (mIsIntAnimation) progress else 1.0f - progress
    }
}