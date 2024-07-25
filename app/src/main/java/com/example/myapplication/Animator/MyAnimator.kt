package com.example.myapplication.Animator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView

class MyAnimator{
    fun setAnimator(imageView: ImageView) : AnimatorSet {
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f)
        rotateAnimator.setDuration(30000)
        rotateAnimator.setInterpolator(LinearInterpolator())
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE)
        val animator_rotate = AnimatorSet()
        animator_rotate.playTogether(rotateAnimator)
        return animator_rotate
    }

    fun likeaddAnimator(imageView: ImageView) : AnimatorSet{
        // 点击放大动画
        val scaleUpX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.2f, 1.0f)
        val scaleUpY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.2f, 1.0f)
        scaleUpX.setDuration(1000)
        scaleUpY.setDuration(1000)
        val rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f)
        rotateY.setDuration(1000)
        val scaleUp = AnimatorSet()
        scaleUp.play(scaleUpX).with(scaleUpY).with(rotateY)
        return scaleUp
    }

    fun likeremoveAnimator(imageView: ImageView) :AnimatorSet{
        val scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.8f, 1.0f)
        val scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.8f, 1.0f)
        scaleDownX.setDuration(1000)
        scaleDownY.setDuration(1000)
        val scaleDown = AnimatorSet()
        scaleDown.play(scaleDownX).with(scaleDownY)
//        scaleDown.start()
        return scaleDown
    }


    fun musicAnimator(imageView: ImageView){
        val scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.2f, 1.0f)
        val scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.2f, 1.0f)
        // 创建一个沿Y轴旋转360度的动画
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f)
        // 设置动画持续时间
        scaleXAnimator.setDuration(1000)
        scaleYAnimator.setDuration(1000)
        rotateAnimator.setDuration(1000)
        // 创建动画集合，同时播放放大缩小和旋转动画
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, rotateAnimator)
        // 启动动画
        animatorSet.start()
    }
//    fun activityAnimator():AnimatorSet{
//        val slideInAnimation = ObjectAnimator.ofFloat(viewToAnimate, "translationY", -viewToAnimate.height.toFloat(), 0f)
//        slideInAnimation.duration = 300
//
//        val slideOutAnimation = ObjectAnimator.ofFloat(viewToAnimate, "translationY", 0f, viewToAnimate.height.toFloat())
//        slideOutAnimation.duration = 300
//
//        // 创建动画集合并设定顺序
//        val animatorSet = AnimatorSet()
//        animatorSet.play(slideInAnimation).before(slideOutAnimation)
//
//        return animatorSet
//    }
}