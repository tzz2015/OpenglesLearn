package com.example.opengleslearn.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.opengleslearn.R
import com.example.opengleslearn.render.PictureLoadRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView
import com.example.opengleslearn.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_picture_load.*

class PictureLoadActivity : AppCompatActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            PictureLoadRender(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_load)
        initClick()
    }

    private fun initClick() {
        btn_horizontal.isSelected = true
        addGlSurfaceView(9f/16f)
        btn_horizontal.setOnClickListener {
            if (!btn_horizontal.isSelected) {
                btn_horizontal.isSelected = true
                btn_vertical.isSelected = false
                addGlSurfaceView(9f/16f)
            }
        }
        btn_vertical.setOnClickListener {
            if (!btn_vertical.isSelected) {
                btn_vertical.isSelected = true
                btn_horizontal.isSelected = false
                addGlSurfaceView(16f/9f)
            }
        }
    }

    private fun addGlSurfaceView(ratio: Float) {
        mGlSurfaceView.parent?.let {
            val parent = it as ViewGroup
            parent.removeAllViews()
        }
        fl_surface_root.removeAllViews()
        val width = ScreenUtils.getScreenWidth(this)
        val height = (width * ratio).toInt()
        val layoutParams = ConstraintLayout.LayoutParams(width, height)
        mGlSurfaceView.layoutParams = layoutParams
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


}