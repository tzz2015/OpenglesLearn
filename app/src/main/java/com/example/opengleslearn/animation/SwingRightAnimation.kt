package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.data.SwingPointData

class SwingRightAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.3f,592f))
        mPointList.add(SwingPointData(0.5f,333f))
        mPointList.add(SwingPointData(0.6f,430f))
        mPointList.add(SwingPointData(0.7f,390f))
        mPointList.add(SwingPointData(0.8f,402f))
        mPointList.add(SwingPointData(0.9f,398f))
        mPointList.add(SwingPointData(1.0f,400f))
    }

    override fun getStartPoint(): PointF {
        return PointF(-120f, 400f)
    }

    override fun getStartRotateAngle(): Float {
        return 10f
    }
}