package com.example.opengleslearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.render.SecondRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView

class SecondActivity : AppCompatActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            SecondRender(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // mGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
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
