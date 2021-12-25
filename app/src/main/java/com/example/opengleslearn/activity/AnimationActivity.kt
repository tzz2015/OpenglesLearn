package com.example.opengleslearn.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.R
import com.example.opengleslearn.animation.BaseAnimation
import com.example.opengleslearn.animation.MoveAnimation
import com.example.opengleslearn.animation.MoveAnimationType
import com.example.opengleslearn.render.AnimationRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.activity_picture_load.fl_surface_root

class AnimationActivity : AppCompatActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            AnimationRender(this)
        )
    }
    private var mAnimation: BaseAnimation? = null

    companion object {
        private const val TAG: String = "AnimationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        fl_surface_root.addView(mGlSurfaceView)
        initListener()
    }

    private fun initListener() {
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_progress.text = "当前进度：$progress"
                updateAnimation(progress / 100f)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
            }
        })
    }

    /**
     * 更新动画
     */
    private fun updateAnimation(progress: Float) {
      mAnimation?.run {
          setProgress(progress)
          mGlSurfaceView.setMvpMatrix(mProjectMatrix)
      }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_animation, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e(TAG, item.title.toString())
        tv_animation_name.text = "当前动画：${item.title}"
        when (item.itemId) {
            R.id.action_shader0 -> mAnimation = MoveAnimation(MoveAnimationType.LEFT)
            R.id.action_shader1 -> mAnimation = MoveAnimation(MoveAnimationType.RIGHT)
            R.id.action_shader2 -> mAnimation = MoveAnimation(MoveAnimationType.TOP)
            R.id.action_shader3 -> mAnimation = MoveAnimation(MoveAnimationType.BOTTOM)
        }
        updateAnimation(seekbar.progress / 100f)
        return true
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