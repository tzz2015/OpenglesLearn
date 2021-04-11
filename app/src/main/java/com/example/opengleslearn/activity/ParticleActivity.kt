package com.example.opengleslearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.render.ParticlesRenderer
import com.example.opengleslearn.surfaceView.CommonSurfaceView

class ParticleActivity : AppCompatActivity() {
    private val mRender: ParticlesRenderer by lazy {  ParticlesRenderer(this)}
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            mRender
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
