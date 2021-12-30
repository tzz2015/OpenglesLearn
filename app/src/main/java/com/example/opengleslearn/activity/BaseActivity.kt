package com.example.opengleslearn.activity

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/**
 * @description:
 * @author:  刘宇飞
 * @date :   2021/12/30 15:06
 */
abstract class BaseActivity : AppCompatActivity() {
    // 两秒
    private val mMaxTime = 2000f
    private var mCurrTime = 0f
    private val mHandler: Handler = Handler()
    private var mRunnable: Runnable = object : Runnable {
        override fun run() {
            mCurrTime += 50
            if (mCurrTime <= mMaxTime) {
                //每隔100ms循环执行run方法
                mHandler.postDelayed(this, 50)
                playProgress((mCurrTime / mMaxTime * 100).toInt())
            } else {
                mHandler.removeCallbacks(this)
            }
        }
    }

    abstract fun playProgress(progress: Int)


    fun startPlay() {
        mCurrTime = -50f
        mHandler.postDelayed(mRunnable, 50)
    }

    fun stopPlay() {
        mHandler.removeCallbacks(mRunnable)
        playProgress(0)
    }

}