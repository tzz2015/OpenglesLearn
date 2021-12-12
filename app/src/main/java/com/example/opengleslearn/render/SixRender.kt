package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES10.glViewport
import android.opengl.GLES20
import android.opengl.Matrix
import com.example.opengleslearn.R
import com.example.opengleslearn.objects.Mallet2
import com.example.opengleslearn.objects.Puck
import com.example.opengleslearn.objects.Table
import com.example.opengleslearn.programs.ColorShaderProgram2
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
class SixRender(context: Context) : CommonRenderer() {
    private val mContext: Context = context
    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)
    private var table: Table? = null
    private var mallet: Mallet2? = null
    private var puck: Puck? = null
    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram2? = null

    private var texture: Int = 0


    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        table = Table()
        mallet = Mallet2(0.08f, 0.15f, 32)
        puck = Puck(0.06f, 0.02f, 32)

        textureProgram = TextureShaderProgram(mContext)
        colorProgram = ColorShaderProgram2(mContext)

        texture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface)[0]


    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        // 使用透视投影
        MatrixHelper.perspectiveM(
            projectionMatrix, 60f, width.toFloat()
                    / height.toFloat(), 1f, 10f
        )
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // 将投影和试图矩阵相乘缓存到viewProjectionMatrix
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // 绘制桌面
        positionTableInScene()
        textureProgram!!.useProgram()
        textureProgram!!.setUniforms(modelViewProjectionMatrix, texture)
        table!!.bindData(textureProgram)
        table!!.draw()

        // Draw the mallets.

        // Draw the mallets.
        positionObjectInScene(0f, mallet!!.height / 2f, -0.4f)
        colorProgram?.useProgram()
        colorProgram?.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f)
        mallet?.bindData(colorProgram)
        mallet?.draw()

        positionObjectInScene(0f, mallet!!.height / 2f, 0.4f)
        colorProgram!!.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f)
        // Note that we don't have to define the object data twice -- we just
        // draw the same mallet again but in a different position and with a
        // different color.
        // Note that we don't have to define the object data twice -- we just
        // draw the same mallet again but in a different position and with a
        // different color.
        mallet?.draw()

        // Draw the puck.

        // Draw the puck.
        positionObjectInScene(0f, puck!!.height / 2f, 0f)
        colorProgram?.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f)
        puck?.bindData(colorProgram)
        puck?.draw()

    }

    private fun positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f)
        Matrix.multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }

    // The mallets and the puck are positioned on the same plane as the table.
    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        Matrix.multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }
}