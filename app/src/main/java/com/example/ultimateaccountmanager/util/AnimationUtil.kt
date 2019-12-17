package com.example.ultimateaccountmanager.util

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class AnimationUtil {

    val valueAnimator = ValueAnimator.ofFloat()
    var animationValue: Float = 0.0f
    var stopSpamminThatShitBro: Boolean = false

    fun generateImageSplash(imageView: ImageView, context: Context) {

        /**
         * Imagem gerada a partir do link
         * https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=${random}&direction=${direction}
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

    fun splashLogoAnimation(startScreen: Float, finishScreen: Float, duration: Long, logoImg : ImageView, logoText : TextView, logoAnimationDuration: Long) {

        valueAnimator.setFloatValues(startScreen, finishScreen)

        /** Centralizar um pouco a imagem */
        logoImg.translationX = -65f

        valueAnimator.addUpdateListener {
            animationValue = it.animatedValue as Float
            logoImg.translationY = animationValue
        }

        valueAnimator.setupStartValues()

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (duration - 100)

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                stopSpamminThatShitBro = true
                logoText.startAnimation(fadeInGenerator(logoAnimationDuration))
                logoText.alpha = 1f
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        valueAnimator.start()
    }

}