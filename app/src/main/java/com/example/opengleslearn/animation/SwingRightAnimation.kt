package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.data.SwingPointData

class SwingRightAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f,592f,0f))
        mPointList.add(SwingPointData(0.3f,333f,0f))
        mPointList.add(SwingPointData(0.46f,421f,0f))
        mPointList.add(SwingPointData(0.6f,391f,0f))
        mPointList.add(SwingPointData(0.76f,402f,0f))
        mPointList.add(SwingPointData(0.9f,398f,0f))
        mPointList.add(SwingPointData(1.0f,400f,0f))
    }

    override fun getStartPoint(): PointF {
        return PointF(-120f, 400f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(0.5f, 0f)
    }

    override fun getStartRotateAngle(): Float {
        return 10f
    }

    override fun isHorizontal(): Boolean {
        return true
    }
}