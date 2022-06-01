package com.example.opengleslearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.render.FirstRender
import com.example.opengleslearn.render.ThreeDimensionalRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView

class ThreeDimensionalActivity : AppCompatActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            ThreeDimensionalRender(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mGlSurfaceView)
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
