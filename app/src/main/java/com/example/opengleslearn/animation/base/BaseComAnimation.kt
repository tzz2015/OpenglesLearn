package com.example.opengleslearn.animation.base

import android.graphics.PointF
import android.opengl.Matrix
import android.util.Log
import com.example.opengleslearn.data.BaseAnimationInputData
import com.example.opengleslearn.util.Matrix3DUtils

/**
 * @description: 组合动画
 * @author:  刘宇飞
 * @date :   2022/1/4 11:36
 */
abstract class BaseComAnimation() : BaseAnimation() {
    // 组合动画
    protected val mComList: MutableList<BaseAnimationInputData> = ArrayList()

    // 平移
    protected val mTransitionList: MutableList<BaseAnimationInputData> = ArrayList()

    // 旋转
    protected val mRotateList: MutableList<BaseAnimationInputData> = ArrayList()

    // 缩放
    protected val mScaleList: MutableList<BaseAnimationInputData> = ArrayList()

    // 模糊
    protected val mBlurList: MutableList<BaseAnimationInputData> = ArrayList()
    private var mRotatePoint = PointF()

    init {
        initData()
    }


    private fun initData() {
        initComList()
        initTransitionList()
        initRotateList()
        initScaleList()
        initBlurList()
        mRotatePoint = getRotatePoint()
    }

    abstract fun initComList()
    abstract fun initTransitionList()
    abstract fun initRotateList()
    abstract fun initScaleList()
    abstract fun initBlurList()

    /**
     * 默认按中心点旋转
     */
    protected open fun getRotatePoint(): PointF {
        return PointF(0.5f, 0.5f)
    }


    override fun setProgress(progress: Float) {
        reset()
        doComAnimation(progress)
        doTransition(progress)
        doRotate(progress)
        doScale(progress)
        doBlur(progress)
    }

    /**
     * 做组合动画
     */
    private fun doComAnimation(progress: Float) {
        val currData = getCurrData(mComList, progress)
        applyTransition(currData, progress)
        applyRotate(currData, progress)
        applyScale(currData, progress)
        applyBlur(currData, progress)
        applyAlpha(currData, progress)
    }

    /**
     * 做平移动画
     */
    private fun doTransition(progress: Float) {
        val currData = getCurrData(mTransitionList, progress)
        applyTransition(currData, progress)
    }

    /**
     * 做旋转动画
     */
    private fun doRotate(progress: Float) {
        val currData = getCurrData(mRotateList, progress)
        applyRotate(currData, progress)
    }

    /**
     * 做缩放动画
     */
    private fun doScale(progress: Float) {
        val currData = getCurrData(mScaleList, progress)
        applyScale(currData, progress)
    }

    /**
     * 做模糊
     */
    private fun doBlur(progress: Float) {
        val currData = getCurrData(mBlurList, progress)
        applyBlur(currData, progress)
    }

    /**
     * 平移
     */
    private fun applyTransition(data: BaseAnimationInputData?, progress: Float) {
        data?.run {
            if (isVertexModel) {
                val transitionX = getVertexTransitionX(progress)
                val transitionY = getVertexTransitionY(progress)
                Matrix3DUtils.preTranslateM(mProjectMatrix, transitionX, transitionY, 0f)
            } else {
                val transitionX = getTextureTransitionX(progress)
                val transitionY = getTextureTransitionY(progress)
                Matrix3DUtils.preTranslateM(mModelMatrix, transitionX, transitionY, 0f)
            }
        }
    }

    /**
     * 旋转
     */
    private fun applyRotate(data: BaseAnimationInputData?, progress: Float) {
        data?.run {
            val currRotate = getCurrRotate(progress)
            if (isVertexModel) {
                Matrix3DUtils.preTranslateM(mModelMatrix, -mRotatePoint.x, -mRotatePoint.y, 0f)
                Matrix3DUtils.preRotateM(mModelMatrix, currRotate, 0f, 0f, 1f)
                Matrix3DUtils.preTranslateM(mModelMatrix, mRotatePoint.x, mRotatePoint.y, 0f)
            } else {
                Matrix3DUtils.preTranslateM(mModelMatrix, -mRotatePoint.x, -mRotatePoint.y, 0f)
                Matrix3DUtils.preRotateM(mModelMatrix, currRotate, 0f, 0f, 1f)
                Matrix3DUtils.preTranslateM(mModelMatrix, mRotatePoint.x, mRotatePoint.y, 0f)
            }
        }
    }

    /**
     * 缩放
     */
    private fun applyScale(data: BaseAnimationInputData?, progress: Float) {
        data?.run {
            val scale = 1f / getCurrScale(progress)
            if (isVertexModel) {
                Matrix3DUtils.preTranslateM(mProjectMatrix, -mRotatePoint.x, -mRotatePoint.y, 0f)
                Matrix3DUtils.preScaleM(mProjectMatrix, scale, scale, 1f)
                Matrix3DUtils.preTranslateM(mProjectMatrix, mRotatePoint.x, mRotatePoint.y, 0f)
            } else {
                Matrix3DUtils.preTranslateM(mModelMatrix, -mRotatePoint.x, -mRotatePoint.y, 0f)
                Matrix3DUtils.preScaleM(mModelMatrix, scale, scale, 1f)
                Matrix3DUtils.preTranslateM(mModelMatrix, mRotatePoint.x, mRotatePoint.y, 0f)
            }
        }
    }

    /**
     * 模糊度
     */
    private fun applyBlur(data: BaseAnimationInputData?, progress: Float) {
        data?.run {
            mBlurSize = getCurrBlur(progress)
        }
    }

    /**
     * 透明度
     */
    private fun applyAlpha(data: BaseAnimationInputData?, progress: Float) {
        data?.run {
            mAlpha = getCurrAlpha(progress)
        }
    }


    /**
     * 根据时间获取当前需要处理的值
     */
    private fun getCurrData(
        mList: MutableList<BaseAnimationInputData>,
        progress: Float
    ): BaseAnimationInputData? {
        if (mList.isEmpty()) {
            return null
        }
        for (item in mList) {
            if (progress <= item.endProgress && progress > item.startProgress) {
                return item
            }
            if (progress == 0f && item.startProgress == 0f) {
                return item
            }
        }
        return null
    }
}