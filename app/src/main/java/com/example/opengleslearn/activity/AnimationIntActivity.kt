package com.example.opengleslearn.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.R
import com.example.opengleslearn.animation.*
import com.example.opengleslearn.render.AnimationRender
import com.example.opengleslearn.surfaceView.CommonSurfaceView
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.activity_picture_load.fl_surface_root

class AnimationIntActivity : BaseActivity() {
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
        menuInflater.inflate(R.menu.menu_int_animation, menu)
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
            R.id.action_fade_int -> mAnimation = FadeAnimation(true)
            R.id.action_zoom_slightly -> mAnimation = ZoomSlightlyAnimation(true)
            R.id.action_zoom_I -> mAnimation = ZoomSecondAnimation(true)
            R.id.action_shader0 -> mAnimation = MoveAnimation(true, MoveAnimationType.LEFT)
            R.id.action_shader1 -> mAnimation = MoveAnimation(true, MoveAnimationType.RIGHT)
            R.id.action_shader2 -> mAnimation = MoveAnimation(true, MoveAnimationType.TOP)
            R.id.action_shader3 -> mAnimation = MoveAnimation(true, MoveAnimationType.BOTTOM)
            R.id.action_shader4 -> mAnimation = FlipAnimation(true)
            R.id.action_shader5 -> mAnimation = RotateAnimation(true)
            R.id.action_shader6 -> {
                mAnimation = SwirlAnimation(true)
                mGlSurfaceView.setShapeType(AnimationShapeType.SWIRL)
                recreateGlSurfaceView()
            }
            R.id.action_shader7 -> mAnimation = RotateInAnimation(true)
            R.id.action_shader8 -> mAnimation = RotateInAnimation(false)
            R.id.action_zoom_out -> mAnimation = ZoomOutAnimation(true)
            R.id.action_swing_right -> mAnimation = SwingRightAnimation()
            R.id.action_swing_down -> mAnimation = SwingDownAnimation()
            R.id.action_swing_right_down -> mAnimation = SwingRightDownAnimation()
            R.id.action_swing_right_up -> mAnimation = SwingRightUpAnimation()
            R.id.action_swing_left_down -> mAnimation = SwingLeftDownAnimation()
            R.id.action_swing_left_up -> mAnimation = SwingLeftUpAnimation()


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