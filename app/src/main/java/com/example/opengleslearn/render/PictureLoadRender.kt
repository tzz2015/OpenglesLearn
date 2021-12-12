package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES10.glClear
import android.opengl.GLES10.glViewport
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.opengl.Matrix.orthoM
import com.example.opengleslearn.R
import com.example.opengleslearn.util.LoggerConfig
import com.example.opengleslearn.util.ShaderHelper
import com.example.opengleslearn.util.TextResourceReader
import com.example.opengleslearn.util.TextureHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
　　* @Description:
　　* @author 刘宇飞
　　* @date  2021/2/28 13:27
　　*/
class PictureLoadRender(context: Context) : GLSurfaceView.Renderer {
    private val mContext: Context = context
    private var mProgram: Int = 0
    private var mPositionLocation = 0
    private var mTextureLocation = 0
    private var mTextureUnitLocation = 0
    private var mMvpMatrixLocation = 0
    private var mStMatrixLocation = 0
    private val mMvpMatrix = FloatArray(16)
    private val mStMatrix = FloatArray(16)
    private var mTexture: Int = 0
    private var mPicWidth: Int = 0
    private var mPicHeight: Int = 0


    companion object {
        const val BYTES_PER_FLOAT = 4
        const val A_POSITION = "a_Position"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
        const val U_MVP_MATRIX = "uMVPMatrix"
        const val U_ST_MATRIX = "uSTMatrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"
        const val POSITION_COMPONENT_COUNT = 2
        const val STRIDE = (POSITION_COMPONENT_COUNT) * BYTES_PER_FLOAT
    }

    // 需要开辟的顶点空间
    private var mVertexData: FloatBuffer? = null
    private var mTextureData: FloatBuffer? = null

    init {
        // 顶点
        val vertexData = floatArrayOf(
            // Order of coordinates: X, Y,
            -1.0f, -1.0f, // v1
            1.0f, -1.0f,  // v2
            -1.0f, 1.0f,  // v3
            1.0f, 1.0f,   // v4
        )
        // 纹理
        val textureData = floatArrayOf(
            // Order of coordinates:  S, T
            0.0f, 1.0f, // v1
            1.0f, 1.0f, // v2
            0.0f, 0.0f, // v3
            1.0f, 0.0f, // v4
        )
        mVertexData = ByteBuffer
            .allocateDirect(vertexData.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexData?.put(vertexData)
        mTextureData = ByteBuffer
            .allocateDirect(textureData.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mTextureData?.put(textureData)
    }

    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        if (mProgram > 0) {
            glDeleteProgram(mProgram)
        }
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        // 加载着色器源码
        val vertexShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, R.raw.picture_load_vertex_shader)
        val fragmentShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, R.raw.five_fragment_shader)
        // 编译着色器源码 生成着色器id
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        // 连接顶点着色器和片段着色器得到单个程序
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(mProgram)
        }
        // 创建属性位置
        mPositionLocation = glGetAttribLocation(mProgram, A_POSITION)
        mTextureLocation = glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES)
        // 创建纹理
        mTextureUnitLocation = glGetUniformLocation(mProgram, U_TEXTURE_UNIT)

        // 创建一个MVP矩阵位置
        mMvpMatrixLocation = glGetUniformLocation(mProgram, U_MVP_MATRIX)
        // 创建一个ST矩阵位置
        mStMatrixLocation = glGetUniformLocation(mProgram, U_ST_MATRIX)


        if (mTexture > 0) {
            glDeleteTextures(0, intArrayOf(mTexture), 0)
        }
        val loadTexture = TextureHelper.loadTexture(mContext, R.drawable.icon_lyf)
        mTexture = loadTexture[0]
        mPicWidth = loadTexture[1]
        mPicHeight = loadTexture[2]

    }

    private fun bindDate() {
        // 关联顶点数据 从开头读取
        mVertexData?.position(0)
        glVertexAttribPointer(
            mPositionLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            0,
            mVertexData
        )
        glEnableVertexAttribArray(mPositionLocation)
        mTextureData?.position(0)
        glVertexAttribPointer(
            mTextureLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            0,
            mTextureData
        )
        glEnableVertexAttribArray(mTextureLocation)
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        Matrix.setIdentityM(mMvpMatrix, 0)
        Matrix.setIdentityM(mStMatrix, 0)
        val projectionMatrix = FloatArray(16)
        val viewMatrix = FloatArray(16)
        val w = mPicWidth
        val h = mPicHeight
        val sWH = w / h.toFloat()
        val sWidthHeight = width / height.toFloat()
        if (width > height) {
            if (sWH > sWidthHeight) {
                orthoM(
                    projectionMatrix,
                    0,
                    -sWidthHeight * sWH,
                    sWidthHeight * sWH,
                    -1f,
                    1f,
                    3f,
                    7f
                )
            } else {
                orthoM(
                    projectionMatrix,
                    0,
                    -sWidthHeight / sWH,
                    sWidthHeight / sWH,
                    -1f,
                    1f,
                    3f,
                    7f
                )
            }
        } else {
            if (sWH > sWidthHeight) {
                orthoM(
                    projectionMatrix,
                    0,
                    -1f,
                    1f,
                    -1 / sWidthHeight * sWH,
                    1 / sWidthHeight * sWH,
                    3f,
                    7f
                )
            } else {
                orthoM(
                    projectionMatrix,
                    0,
                    -1f,
                    1f,
                    -sWH / sWidthHeight,
                    sWH / sWidthHeight,
                    3f,
                    7f
                )
            }
        }
        //设置相机位置
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        //计算变换矩阵
        Matrix.multiplyMM(mMvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glUseProgram(mProgram)
        // 传入矩阵
        glUniformMatrix4fv(mMvpMatrixLocation, 1, false, mMvpMatrix, 0)
        glUniformMatrix4fv(mStMatrixLocation, 1, false, mStMatrix, 0)
        // 绑定纹理
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mTexture)
        glUniform1i(mTextureUnitLocation, 0)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        bindDate()

//        glDisableVertexAttribArray(mPositionLocation)
//        glDisableVertexAttribArray(mTextureLocation)
//        glBindTexture(GL_TEXTURE_2D, 0)


    }


}