package com.example.opengleslearn.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.render.SevenRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView

class SevenActivity : AppCompatActivity() {
    private val mRender:SevenRender by lazy {  SevenRender(this)}
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            mRender
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mGlSurfaceView)
        initTouchListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        mGlSurfaceView.setOnTouchListener { v, event ->
            if (event != null) {
                val normalizedX: Float = event.x / v.width.toFloat() * 2 - 1
                val normalizedY: Float = -(event.y / v.height.toFloat() * 2 - 1)
                if (event.action == MotionEvent.ACTION_DOWN) {
                    mGlSurfaceView.queueEvent {
                        mRender.handleTouchPress(
                            normalizedX, normalizedY
                        )
                    }
                } else if (event.action == MotionEvent.ACTION_MOVE) {
                    mGlSurfaceView.queueEvent {
                        mRender.handleTouchDrag(
                            normalizedX, normalizedY
                        )
                    }
                }
                true
            } else false
        }
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGlSurfaceView.onPause()
    }


}
