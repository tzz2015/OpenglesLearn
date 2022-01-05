package com.example.opengleslearn.animation.combination

import android.graphics.PointF
import com.example.opengleslearn.animation.base.BaseComAnimation
import com.example.opengleslearn.data.AnimationSpeedType
import com.example.opengleslearn.data.BaseAnimationInputData

/**
 * @description: 旋转降落
 * @author:  刘宇飞
 * @date :   2022/1/5 10:30
 */
class SpinLandingAnimation : BaseComAnimation() {

    override fun initComList() {
    }

    override fun initTransitionList() {
        mTransitionList.add(
            BaseAnimationInputData(
                30f / 75f,
                65f / 75f,
                startTransitionY = 400f,
                endTransitionY = 363.6f
            )
        )
        // 先慢后快
        mTransitionList.add(
            BaseAnimationInputData(
                65f / 75f,
                1f,
                startTransitionY = 363.6f,
                endTransitionY = 15f,
                speedType = AnimationSpeedType.EASE_IN_CUBIC
            )
        )

    }

    override fun initRotateList() {
        // 先快后慢
        mRotateList.add(
            BaseAnimationInputData(
                0f,
                15f / 75f,
                startRotate = 90f,
                endRotate = 5.5f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                15f / 75f,
                40f / 75f,
                startRotate = 5.5f,
                endRotate = 0f
            )
        )

    }

    override fun initScaleList() {
    }

    override fun initBlurList() {
        mBlurList.add(
            BaseAnimationInputData(
                65f / 75f,
                1f,
                startBlur = 0.0, endBlur = -4.0
            )
        )
    }


}