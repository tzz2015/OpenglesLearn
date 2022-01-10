package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.animation.base.BaseSwingVideoAnimation
import com.example.opengleslearn.data.SwingPointData

class SwingRightDownVideoAnimation : BaseSwingVideoAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f,579f,0f))
        mPointList.add(SwingPointData(0.3f,337.5f,0f))
        mPointList.add(SwingPointData(0.46f,420.5f,0f))
        mPointList.add(SwingPointData(0.6f,393f,0f))
        mPointList.add(SwingPointData(0.76f,402.5f,0f))
        mPointList.add(SwingPointData(0.9f,399f,0f))
        mPointList.add(SwingPointData(1.0f,400f,0f))
    }

    override fun getStartPoint(): PointF {
        return PointF(-88f, 888.8f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(0.5f, 0.5f)
    }

    override fun getStartRotateAngle(): Float {
        return 20f
    }

    override fun isHorizontal(): Boolean {
        return true
    }
}