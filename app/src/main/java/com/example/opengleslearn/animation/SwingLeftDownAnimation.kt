package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.animation.base.BaseSwingAnimation
import com.example.opengleslearn.data.SwingPointData

class SwingLeftDownAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f,222.6f,0f))
        mPointList.add(SwingPointData(0.3f,461.1f,0f))
        mPointList.add(SwingPointData(0.46f,379.1f,0f))
        mPointList.add(SwingPointData(0.6f,407.5f,0f))
        mPointList.add(SwingPointData(0.76f,398f,0f))
        mPointList.add(SwingPointData(0.9f,401f,0f))
        mPointList.add(SwingPointData(1.0f,400f,0f))
    }

    override fun getStartPoint(): PointF {
        return PointF(888.3f, 891f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(0.5f, 0.5f)
    }

    override fun getStartRotateAngle(): Float {
        return -20f
    }

    override fun isHorizontal(): Boolean {
        return true
    }
}