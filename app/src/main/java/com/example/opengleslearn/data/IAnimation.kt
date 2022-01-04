package com.example.opengleslearn.data

interface IAnimation {

    fun setAlpha(alpha: Float)

    fun setProgress(progress: Float)

    fun easeProgress(progress: Float, speedUp: Boolean): Float
}