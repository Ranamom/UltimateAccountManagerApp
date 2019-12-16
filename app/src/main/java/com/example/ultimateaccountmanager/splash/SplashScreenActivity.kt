package com.example.ultimateaccountmanager.splash

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.MainActivity
import com.example.ultimateaccountmanager.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlin.random.Random


class SplashScreenActivity : AppCompatActivity() {

    private val timeoutSplashScreen: Long = 6000 //3 Segundos
    private val random = Random.nextInt(1, 100)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        spl_logo_name.alpha = 0f
        generateSplashAnimation()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, timeoutSplashScreen)
    }

    fun fadeInGenarate(duration: Long): AlphaAnimation {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = duration

        return fadeIn
    }

    fun generateSplashAnimation() {
        splashLogoAnimation(timeoutSplashScreen / 2)
        splashAnimateBackground()
    }


    fun generateImageSplash(random: Int, direction: Int = 3, imageView: ImageView = spl_logo_img) {
        Glide
            .with(this)
            .load("https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=${random}&direction=${direction}")
            .into(imageView)
    }


    fun splashAnimateBackground(repeat: Int = 1, timeout: Long = timeoutSplashScreen) {

        val objectAnimator = ObjectAnimator.ofObject(
            layout_splash,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            ),
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            ),
            ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )
        )

        objectAnimator.repeatCount = repeat
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = timeout / repeat

        objectAnimator.start()
    }

    fun splashLogoAnimation(timer: Long, reverse: Boolean = false) {

        val animation = AnimationSet(false)
        animation.addAnimation(fadeInGenarate(2000))
        spl_logo_img.animation = animation

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
//        val screenWith = displayMetrics.widthPixels

        val valueAnimator = ValueAnimator.ofFloat(-screenHeight.toFloat(), screenHeight.toFloat())
//        val valueAnimator = ValueAnimator.ofFloat(-screenWith.toFloat(), screenWith.toFloat())
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            spl_logo_img.translationY = value
//            spl_logo_img.translationX = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (timer - 100)

        if (reverse) {
            generateImageSplash(random, 4)
            valueAnimator.reverse()
        } else {
            valueAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    //TODO ADD ANIMATION LOGO
                    val animateLogo = AnimationSet(false)
                    animateLogo.addAnimation(fadeInGenarate(1000))
                    spl_logo_name.alpha = 1f
                    spl_logo_name.animation = animateLogo

                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }
            })
            generateImageSplash(random)
            valueAnimator.start()
        }
    }
}