package com.example.opengleslearn.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.opengleslearn.R
import com.example.opengleslearn.animation.*
import com.example.opengleslearn.animation.base.BaseVideoAnimation
import com.example.opengleslearn.data.AnimationShapeType
import com.example.opengleslearn.data.MoveAnimationType
import com.example.opengleslearn.render.AnimationRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.activity_picture_load.fl_surface_root

class AnimationOutActivity : BaseActivity() {
    private val mGlSurfaceView: CommonSurfaceView by lazy {
        CommonSurfaceView(
            this,
            AnimationRender(this)
        )
    }
    private var mAnimation: BaseVideoAnimation? = null

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
        btn_play.setOnClickListener { startPlay() }
        btn_pause.setOnClickListener { stopPlay() }
    }

    /**
     * 更新动画
     */
    private fun updateAnimation(progress: Float) {
        mAnimation?.run {
            setProgress(progress)
            mGlSurfaceView.setMvpMatrix(mProjectMatrix)
            mGlSurfaceView.setStMatrix(mModelMatrix)
            mGlSurfaceView.setRenderAlpha(mAlpha)
            mGlSurfaceView.setProgress(progress)
            mGlSurfaceView.setBlurSize(mBlurSize)
            mGlSurfaceView.requestRender()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_out_animation, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e(TAG, item.title.toString())
        tv_animation_name.text = "当前动画：${item.title}"
        if (mGlSurfaceView.getShapeType() != AnimationShapeType.DEFEAT) {
            mGlSurfaceView.setShapeType(AnimationShapeType.DEFEAT)
            recreateGlSurfaceView()
        }
        when (item.itemId) {
            R.id.action_fade_out -> mAnimation = FadeVideoAnimation(false)
            R.id.action_zoom_slightly -> mAnimation = ZoomSlightlyVideoAnimation(false)
            R.id.action_zoom_I -> mAnimation = ZoomSecondVideoAnimation(false)
            R.id.action_shader0 -> mAnimation = MoveVideoAnimation(false, MoveAnimationType.LEFT)
            R.id.action_shader1 -> mAnimation = MoveVideoAnimation(false, MoveAnimationType.RIGHT)
            R.id.action_shader2 -> mAnimation = MoveVideoAnimation(false, MoveAnimationType.TOP)
            R.id.action_shader3 -> mAnimation = MoveVideoAnimation(false, MoveAnimationType.BOTTOM)
            R.id.action_shader4 -> mAnimation = FlipVideoAnimation(false)
            R.id.action_shader5 -> mAnimation = RotateVideoAnimation(false)
            R.id.action_shader6 -> {
                mAnimation = SwirlVideoAnimation(false)
                mGlSurfaceView.setShapeType(AnimationShapeType.SWIRL)
                (mGlSurfaceView.getRender() as AnimationRender).mDirection = 1
                recreateGlSurfaceView()
            }
            R.id.action_zoom_out -> mAnimation = ZoomOutVideoAnimation(false)
            R.id.action_spin_u -> mAnimation = SpinOutVideoAnimation()
        }
        updateAnimation(seekbar.progress / 100f)
        return true
    }

    private fun recreateGlSurfaceView() {
        val viewGroup = mGlSurfaceView.parent as ViewGroup
        viewGroup.removeAllViews()
        fl_surface_root.removeAllViews()
        fl_surface_root.addView(mGlSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView.onResume()
    }

    override fun playProgress(progress: Int) {
        seekbar.progress = progress
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