package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES10.glClear
import android.opengl.GLES20.*
import android.opengl.Matrix
import android.opengl.Matrix.orthoM
import com.example.opengleslearn.R
import com.example.opengleslearn.data.AnimationShapeType
import com.example.opengleslearn.util.*
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
class AnimationRender(context: Context) : CommonRenderer() {
    private val mContext: Context = context
    private var mPositionLocation = 0
    private var mTextureLocation = 0
    private var mTextureUnitLocation = 0
    private var mAlphaLocation = 0
    private var mDirectionLocation = 0
    private var mProgressLocation = 0
    private var mInputSizeLocation = 0
    private var mMvpMatrixLocation = 0
    private var mStMatrixLocation = 0
    private var mBlurSizeLocation = 0
    private val mMvpMatrix = FloatArray(16)
    private val mProjectViewMatrix = FloatArray(16)
    private val mStMatrix = FloatArray(16)
    private var mBgTexture: Int = 0
    private var mPicWidth: Int = 0
    private var mPicHeight: Int = 0
    private var mAlpha: Float = 1.0f
    private var mShapeType: Int = AnimationShapeType.DEFEAT
    var mDirection: Int = 0
    private var mProgress: Float = 0f
    private var mBlurSize: Float = 0f
    private var mIconId: Int = R.drawable.icon_animation
    private var mScreenRatio: Float = 0f


    companion object {
        const val BYTES_PER_FLOAT = 4
        const val A_POSITION = "a_Position"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
        const val U_MVP_MATRIX = "uMVPMatrix"
        const val U_ST_MATRIX = "uSTMatrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"
        const val U_ALPHA = "alpha"
        const val U_INPUT_SIZE = "inputSize"
        const val U_DIRECTION = "direction"
        const val U_PROGRESS = "progress"
        const val U_BLUR_SIZE = "blurSize"

        const val POSITION_COMPONENT_COUNT = 2
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

    override fun setMvpMatrix(matrix: FloatArray) {
        Matrix.setIdentityM(mMvpMatrix, 0)
        // 缩放回来
        if (mScreenRatio > 1.0f) {
            Matrix.scaleM(matrix, 0, mScreenRatio, 1.0f, 1.0f)
            Matrix.scaleM(matrix, 0, 1f / mScreenRatio, 1f / mScreenRatio, 1.0f)
        } else {
            Matrix.scaleM(matrix, 0, 1.0f, 1.0f / mScreenRatio, 1.0f)
            Matrix.scaleM(matrix, 0, mScreenRatio, mScreenRatio, 1.0f)
        }
        Matrix.multiplyMM(mMvpMatrix, 0, mProjectViewMatrix, 0, matrix, 0)
    }

    override fun setStMatrix(matrix: FloatArray) {
        Matrix.setIdentityM(mStMatrix, 0)
        System.arraycopy(matrix, 0, mStMatrix, 0, mStMatrix.size)

    }

    override fun setAlpha(alpha: Float) {
        mAlpha = alpha
    }

    override fun setShapeType(type: Int) {
        this.mShapeType = type
    }

    override fun getShapeType(): Int {
        return mShapeType
    }

    override fun setProgress(progress: Float) {
        this.mProgress = progress
    }

    override fun setBlurSize(blurSize: Float) {
        this.mBlurSize = blurSize
    }


    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        createProgram()
    }

