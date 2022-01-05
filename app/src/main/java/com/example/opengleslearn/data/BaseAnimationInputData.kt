package com.example.opengleslearn.data

import com.example.opengleslearn.util.AnimationInterpolator

/**
 * @description: 线性输入输入参数 计算   平移为垂直情况 90° 或者 180°
 * @author:  刘宇飞
 * @date :   2021/12/31 12:24
 */
open class BaseAnimationInputData() {

    companion object {
        const val WIDTH: Float = 800f
        const val HEIGHT: Float = 800f
    }

    var startProgress: Float = 0f
    var endProgress: Float = 1f
    var startRotate: Float = 0f
    var endRotate: Float = 0f
    var startTransitionX: Float = WIDTH / 2f
    var endTransitionX: Float = WIDTH / 2f
    var startTransitionY: Float = HEIGHT / 2f
    var endTransitionY: Float = HEIGHT / 2f
    var startAlpha: Float = 1f
    var endAlpha: Float = 1f
    var speedType: Int = AnimationSpeedType.LINEAR
    var startScale: Float = 1f
    var endScale: Float = 1f
    var startBlur: Double = 0.0
    var endBlur: Double = 0.0

    // true 作用于顶点 false 作用于纹理
    var isVertexModel: Boolean = false

    constructor(
        startProgress: Float = 0.0f,
        endProgress: Float = 1.0f,
        startRotate: Float = 0.0f,
        endRotate: Float = 0f,
        speedType: Int = AnimationSpeedType.LINEAR,
        startTransitionX: Float = WIDTH / 2f,
        endTransitionX: Float = WIDTH / 2f,
        startBlur: Double = 0.0,
        endBlur: Double = 0.0,
        startScale: Float = 1.0f,
        endScale: Float = 1.0f,
        isVertexModel: Boolean = false
    ) : this() {
        this.startProgress = startProgress
        this.endProgress = endProgress
        this.startRotate = startRotate
        this.endRotate = endRotate
        this.startTransitionX = startTransitionX
        this.endTransitionX = endTransitionX
        this.speedType = speedType
        this.startBlur = startBlur
        this.endBlur = endBlur
        this.isVertexModel = isVertexModel
        this.startScale = startScale
        this.endScale = endScale
    }


    constructor(
        startProgress: Float = 0.0f,
        endProgress: Float = 1.0f,
        startTransitionX: Float = WIDTH / 2f,
        endTransitionX: Float = WIDTH / 2f,
        startTransitionY: Float = HEIGHT / 2f,
        endTransitionY: Float = HEIGHT / 2f,
        speedType: Int = AnimationSpeedType.LINEAR,
        isVertexModel: Boolean = false
    ) : this() {
        this.startProgress = startProgress
        this.endProgress = endProgress
        this.startTransitionX = startTransitionX
        this.endTransitionX = endTransitionX
        this.startTransitionY = startTransitionY
        this.endTransitionY = endTransitionY
        this.speedType = speedType
        this.isVertexModel = isVertexModel
    }




    /**
     * 获取当前真实进度
     */
    private fun getCurrProgress(progress: Float): Float {
        var currProgress = (progress - startProgress) / (endProgress - startProgress)
        when (speedType) {
            AnimationSpeedType.EASE_IN_QUAD -> currProgress =
                AnimationInterpolator.easeInQuad(currProgress)
            AnimationSpeedType.EASE_IN_CUBIC -> currProgress =
                AnimationInterpolator.easeInCubic(currProgress)
        }
        return currProgress
    }

    /**
     * 获取当前线性角度
     */
    fun getCurrRotate(progress: Float): Float {
        return getNumByProgress(startRotate, endRotate, getCurrProgress(progress))
    }

    /**
     * 获取当前线性平移参数
     */
    fun getTextureTransitionX(progress: Float): Float {
        val x = getNumByProgress(startTransitionX, endTransitionX, getCurrProgress(progress))
        return windowXToTextureX(x)
    }

    fun getVertexTransitionX(progress: Float): Float {
        val x = getNumByProgress(startTransitionX, endTransitionX, getCurrProgress(progress))
        return windowXToVertexX(x)
    }

    fun getTextureTransitionY(progress: Float): Float {
        val y =
            getNumByProgress(startTransitionY, endTransitionY, getCurrProgress(progress))
        return windowYToTextureY(y)
    }

    fun getVertexTransitionY(progress: Float): Float {
        val y =
            getNumByProgress(startTransitionY, endTransitionY, getCurrProgress(progress))
        return windowYToVertexY(y)
    }

    /**
     * 获取当前线性alpha参数
     */
    fun getCurrAlpha(progress: Float): Float {
        return getNumByProgress(startAlpha, endAlpha, getCurrProgress(progress))
    }

    /**
     * 获取当前的缩放值
     */
    fun getCurrScale(progress: Float): Float {
        return getNumByProgress(startScale, endScale, getCurrProgress(progress))
    }

    /**
     * 获取当前模糊度
     */
    fun getCurrBlur(progress: Float): Float {
        return getNumByProgress(startBlur.toFloat(), endBlur.toFloat(), getCurrProgress(progress))
    }

    /**
     * @param startNum 开始时的数值
     * @param endNum 结束数值
     * @param progress 进度
     * @return  返回该进度数值
     */
    private fun getNumByProgress(startNum: Float, endNum: Float, progress: Float): Float {
        return (endNum - startNum) * progress + startNum
        /* return if (endNum >= startNum) {
             abs(endNum - startNum) * progress + startNum
         } else {
             -abs(endNum - startNum) * progress + startNum
         }*/
    }

    /**
     * 窗口坐标转纹理坐标
     */
    private fun windowXToTextureX(x: Float): Float {

        return if (x >= 0) -(x - WIDTH / 2f) / WIDTH else (-x + WIDTH / 2f) / WIDTH
    }

    private fun windowYToTextureY(y: Float): Float {
        return if (y >= 0) (y - HEIGHT / 2f) / HEIGHT else -(-y + HEIGHT / 2f) / HEIGHT
    }

    /**
     * 窗口坐标转顶点坐标
     */
    private fun windowXToVertexX(x: Float): Float {
        return (x - WIDTH / 2f) / (WIDTH / 2f)
    }

    private fun windowYToVertexY(y: Float): Float {
        return (y - HEIGHT / 2f) / (HEIGHT / 2f)
    }

}