package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.data.SwingPointData

class SwingLeftUpAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f,193f,0f))
        mPointList.add(SwingPointData(0.3f,472f,0f))
        mPointList.add(SwingPointData(0.46f,375f,0f))
        mPointList.add(SwingPointData(0.6f,409f,0f))
        mPointList.add(SwingPointData(0.76f,396f,0f))
        mPointList.add(SwingPointData(0.9f,401f,0f))
        mPointList.add(SwingPointData(1.0f,400f,0f))
    }

    override fun getStartPoint(): PointF {
        return PointF(975f, -86f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(0.5f, 0.2f)
    }

    override fun getStartRotateAngle(): Float {
        return -20f
    }

    override fun isHorizontal(): Boolean {
        return true
    }
}