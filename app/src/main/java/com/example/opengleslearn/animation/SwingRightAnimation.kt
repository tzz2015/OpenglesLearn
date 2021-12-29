package com.example.opengleslearn.animation

import android.graphics.PointF

class SwingRightAnimation : BaseSwingAnimation() {
    override fun initPointList() {

    }

    override fun getStartPoint(): PointF {
        return PointF(400f, 400f)
    }

    override fun getStartAngle(): Float {
        return 10f
    }
}