    private fun createProgram() {
        if (mProgram > 0) {
            glDeleteProgram(mProgram)
        }
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        // 加载着色器源码
        val vertexShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, R.raw.picture_load_vertex_shader)
        val fragmentShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, getFragmentShader())
        // 编译着色器源码 生成着色器id
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        // 连接顶点着色器和片段着色器得到单个程序
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        // 创建属性位置
        mPositionLocation = glGetAttribLocation(mProgram, A_POSITION)
        mTextureLocation = glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES)
        // 创建纹理
        mTextureUnitLocation = glGetUniformLocation(mProgram, U_TEXTURE_UNIT)
        // 创建透明度桩位
        mAlphaLocation = glGetUniformLocation(mProgram, U_ALPHA)
        // 方向
        mDirectionLocation = glGetUniformLocation(mProgram, U_DIRECTION)
        // 输入宽高
        mInputSizeLocation = glGetUniformLocation(mProgram, U_INPUT_SIZE)
        // 进度
        mProgressLocation = glGetUniformLocation(mProgram, U_PROGRESS)
        // 模糊度
        mBlurSizeLocation = glGetUniformLocation(mProgram, U_BLUR_SIZE)

        // 创建一个MVP矩阵位置
        mMvpMatrixLocation = glGetUniformLocation(mProgram, U_MVP_MATRIX)
        // 创建一个ST矩阵位置
        mStMatrixLocation = glGetUniformLocation(mProgram, U_ST_MATRIX)


        if (mBgTexture > 0) {
            glDeleteTextures(0, intArrayOf(mBgTexture), 0)
        }
        val loadTexture = TextureHelper.loadTexture(mContext, mIconId)
        mBgTexture = loadTexture[0]
        mPicWidth = loadTexture[1]
        mPicHeight = loadTexture[2]
    }

    fun setIconId(id: Int) {
        this.mIconId = id
    }

    /**
     * 根据类型获取脚本
     */
    private fun getFragmentShader(): Int {
        when (mShapeType) {
            AnimationShapeType.SWIRL -> return R.raw.animation_swirl_fragment_shader
        }
        return R.raw.animation_fragment_shader
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
        mTextureData?.position(0)
        glVertexAttribPointer(
            mTextureLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            0,
            mTextureData
        )
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(p0, width, height)
        val canvasSize = mViewWidth.coerceAtLeast(mViewHeight)
        glViewport(
            (mViewWidth - canvasSize) / 2,
            (mViewHeight - canvasSize) / 2,
            canvasSize,
            canvasSize
        )
        centerInsert()
    }

    /**
     * 居中显示
     */
    private fun centerInsert() {
        Matrix.setIdentityM(mMvpMatrix, 0)
        Matrix.setIdentityM(mStMatrix, 0)
        Matrix.setIdentityM(mProjectViewMatrix, 0)
        val projectionMatrix = FloatArray(16)
        val viewMatrix = FloatArray(16)

        val picWidth = mPicWidth.toFloat()
        val picHeight = mPicHeight.toFloat()
        val picRatio = picWidth / picHeight
         mScreenRatio = mViewWidth / mViewHeight.toFloat()
        if (mViewWidth > mViewHeight) {
            if (picRatio > mScreenRatio) {
                orthoM(
                    projectionMatrix,
                    0,
                    -mScreenRatio * picRatio,
                    mScreenRatio * picRatio,
                    -1f,
                    1f,
                    3f,
                    7f
                )
            } else {
                orthoM(
                    projectionMatrix,
                    0,
                    -mScreenRatio / picRatio,
                    mScreenRatio / picRatio,
                    -1f,
                    1f,
                    3f,
                    7f
                )
            }
        } else {
            if (picRatio > mScreenRatio) {
                orthoM(
                    projectionMatrix,
                    0,
                    -1f,
                    1f,
                    -1 / mScreenRatio * picRatio,
                    1 / mScreenRatio * picRatio,
                    3f,
                    7f
                )
            } else {
                orthoM(
                    projectionMatrix,
                    0,
                    -1f,
                    1f,
                    -picRatio / mScreenRatio,
                    picRatio / mScreenRatio,
                    3f,
                    7f
                )
            }
        }
        //设置相机位置
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 3.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        //计算变换矩阵
        Matrix.multiplyMM(mProjectViewMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Constants.MAX_HORIZONTAL_OFFSET = 2.0f - mProjectViewMatrix[0]
        Constants.MAX_VERTICAL_OFFSET = 2.0f - mProjectViewMatrix[5]
        System.arraycopy(mProjectViewMatrix, 0, mMvpMatrix, 0, mMvpMatrix.size)
    }


    override fun onDrawFrame(glUnused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        drawBackGround()
    }

    /**
     * 绘制背景
     */
    private fun drawBackGround() {

        glUseProgram(mProgram)
        // 传入矩阵
        glUniformMatrix4fv(mMvpMatrixLocation, 1, false, mMvpMatrix, 0)
        glUniformMatrix4fv(mStMatrixLocation, 1, false, mStMatrix, 0)
        // 透明度
        glUniform1f(mAlphaLocation, mAlpha)
        // 大小
        glUniform2f(mInputSizeLocation, mViewWidth.toFloat(), mViewHeight.toFloat())
        glUniform1i(mDirectionLocation, mDirection)
        glUniform1f(mProgressLocation, mProgress)
        glUniform1f(mBlurSizeLocation, mBlurSize)
        glEnableVertexAttribArray(mPositionLocation)
        glEnableVertexAttribArray(mTextureLocation)
        // 绑定纹理
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mBgTexture)
        glUniform1i(mTextureUnitLocation, 0)
        bindDate()
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        // 释放使用
        glDisableVertexAttribArray(mPositionLocation)
        glDisableVertexAttribArray(mTextureLocation)
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    override fun onDestroy() {
        if (mBgTexture > 0) {
            glDeleteTextures(0, intArrayOf(mBgTexture), 0)
        }
    }


}