package com.example.opengleslearn.animation.combination

import com.example.opengleslearn.animation.base.BaseComVideoAnimation
import com.example.opengleslearn.data.AnimationSpeedType
import com.example.opengleslearn.data.BaseAnimationInputData

/**
 * @description: 左晃晃 右晃晃
 * @author:  刘宇飞
 * @date :   2021/12/31 11:06
 */
class ShakeVideoAnimation() : BaseComVideoAnimation() {
    private var isLeft: Boolean = false

    constructor(isLeft: Boolean) : this() {
        this.isLeft = isLeft
        initRotateList()
    }

    override fun initComList() {
    }

    private fun getTransitionY(y: Float): Float {
        return 800f - 400f / 1480f * y
    }

    override fun initTransitionList() {
        // 中心点在 1480,1480
        mTransitionList.add(
            BaseAnimationInputData(
                0f,
                10f / 75f,
                startTransitionY = getTransitionY(1480f),
                endTransitionY = getTransitionY(1216f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                10f / 75f,
                20f / 75f,
                startTransitionY = getTransitionY(1216f),
                endTransitionY = getTransitionY(1590f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                20f / 75f,
                25f / 75f,
                startTransitionY = getTransitionY(1590f),
                endTransitionY = getTransitionY(1650f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                25f / 75f,
                40f / 75f,
                startTransitionY = getTransitionY(1650f),
                endTransitionY = getTransitionY(1370f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                40f / 75f,
                50f / 75f,
                startTransitionY = getTransitionY(1370f),
                endTransitionY = getTransitionY(1459f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                50f / 75f,
                60f / 75f,
                startTransitionY = getTransitionY(1459f),
                endTransitionY = getTransitionY(1567f)
            )
        )
        mTransitionList.add(
            BaseAnimationInputData(
                60 / 75f,
                75f / 75f,
                startTransitionY = getTransitionY(1567f),
                endTransitionY = getTransitionY(1450f)
            )
        )
    }

    override fun initRotateList() {
        mRotateList.clear()
        // 先快后慢
        mRotateList.add(
            BaseAnimationInputData(
                0f,
                10f / 75f,
                startRotate = 0f,
                endRotate = if (isLeft) -4.5f else 4.5f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        // 先慢后快
        mRotateList.add(
            BaseAnimationInputData(
                10f / 75f,
                20f / 75f,
                startRotate = if (isLeft) -4.5f else 4.5f,
                endRotate = if (isLeft) -9f else 9f,
                speedType = AnimationSpeedType.EASE_IN_CUBIC
            )
        )

        mRotateList.add(
            BaseAnimationInputData(
                20f / 75f,
                35f / 75f,
                startRotate = if (isLeft) -9f else 9f,
                endRotate = if (isLeft) -1f else 1f,
                speedType = AnimationSpeedType.EASE_IN_CUBIC
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                35f / 75f,
                50f / 75f,
                startRotate = if (isLeft) -1f else 1f,
                endRotate = if (isLeft) 7f else -7f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )

        mRotateList.add(
            BaseAnimationInputData(
                50f / 75f,
                62.5f / 75f,
                startRotate = if (isLeft) 7f else -7f,
                endRotate = if (isLeft) 2f else -2f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mRotateList.add(
            BaseAnimationInputData(
                62.5f / 75f,
                1f,
                startRotate = if (isLeft) 2f else -2f,
                endRotate = if (isLeft) -3f else 3f,
                speedType = AnimationSpeedType.EASE_IN_CUBIC
            )
        )

    }

    override fun initScaleList() {
        mScaleList.add(
            BaseAnimationInputData(
                0f, 20f / 75f, startScale = 1.23f, endScale = 1.04f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mScaleList.add(
            BaseAnimationInputData(
                20f / 75f,
                30f / 75f,
                startScale = 1.04f,
                endScale = 1.0f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mScaleList.add(
            BaseAnimationInputData(
                30f / 75f,
                40f / 75f,
                startScale = 1.0f,
                endScale = 1.0f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mScaleList.add(
            BaseAnimationInputData(
                40f / 75f,
                60f / 75f,
                startScale = 1.0f,
                endScale = 1.04f,
                speedType = AnimationSpeedType.EASE_OUT_CUBIC
            )
        )
        mScaleList.add(BaseAnimationInputData(60f / 75f, 1f, startScale = 1.04f, endScale = 1.15f))

    }

    override fun initBlurList() {
        mBlurList.add(BaseAnimationInputData(0f, 20f / 75f, startBlur = -5.0, endBlur = 0.0))
        mBlurList.add(BaseAnimationInputData(60f / 75f, 1f, startBlur = 0.0, endBlur = -2.5))
    }




}