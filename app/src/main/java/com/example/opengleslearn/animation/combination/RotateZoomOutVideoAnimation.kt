package com.example.opengleslearn.animation.combination

import com.example.opengleslearn.animation.base.BaseComVideoAnimation
import com.example.opengleslearn.data.AnimationSpeedType
import com.example.opengleslearn.data.BaseAnimationInputData

/**
 * @description: 旋转缩小
 * @author:  刘宇飞
 * @date :   2022/1/5 11:52
 */
class RotateZoomOutVideoAnimation : BaseComVideoAnimation() {

    override fun initComList() {
    }

    override fun initTransitionList() {
    }

    override fun initRotateList() {
        mRotateList.add(
            BaseAnimationInputData(
                0f, 20f / 75f, startRotate = -90f, endRotate = -2f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                20f / 75f,
                35f / 75f,
                startRotate = -2f,
                endRotate = 0f
            )
        )

    }

    override fun initScaleList() {
        mScaleList.add(
            BaseAnimationInputData(
                50f / 75f,
                1f,
                startScale = 1f,
                endScale = 0.46f,
                speedType = AnimationSpeedType.EASE_IN_QUART
            )
        )
    }

    override fun initBlurList() {
        mBlurList.add(
            BaseAnimationInputData(
                68f / 75f,
                1f,
                startBlur = 0.0,
                endBlur = -4.0,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
    }
}