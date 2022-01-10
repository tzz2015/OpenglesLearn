package com.example.opengleslearn.animation.base

import android.graphics.PointF
import android.opengl.Matrix
import android.util.Log
import com.example.opengleslearn.data.SwingPointData
import com.example.opengleslearn.data.SwingProgressData
import com.example.opengleslearn.util.Matrix3DUtils

abstract class BaseSwingVideoAnimation : BaseVideoAnimation() {

    protected val mPointList: MutableList<SwingPointData> = ArrayList()

    // 斜率
    private var mK: Float = 0f
    private var mB: Float = 0f
    private var mRotate: Float = 0f
    private var mRotatePoint = PointF()
    protected var mProgressData = SwingProgressData()

    companion object {
        const val WIDTH: Float = 800f
        const val HEIGHT: Float = 800f
    }

    init {
        initPoint()
    }

    private fun initPoint() {
        // 公式 y=kx+b
        initPointList()
        val startPoint = getStartPoint()
        // 起始点
        mPointList.add(0, SwingPointData(0f, startPoint.x, startPoint.y))
        // 计算公式值
        mK = (startPoint.y - HEIGHT / 2f) / (startPoint.x - WIDTH / 2f)
        mB = HEIGHT / 2f - mK * WIDTH / 2f
        mRotatePoint = getRotatePoint()
    }

    /**
     * 根据x坐标求Y
     */
    private fun getPointY(pointX: Float): Float {
        return mK * pointX + mB
    }

    /**
     * 根据y坐标获取x
     */
    private fun getPointX(pointY: Float): Float {
        return (pointY - mB) / mK
    }

    override fun setProgress(progress: Float) {
        Matrix.setIdentityM(mProjectMatrix, 0)
        Matrix.setIdentityM(mModelMatrix, 0)
        mProgressData = calculationProgressData(progress)

        // 移动
        movingByProgress(mProgressData)
        // 模糊
        mBlurSize =
            -2f * (1.0f - mProgressData.progress) * mProgressData.timeWeight
        // 旋转
        rotateByProgress(mProgressData)
    }

    private fun rotateByProgress(progressData: SwingProgressData) {
        Matrix3DUtils.preTranslateM(mModelMatrix, -mRotatePoint.x, -mRotatePoint.y, 0f)
        Matrix3DUtils.preRotateM(mModelMatrix, progressData.rotate, 0f, 0f, 1f)
        Matrix3DUtils.preTranslateM(mModelMatrix, mRotatePoint.x, mRotatePoint.y, 0f)
    }


    /**
     * 根据进度平移
     */
    private fun movingByProgress(progressData: SwingProgressData) {
        Matrix3DUtils.preTranslateM(
            mModelMatrix,
            progressData.vertexX,
            progressData.vertexY
            , 0f
        )
    }

    /**
     * 根据进度计算想过值
     */
    private fun calculationProgressData(progress: Float): SwingProgressData {
        val progressData = SwingProgressData()
        // 需要移动到的点
        var currPointData = SwingPointData(
            0f,
            mPointList[mPointList.size - 1].xPoint,
            mPointList[mPointList.size - 1].yPoint
        )
        // 上一个点
        var lastPointData: SwingPointData? = null
        if (progress == 0.0f) {
            currPointData = mPointList[0]
            lastPointData = null
            progressData.progress = 0f
            updateRotate()
        } else if (progress == 1.0f) {
            currPointData = mPointList[mPointList.size - 1]
            lastPointData = null
            progressData.progress = 1f
            updateRotate()
        } else {
            // 需要要移动到的目标点
            for ((index, value) in mPointList.withIndex()) {
                if (progress <= value.endProgress) {
                    currPointData = value
                    lastPointData = mPointList[index - 1]
                    updateRotate()
                    mRotate *= (1.0f + 0.5f * (index - 1))
                    break
                }
            }
            if (lastPointData == null) {
                currPointData = mPointList[mPointList.size - 1]
                progressData.progress = 1f
                progressData.timeWeight = 0f
            }
        }
        val moveToPoint =
            if (isHorizontal()) PointF(currPointData.xPoint, getPointY(currPointData.xPoint))
            else PointF(getPointX(currPointData.yPoint), currPointData.yPoint)
        if (progress == 1.0f) {
            moveToPoint.x = WIDTH / 2
            moveToPoint.y = HEIGHT / 2
        }
        // 不是起点和终点
        if (lastPointData != null) {
            val currProgress =
                (progress - lastPointData.endProgress) / (currPointData.endProgress - lastPointData.endProgress)
            if (isHorizontal()) {
                val xPoint =
                    lastPointData.xPoint - (lastPointData.xPoint - currPointData.xPoint) * currProgress
                val yPoint = getPointY(xPoint)
                moveToPoint.x = xPoint
                moveToPoint.y = yPoint
            } else {
                val yPoint =
                    lastPointData.yPoint - (lastPointData.yPoint - currPointData.yPoint) * currProgress
                val xPoint = getPointX(yPoint)
                moveToPoint.x = xPoint
                moveToPoint.y = yPoint
            }

            progressData.progress = currProgress
            progressData.timeWeight =
                1.0f - currPointData.endProgress / mPointList[mPointList.size - 1].endProgress
            /*Log.e(
                TAG,
                "progress:$progress currProgress:$currProgress  xPoint:$xPoint  yPoint:$yPoint"
            )*/
        }
        val windowXToVertexX = windowXToVertexX(moveToPoint.x)
        val windowYToVertexY = windowYToVertexY(moveToPoint.y)
        progressData.windowX = moveToPoint.x
        progressData.windowY = moveToPoint.y
        progressData.vertexX = windowXToVertexX
        progressData.vertexY = windowYToVertexY
        progressData.rotate =
            if (isHorizontal()) windowXToVertexX * mRotate else windowYToVertexY * mRotate


        Log.e(
            TAG,
            "progress:$progress rotate:${progressData.rotate} mRotate:$mRotate"
        )
        /* Log.e(
             TAG,
             "x:${moveToPoint.x} VertexX:$windowXToVertexX  y:${moveToPoint.y} VertexY:$windowYToVertexY rotate:${progressData.rotate}"
         )*/
        return progressData
    }

    private fun updateRotate() {
        mRotate = if (isHorizontal()) getStartRotateAngle() / windowXToVertexX(mPointList[0].xPoint)
        else getStartRotateAngle() / windowXToVertexX(mPointList[0].yPoint)

    }


    /**
     * The coordinates of the vertex from the left side of the window
     */
    private fun windowXToVertexX(x: Float): Float {

        return if (x >= 0) -(x - WIDTH / 2f) / WIDTH else (-x + WIDTH / 2f) / WIDTH
    }

    private fun windowYToVertexY(y: Float): Float {
        return if (y >= 0) (y - HEIGHT / 2f) / HEIGHT else -(-y + HEIGHT / 2f) / HEIGHT
    }


    abstract fun initPointList()

    /**
     * 获取起始点
     */
    abstract fun getStartPoint(): PointF

    abstract fun getRotatePoint(): PointF

    /**
     * 其实角度
     */
    abstract fun getStartRotateAngle(): Float

    abstract fun isHorizontal(): Boolean


}