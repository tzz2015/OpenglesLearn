package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.data.SwingPointData

class SwingRightUpAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f,578f,0f))
        mPointList.add(SwingPointData(0.3f,338f,0f))
        mPointList.add(SwingPointData(0.46f,422f,0f))
        mPointList.add(SwingPointData(0.6f,392f,0f))
        mPointList.add(SwingPointData(0.76f,403f,0f))
        mPointList.add(SwingPointData(0.9f,399f,0f))
        mPointList.add(SwingPointData(1.0f,400f,0f))
    }

    override fun getStartPoint(): PointF {
        return PointF(-82f, -98.8f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(1.0f, 0.0f)
    }

    override fun getStartRotateAngle(): Float {
        return 20f
    }

    override fun isHorizontal(): Boolean {
        return true
    }
}