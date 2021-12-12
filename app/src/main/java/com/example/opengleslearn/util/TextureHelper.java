package com.example.opengleslearn.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

/**
 * 　　* @Description: 纹理加载工具
 * 　　* @author 刘宇飞
 * 　　* @date  2021/3/24 20:37
 */
public class TextureHelper {
    private static final String TAG = "TextureHelper";

    /**
     * 加载图片到OpenGL
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static int[] loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        // 创建一个纹理对象 将id 储存在数组
        glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            glDeleteTextures(1, textureObjectIds, 0);
            if (LoggerConfig.ON) {
                Log.e(TAG, "不能创建一个纹理对象");
            }
            return new int[]{0,0,0};
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // Read in the resource
        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        if (bitmap == null) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Resource ID " + resourceId + " could not be decoded.");
            }

            glDeleteTextures(1, textureObjectIds, 0);
            return new int[]{0,0,0};
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // Bind to the texture in OpenGL
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);
        // Set filtering: a default must be set, or the texture will be
        // black. 纹理过滤
        // 缩小时 使用三线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        // 放大时使用 双线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // 加载位图到opengl
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        // 生成MIP贴图
        glGenerateMipmap(GL_TEXTURE_2D);

        // Recycle the bitmap, since its data has been loaded into
        // OpenGL.
        bitmap.recycle();

        // Unbind from the texture.
        glBindTexture(GL_TEXTURE_2D, 0);

        return new int[]{textureObjectIds[0],width,height};
    }
}
