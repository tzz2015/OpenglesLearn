package com.example.opengleslearn.animation

import android.graphics.PointF
import com.example.opengleslearn.data.SwingPointData

/**
 * @description:Swing D向下甩入
 * @author:  刘宇飞
 * @date :   2021/12/30 15:06
 */
class SwingDownAnimation : BaseSwingAnimation() {
    override fun initPointList() {
        mPointList.add(SwingPointData(0.16f, 0f, 252.8f))
        mPointList.add(SwingPointData(0.3f, 0f, 472.8f))
        mPointList.add(SwingPointData(0.46f, 0f, 381.8f))
        mPointList.add(SwingPointData(0.6f, 0f, 405.8f))
        mPointList.add(SwingPointData(0.76f, 0f, 389.3f))
        mPointList.add(SwingPointData(0.9f, 0f, 401f))
        mPointList.add(SwingPointData(1.0f, 0f, 400f))
    }

    override fun setProgress(progress: Float) {
        super.setProgress(progress)
        // 模糊
        mBlurSize =
            -1.5f * (1.0f - mProgressData.progress) * mProgressData.timeWeight
    }

    override fun getStartPoint(): PointF {
        return PointF(436.8f, 796.8f)
    }

    override fun getRotatePoint(): PointF {
        return PointF(0.5f, 0.5f)
    }

    override fun getStartRotateAngle(): Float {
        return 7f
    }

    override fun isHorizontal(): Boolean {
        return false
    }
}