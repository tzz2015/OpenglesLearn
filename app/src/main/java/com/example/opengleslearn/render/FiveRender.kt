package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES10.glViewport
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.opengl.Matrix.rotateM
import android.opengl.Matrix.setIdentityM
import com.example.opengleslearn.R
import com.example.opengleslearn.objects.Mallet
import com.example.opengleslearn.objects.Table
import com.example.opengleslearn.programs.ColorShaderProgram
import com.example.opengleslearn.programs.TextureShaderProgram
import com.example.opengleslearn.util.MatrixHelper
import com.example.opengleslearn.util.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
　　* @Description:3D 展示
　　* @author 刘宇飞
　　* @date  2021/2/28 13:27
　　*/
class FiveRender(context: Context) : GLSurfaceView.Renderer {
    private val mContext: Context = context
    private val mProjectionMatrix = FloatArray(16)
    private val mModelMatrix = FloatArray(16)
    private var table: Table? = null
    private var mallet: Mallet? = null
    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram? = null

    private var texture: Int = 0


    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        table = Table()
        mallet = Mallet()

        textureProgram = TextureShaderProgram(mContext)
        colorProgram = ColorShaderProgram(mContext)

        texture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface)[0]


    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        // 使用透视投影
        MatrixHelper.perspectiveM(
            mProjectionMatrix, 60f, width.toFloat()
                    / height.toFloat(), 1f, 10f
        )
        setIdentityM(mModelMatrix, 0)
        Matrix.translateM(mModelMatrix, 0, 0f, 0f, -2.5f)
        rotateM(mModelMatrix, 0, -60f, 1f, 0f, 0f)
        val temp = FloatArray(16)
        Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, mModelMatrix, 0)
        System.arraycopy(temp, 0, mProjectionMatrix, 0, temp.size)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // 绘制桌面
        textureProgram?.useProgram()
        textureProgram?.setUniforms(mProjectionMatrix, texture)
        table?.bindData(textureProgram)
        table?.draw()

        // 绘制木槌
        colorProgram?.useProgram()
        colorProgram?.setUniforms(mProjectionMatrix)
        mallet?.bindData(colorProgram)
        mallet?.draw()

    }
}