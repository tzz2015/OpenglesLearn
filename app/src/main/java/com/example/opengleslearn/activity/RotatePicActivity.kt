package com.example.opengleslearn.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opengleslearn.R
import com.example.opengleslearn.render.AnimationRender
import com.example.opengleslearn.render.RotatePictureRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.activity_picture_load.*
import kotlinx.android.synthetic.main.activity_picture_load.fl_surface_root

class RotatePicActivity : AppCompatActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            RotatePictureRender(this)
        )
    }

    companion object {
        private const val TAG: String = "RotatePicActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_pic)
        fl_surface_root.addView(mGlSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView.onResume()
    }


    override fun onPause() {
        super.onPause()
        mGlSurfaceView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mGlSurfaceView.onDestroy()
    }

}