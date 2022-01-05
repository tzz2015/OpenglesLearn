package com.example.opengleslearn.animation.combination

import android.graphics.PointF
import com.example.opengleslearn.animation.base.BaseComAnimation
import com.example.opengleslearn.data.AnimationSpeedType
import com.example.opengleslearn.data.BaseAnimationInputData

/**
 * @description: 降落旋转
 * @author:  刘宇飞
 * @date :   2022/1/5 10:30
 */
class LandingSpinAnimation : BaseComAnimation() {

    override fun initComList() {
    }

    override fun initTransitionList() {
        // 先快后慢
        mTransitionList.add(
            BaseAnimationInputData(
                0f,
                15f / 75f,
                startTransitionY = 800f,
                endTransitionY = 424.6f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                15f / 75f,
                40f / 75f,
                startTransitionY = 424.6f,
                endTransitionY = 400f
            )
        )

    }

    override fun initRotateList() {
        // 先快后慢
        mRotateList.add(
            BaseAnimationInputData(
                30f / 75f,
                55f / 75f,
                startRotate = 0f,
                endRotate = 2.5f
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                55f / 75f,
                1f,
                startRotate = 2.5f,
                endRotate = 90f,
                speedType = AnimationSpeedType.EASE_IN_QUAD
            )
        )

    }

    override fun initScaleList() {
    }

    override fun initBlurList() {
        mBlurList.add(
            BaseAnimationInputData(
                0f,
                25f / 75f,
                startBlur =  -4.0, endBlur = 0.0
            )
        )
    }


}