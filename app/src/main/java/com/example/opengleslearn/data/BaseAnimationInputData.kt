package com.example.opengleslearn.data

import kotlin.math.abs

/**
 * @description: 线性输入输入参数 计算   平移为垂直情况 90° 或者 180°
 * @author:  刘宇飞
 * @date :   2021/12/31 12:24
 */
open class BaseAnimationInputData() {

    constructor(
        startProgress: Float,
        endProgress: Float,
        startRotate: Float,
        endRotate: Float,
        startTransition: Float,
        endTransition: Float, ) : this() {
        this.startProgress = startProgress
        this.endProgress = endProgress
        this.startRotate = startRotate
        this.endRotate = endRotate
        this.startTransition = startTransition
        this.endTransition = endTransition
    }

    var startProgress: Float = 0f
    var endProgress: Float = 0f
    var startRotate: Float = 0f
    var endRotate: Float = 0f
    var startTransition: Float = 0f
    var endTransition: Float = 0f
    var startAlpha: Float = 0f
    var endAlpha: Float = 0f

    /**
     * 获取当前真实进度
     */
    fun getCurrProgress(progress: Float): Float {
        return (progress - startProgress) / (endProgress - startProgress)
    }

    /**
     * 获取当前线性角度
     */
    fun getLinearRotate(progress: Float): Float {
       return getNumByProgress(startRotate,endRotate,progress)
    }

    /**
     * 获取当前线性平移参数
     */
    fun getLinearTransition(progress: Float): Float{
        return getNumByProgress(startTransition,endTransition,progress)
    }
    /**
     * 获取当前线性alpha参数
     */
    fun getLinearAlpha(progress: Float): Float{
        return getNumByProgress(startAlpha,endAlpha,progress)
    }

    /**
     * @param startNum 开始时的数值
     * @param endNum 结束数值
     * @param progress 进度
     * @return  返回该进度数值
     */
   private fun getNumByProgress(startNum: Float, endNum: Float, progress: Float): Float {
        return if (endNum >= startNum) {
            abs(endNum - startNum) * progress + startNum
        } else {
            -abs(endNum - startNum) * progress + startNum
        }
    }

}