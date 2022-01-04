package com.example.opengleslearn.animation.base

import com.example.opengleslearn.animation.base.BaseAnimation
import com.example.opengleslearn.data.BaseAnimationInputData
import com.example.opengleslearn.data.SwingPointData

/**
 * @description: 组合动画
 * @author:  刘宇飞
 * @date :   2022/1/4 11:36
 */
class BaseComAnimation : BaseAnimation() {
    // 组合动画
    protected val mComList: MutableList<BaseAnimationInputData> = ArrayList()
    // 平移
    protected val mTransitionList: MutableList<BaseAnimationInputData> = ArrayList()
    // 旋转
    protected val mRotateList: MutableList<BaseAnimationInputData> = ArrayList()
    // 缩放
    protected val mScaleList: MutableList<BaseAnimationInputData> = ArrayList()




    override fun setProgress(progress: Float) {

    }
}