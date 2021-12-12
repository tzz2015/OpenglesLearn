package com.example.opengleslearn.render

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import com.example.opengleslearn.R
import com.example.opengleslearn.objects.Mallet2
import com.example.opengleslearn.objects.Puck
import com.example.opengleslearn.objects.Table
import com.example.opengleslearn.programs.ColorShaderProgram2
import com.example.opengleslearn.programs.TextureShaderProgram
import com.example.opengleslearn.util.Geometry
import com.example.opengleslearn.util.Geometry.*
import com.example.opengleslearn.util.MatrixHelper
import com.example.opengleslearn.util.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
　　* @Description:3D 展示
　　* @author 刘宇飞
　　* @date  2021/2/28 13:27
　　*/
class SevenRender(context: Context) : CommonRenderer() {
    private val TAG = this.javaClass.name
    private val mContext: Context = context
    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)
    private val invertedViewProjectionMatrix = FloatArray(16)
    private var table: Table? = null
    private var mallet: Mallet2? = null
    private var puck: Puck? = null
    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram2? = null
    private var malletPressed = false
    private var blueMalletPosition: Point? = null
    private var previousBlueMalletPosition: Point? = null
    private var texture: Int = 0
    private var leftBound = -0.5f
    private var rightBound = 0.5f
    private var farBound = -0.8f
    private var nearBound = 0.8f

    private var puckPosition: Point? = null
    private var puckVector: Vector? = null


    fun handleTouchPress(normalizedX: Float, normalizedY: Float) {
        val ray: Ray = convertNormalized2DPointToRay(normalizedX, normalizedY)

        // Now test if this ray intersects with the mallet by creating a
        // bounding sphere that wraps the mallet.
        val malletBoundingSphere = Geometry.Sphere(
            Point(
                blueMalletPosition!!.x,
                blueMalletPosition!!.y,
                blueMalletPosition!!.z
            ),
            mallet!!.height / 2f
        )

        // If the ray intersects (if the user touched a part of the screen that
        // intersects the mallet's bounding sphere), then set malletPressed =
        // true.
        malletPressed = intersects(malletBoundingSphere, ray)
    }

    private fun convertNormalized2DPointToRay(
        normalizedX: Float, normalizedY: Float
    ): Ray {
        // We'll convert these normalized device coordinates into world-space
        // coordinates. We'll pick a point on the near and far planes, and draw a
        // line between them. To do this transform, we need to first multiply by
        // the inverse matrix, and then we need to undo the perspective divide.
        val nearPointNdc = floatArrayOf(normalizedX, normalizedY, -1f, 1f)
        val farPointNdc = floatArrayOf(normalizedX, normalizedY, 1f, 1f)
        val nearPointWorld = FloatArray(4)
        val farPointWorld = FloatArray(4)
        Matrix.multiplyMV(
            nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0
        )
        Matrix.multiplyMV(
            farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0
        )

        // Why are we dividing by W? We multiplied our vector by an inverse
        // matrix, so the W value that we end up is actually the *inverse* of
        // what the projection matrix would create. By dividing all 3 components
        // by W, we effectively undo the hardware perspective divide.
        divideByW(nearPointWorld)
        divideByW(farPointWorld)

        // We don't care about the W value anymore, because our points are now
        // in world coordinates.
        val nearPointRay = Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2])
        val farPointRay = Point(farPointWorld[0], farPointWorld[1], farPointWorld[2])
        return Ray(
            nearPointRay,
            vectorBetween(nearPointRay, farPointRay)
        )
    }

    private fun divideByW(vector: FloatArray) {
        vector[0] /= vector[3]
        vector[1] /= vector[3]
        vector[2] /= vector[3]
    }


    fun handleTouchDrag(normalizedX: Float, normalizedY: Float) {
        if (malletPressed) {
            val ray: Ray = convertNormalized2DPointToRay(normalizedX, normalizedY)
            // Define a plane representing our air hockey table.
            val plane = Plane(Point(0F, 0F, 0F), Vector(0F, 1F, 0F))
            // Find out where the touched point intersects the plane
            // representing our table. We'll move the mallet along this plane.
            val touchedPoint = Geometry.intersectionPoint(ray, plane)
            // Clamp to bounds
            previousBlueMalletPosition = blueMalletPosition
            /*
            blueMalletPosition =
                new Point(touchedPoint.x, mallet.height / 2f, touchedPoint.z);
            */
            // Clamp to bounds
            blueMalletPosition = Point(
                clamp(
                    touchedPoint.x,
                    leftBound + mallet!!.radius,
                    rightBound - mallet!!.radius
                ),
                mallet!!.height / 2f,
                clamp(
                    touchedPoint.z,
                    0f + mallet!!.radius,
                    nearBound - mallet!!.radius
                )
            )

            // Now test if mallet has struck the puck.
            val distance = vectorBetween(blueMalletPosition, puckPosition).length()
            Log.e(TAG, "distance:$distance")
            Log.e(TAG, "radius:" + (puck!!.radius + mallet!!.radius))
            if (distance < puck!!.radius + mallet!!.radius) {
                // The mallet has struck the puck. Now send the puck flying
                // based on the mallet velocity.
                puckVector = vectorBetween(
                    previousBlueMalletPosition, blueMalletPosition
                )
            }
        }
    }

    private fun clamp(value: Float, min: Float, max: Float): Float {
        return Math.min(max, Math.max(value, min))
    }

    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        table = Table()
        mallet = Mallet2(0.08f, 0.15f, 32)
        puck = Puck(0.06f, 0.02f, 32)
        blueMalletPosition = Point(0f, mallet!!.height / 2f, 0.4f)
        puckPosition = Point(0f, puck!!.height / 2f, 0f)
        puckVector = Vector(0f, 0f, 0f)
        textureProgram = TextureShaderProgram(mContext)
        colorProgram = ColorShaderProgram2(mContext)
        texture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface)[0]
    }

    override fun onSurfaceChanged(glUnused: GL10?, width: Int, height: Int) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height)
        MatrixHelper.perspectiveM(
            projectionMatrix, 45f, width.toFloat()
                    / height.toFloat(), 1f, 10f
        )
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Translate the puck by its vector
        puckPosition = puckPosition!!.translate(puckVector)

        // If the puck struck a side, reflect it off that side.
        if (puckPosition!!.x < leftBound + puck!!.radius
            || puckPosition!!.x > rightBound - puck!!.radius
        ) {
            puckVector = Vector(-puckVector!!.x, puckVector!!.y, puckVector!!.z)
            puckVector = puckVector!!.scale(0.9f)
        }
        if (puckPosition!!.z < farBound + puck!!.radius
            || puckPosition!!.z > nearBound - puck!!.radius
        ) {
            puckVector = Vector(puckVector!!.x, puckVector!!.y, -puckVector!!.z)
            puckVector = puckVector!!.scale(0.9f)
        }
        // Clamp the puck position.
        puckPosition = Point(
            clamp(puckPosition!!.x, leftBound + puck!!.radius, rightBound - puck!!.radius),
            puckPosition!!.y,
            clamp(puckPosition!!.z, farBound + puck!!.radius, nearBound - puck!!.radius)
        )

        // Friction factor
        puckVector = puckVector!!.scale(0.99f)

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        Matrix.multiplyMM(
            viewProjectionMatrix, 0, projectionMatrix, 0,
            viewMatrix, 0
        )
        Matrix.invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0)

        // Draw the table.
        positionTableInScene()
        textureProgram!!.useProgram()
        textureProgram!!.setUniforms(modelViewProjectionMatrix, texture)
        table!!.bindData(textureProgram)
        table!!.draw()

        // Draw the mallets.
        positionObjectInScene(0f, mallet!!.height / 2f, -0.4f)
        colorProgram!!.useProgram()
        colorProgram!!.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f)
        mallet!!.bindData(colorProgram)
        mallet!!.draw()
        positionObjectInScene(
            blueMalletPosition!!.x, blueMalletPosition!!.y,
            blueMalletPosition!!.z
        )
        colorProgram!!.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f)
        // Note that we don't have to define the object data twice -- we just
        // draw the same mallet again but in a different position and with a
        // different color.
        mallet!!.draw()

        // Draw the puck.
        positionObjectInScene(puckPosition!!.x, puckPosition!!.y, puckPosition!!.z)
        colorProgram!!.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f)
        puck!!.bindData(colorProgram)
        puck!!.draw()
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