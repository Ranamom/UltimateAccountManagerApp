package com.example.ultimateaccountmanager.util

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.R
import kotlin.random.Random

class AnimationUtil {

	var imageAnimationDuration: Long = 3 * 1000 //3 secs
	var textLogoAnimationDuration: Double = 1.5 * 1000 //1.5 secs

	val valueAnimator = ValueAnimator.ofFloat()
	var animationValue: Float = 0.0f
	var stopSpamminThatShitBro: Boolean = false

	var currentPlayTime: Long = 0
	var currentTotalTime: Long = 0

	var screenHeight: Float = 0.0f

    fun generateImageSplash(imageView: ImageView, context: Context) {

        val arr = listOf(
            R.drawable.animoutfit,
            R.drawable.animoutfit1,
            R.drawable.animoutfit2,
            R.drawable.animoutfit3,
            R.drawable.animoutfit4
        )

        /**
         * Image generated with
         * https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=${random}&direction=${direction}
         *
         * mount = Mount  (Works from 1 to 142)
         * direction = Direction where character was looking
         * 1 /\
         * 2 >
         * 3 \/
         * 4 <
         */
        Glide
            .with(context)
            .load(arr[Random.nextInt(0, arr.size)])
            .into(imageView)
    }

    fun splashAnimateBackground(repeat: Int = 1, view: View, timeout: Long, context: Context) {

        val objectAnimator = ObjectAnimator.ofObject(
            view,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(
                context,
                R.color.colorAccent
            ),
            ContextCompat.getColor(
                context,
                R.color.colorPrimaryDark
            )
        )

//        objectAnimator.repeatCount = repeat
//        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = timeout / repeat

        objectAnimator.start()
    }


    fun fadeInGenerator(duration: Double = 1200.0): AlphaAnimation {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = duration.toLong()
        fadeIn.fillAfter = true

        return fadeIn
    }

    fun saveAnimationStates(outState: Bundle) {
        outState.putFloat("animatedValue", animationValue)
        outState.putLong("currentPlayTime", valueAnimator.currentPlayTime)
        outState.putBoolean("stopSpamminThatShitBro", stopSpamminThatShitBro)
        outState.putFloat("screenHeight", screenHeight)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            outState.putLong("currentTotalTime", valueAnimator.totalDuration)
        } else {
            if (currentPlayTime.toInt() != 0 && currentTotalTime.toInt() != 0) {
                outState.putLong("currentTotalTime", currentTotalTime - currentPlayTime)
            } else {
                outState.putLong(
                    "currentTotalTime",
                    imageAnimationDuration - valueAnimator.currentPlayTime
                )
            }
        }

    }

    fun splashLogoAnimation(
        startScreen: Float,
        finishScreen: Float,
        duration: Long,
        logoImgToAnimate: ImageView,
        logoTextToAnimate: TextView
    ) {

        valueAnimator.setFloatValues(startScreen, finishScreen)

	    /** Center image */
        logoImgToAnimate.translationX = -65f

        valueAnimator.addUpdateListener {
            animationValue = it.animatedValue as Float
            logoImgToAnimate.translationY = animationValue
        }

        valueAnimator.setupStartValues()

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (duration - 100)

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                stopSpamminThatShitBro = true
                logoTextToAnimate.startAnimation(fadeInGenerator(textLogoAnimationDuration))
                logoTextToAnimate.alpha = 1f
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        valueAnimator.start()
    }

}