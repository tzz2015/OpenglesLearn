package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES10.glClear
import android.opengl.GLES10.glViewport
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.example.opengleslearn.R
import com.example.opengleslearn.util.LoggerConfig
import com.example.opengleslearn.util.ShaderHelper
import com.example.opengleslearn.util.TextResourceReader
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
class SecondRender(context: Context) : GLSurfaceView.Renderer {
    private val mContext: Context = context
    private var mProgram: Int = 0
    private var mColorLocation = 0
    private var mPositionLocation = 0

    companion object {
        const val BYTES_PER_FLOAT = 4
        const val A_COLOR = "a_Color"
        const val A_POSITION = "a_Position"
        const val POSITION_COMPONENT_COUNT = 2
        const val COLOR_COMPONENT_COUNT = 3
        const val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT
    }

    // 需要开辟的顶点空间
    private var mVertexData: FloatBuffer? = null

    init {


        val tableVerticesWithTriangles = floatArrayOf( // Order of coordinates: X, Y, R, G, B
            // Triangle Fan
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,  // Line 1
            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,  // Mallets
            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f
        )
        mVertexData = ByteBuffer
            .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexData?.put(tableVerticesWithTriangles)
    }

    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        // 加载着色器源码
        val vertexShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, R.raw.second_vertex_shader)
        val fragmentShaderSource = TextResourceReader
            .readTextFileFromResource(mContext, R.raw.second_fragment_shader)
        // 编译着色器源码 生成着色器id
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        // 连接顶点着色器和片段着色器得到单个程序
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(mProgram)
        }
        glUseProgram(mProgram)
        // 创建一个color varying
        mColorLocation = glGetAttribLocation(mProgram, A_COLOR)
        // 创建属性位置
        mPositionLocation = glGetAttribLocation(mProgram, A_POSITION)
        // 完成顶点坐标和颜色的输入
        // 关联顶点数据 从开头读取
        mVertexData?.position(0)
        glVertexAttribPointer(
            mPositionLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            STRIDE,
            mVertexData
        )
        glEnableVertexAttribArray(mPositionLocation)
        // 关联颜色  从第二位开始取
        mVertexData?.position(POSITION_COMPONENT_COUNT)
        glVertexAttribPointer(
            mColorLocation,
            // 取后三位
            COLOR_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            STRIDE,
            mVertexData
        )
        glEnableVertexAttribArray(mColorLocation)
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        // 绘制白色桌面
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)

        // 从桌面中间绘制一条分割线
        glDrawArrays(GL_LINES, 6, 2)

        // 桌面两侧绘制两个点
        glDrawArrays(GL_POINTS, 8, 1)
        glDrawArrays(GL_POINTS, 9, 1)


    }
}