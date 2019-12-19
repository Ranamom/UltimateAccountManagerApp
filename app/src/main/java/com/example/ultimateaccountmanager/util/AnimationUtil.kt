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

class AnimationUtil {

    var imageAnimationDuration: Long = 3000 //3 Segundos
    var textLogoAnimationDuration: Long = 1500 //1.5 Segundos

    val valueAnimator = ValueAnimator.ofFloat()
    var animationValue: Float = 0.0f
    var stopSpamminThatShitBro: Boolean = false

    var currentPlayTime: Long = 0
    var currentTotalTime: Long = 0

    var screenHeight: Float = 0.0f

    fun generateImageSplash(imageView: ImageView, context: Context) {

        /**
         * Imagem gerada a partir do link
         * https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=${random}&direction=${direction}
         *
         * mount = Montaria  (Funciona de 1 à 142)
         * direction = Direção que o personagem está olhando
         * 1 /\
         * 2 >
         * 3 \/
         * 4 <
         */
        Glide
            .with(context)
            .load(R.drawable.animoutfit1)
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


    fun fadeInGenerator(duration: Long = 1200): AlphaAnimation {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = duration
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

        /** Centralizar um pouco a imagem */
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