package com.example.opengleslearn.util;

import android.opengl.Matrix;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class Matrix3DUtils {
    private final static String TAG = "Matrix3DUtils";
    public final static float[] IDENTITY = new float[16];
    private final static float[] S_TEMP = new float[16];

    static {
        Matrix.setIdentityM(IDENTITY, 0);
    }

    public static void dumpMatrix(String matrixName, float[] matrix, int stride) {
        Log.e(TAG, matrixName + "Matrix:-----");
        for (int index = 0; index < stride; index++) {
            Log.e(TAG, "Matrix:" + Arrays.toString(Arrays.copyOfRange(matrix, index * stride, (index + 1) * stride)));
        }
    }


    /**
     * 根据model, view和project矩阵，生成MVP矩阵
     * mvp = m * v * p
     *
     * @return mvp
     */
    public static float[] createMvpMatrix(float[] m, float[] v, float[] p) {
        float[] mvp = new float[16];
        Matrix.multiplyMM(mvp, 0, v, 0, m, 0);
        Matrix.multiplyMM(mvp, 0, p, 0, mvp, 0);
        return mvp;
    }

    public static void preScaleM(float[] m, float x, float y, float z) {
        float[] scaleMatrix = new float[16];
        float[] resultMatrix = new float[16];
        Matrix.setIdentityM(scaleMatrix, 0);
        Matrix.scaleM(scaleMatrix, 0, x, y, z);
        Matrix.multiplyMM(resultMatrix, 0, scaleMatrix, 0, m, 0);
        System.arraycopy(resultMatrix, 0, m, 0, m.length);
    }

    public static void preTranslateM(float[] m, float x, float y, float z) {
        float[] translateMatrix = new float[16];
        float[] resultMatrix = new float[16];
        Matrix.setIdentityM(translateMatrix, 0);
        Matrix.translateM(translateMatrix, 0, x, y, z);
        Matrix.multiplyMM(resultMatrix, 0, translateMatrix, 0, m, 0);
        System.arraycopy(resultMatrix, 0, m, 0, m.length);
    }

    public static void preRotateM(float[] m, float a, float x, float y, float z) {
        float[] rotateMatrix = new float[16];
        float[] resultMatrix = new float[16];
        Matrix.setIdentityM(rotateMatrix, 0);
        Matrix.rotateM(rotateMatrix, 0, a, x, y, z);
        Matrix.multiplyMM(resultMatrix, 0, rotateMatrix, 0, m, 0);
        System.arraycopy(resultMatrix, 0, m, 0, m.length);
    }

    public static void multiplyMM(float[] result, float[] lhs, float[] rhs) {
        /*
         * The same float array may be passed for result, lhs, and/or rhs. However,
         * the result element values are undefined if the result elements overlap
         * either the lhs or rhs elements.
         * 可以将相同的float数组传递给result，lhs和/或rhs。
         * 但是，如果结果元素与lhs或rhs元素重叠，则结果元素的值不确定。
         */
        synchronized (S_TEMP) {
            Matrix.multiplyMM(S_TEMP, 0, lhs, 0, rhs, 0);
            copyM(S_TEMP, result);
        }
    }

    /**
     * @param m   转换矩阵
     * @param src 2维数组（x, y) 原始点位置
     * @param dst 2维数组（x, y) 目标点位置
     */
    public static void mapPoint(float[] m, float[] src, float[] dst) {
        float[] srcVec = new float[]{src[0], src[1], 0f, 1f};
        float[] dstVec = new float[4];

        Matrix.multiplyMV(dstVec, 0, m, 0, srcVec, 0);
        dst[0] = dstVec[0];
        dst[1] = dstVec[1];
    }

    public static float[] mapPoint(@Nullable List<float[]> orgVecs, @NonNull float[] m) {
        float[] curVec = new float[2];
        float[] dstVec = new float[8];

        // jp.co.cyberagent.android.gpuimage.util.GLConstants.CUBE
        // 需要跟上面这个类一致
        if (orgVecs == null) {
            orgVecs = Arrays.asList(
                    new float[]{-1.0f, 1.0f},
                    new float[]{1.0f, 1.0f},
                    new float[]{-1.0f, -1.0f},
                    new float[]{1.0f, -1.0f});
        }

        for (int index = 0; index < orgVecs.size(); index++) {
            float[] orgPosition = orgVecs.get(index);
            mapPoint(m, orgPosition, curVec);

            dstVec[index * 2] = curVec[0];
            dstVec[index * 2 + 1] = curVec[1];
        }

        return dstVec;
    }

    public static float pxConvertNormalizedX(float x, float width) {
        return x / width * 2 - 1;
    }

    public static float pxConvertNormalizedY(float y, float height) {
        return -(y / height * 2 - 1);
    }

    public static float normalizedXConvertPx(float normalizedX, float width) {
        return (normalizedX + 1) / 2 * width;
    }

    public static float normalizedYConvertPy(float normalizedY, float height) {
        return (1 - normalizedY) / 2 * height;
    }

    public static void setIdentityM(float[] m) {
        Matrix.setIdentityM(m, 0);
    }

    public static void copyM(float[] src, float[] dest) {
        System.arraycopy(src, 0, dest, 0, src.length);
    }
}