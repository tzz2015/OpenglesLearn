package com.example.opengleslearn.data

interface IVideoAnimation {

    fun setAlpha(alpha: Float)

    fun setProgress(progress: Float)

    fun easeProgress(progress: Float, speedUp: Boolean): Float
}