package com.example.opengleslearn.animation

import android.graphics.PointF
import android.util.Log
import com.example.opengleslearn.data.SwingPointData
import kotlin.math.atan2

abstract class BaseSwingAnimation : BaseAnimation() {

    private val mPointList: MutableList<SwingPointData> = ArrayList()

    companion object {
        const val WIDTH: Float = 800f
        const val HEIGHT: Float = 800f
    }

    init {
        initPoint()
    }

    private fun initPoint() {
        initPointList()
        val startPoint = getStartPoint()
        mPointList.add(0, SwingPointData(0f, startPoint.x))
        mPointList.add(mPointList.size, SwingPointData(1f, 400f))
        val range = atan2(startPoint.x.toDouble(), startPoint.y.toDouble())
        val toDegrees = Math.toDegrees(range)
        Log.e(TAG,"range:$range  --toDegrees:$toDegrees")
    }

    override fun setProgress(progress: Float) {

    }

    abstract fun initPointList()

    /**
     * 获取起始点
     */
    abstract fun getStartPoint(): PointF

    /**
     * 其实角度
     */
    abstract fun getStartAngle(): Float

}