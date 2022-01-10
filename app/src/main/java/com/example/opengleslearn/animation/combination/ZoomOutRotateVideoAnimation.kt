package com.example.opengleslearn.animation.combination

import com.example.opengleslearn.animation.base.BaseComVideoAnimation
import com.example.opengleslearn.data.AnimationSpeedType
import com.example.opengleslearn.data.BaseAnimationInputData

/**
 * @description: 缩小旋转
 * @author:  刘宇飞
 * @date :   2022/1/5 11:52
 */
class ZoomOutRotateVideoAnimation : BaseComVideoAnimation() {

    override fun initComList() {
    }

    override fun initTransitionList() {
    }

    override fun initRotateList() {
        mRotateList.add(
            BaseAnimationInputData(
                35f / 75f,
                60f / 75f,
                startRotate = 0f,
                endRotate = -4.5f,
                speedType = AnimationSpeedType.EASE_IN_QUART
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                60f / 75f,
                1f,
                startRotate = -4.5f,
                endRotate = -90f,
                speedType = AnimationSpeedType.EASE_IN_QUART
            )
        )

    }

    override fun initScaleList() {
        mScaleList.add(
            BaseAnimationInputData(
                0f,
                25f / 75f,
                startScale = 1.69f,
                endScale = 1.016f,
                speedType = AnimationSpeedType.EASE_OUT_QUINT
            )
        )
        mScaleList.add(
            BaseAnimationInputData(
                25f / 75f,
                35f / 75f,
                startScale = 1.016f,
                endScale = 1.0f
            )
        )
    }

    override fun initBlurList() {
        mBlurList.add(
            BaseAnimationInputData(
                0f,
                6f / 75f,
                startBlur = -3.0,
                endBlur = 0.0,
                speedType = AnimationSpeedType.EASE_IN_CUBIC
            )
        )
    }
}