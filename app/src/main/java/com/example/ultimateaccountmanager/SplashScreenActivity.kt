package com.example.ultimateaccountmanager

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    private val TIMEOUT_SPLASH_SCREEN: Long = 9000 //3 Segundos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Glide
            .with(this)
            .load("https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=92&direction=3")
            .into(spl_logo_img)

        generateSplashAnimation()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, TIMEOUT_SPLASH_SCREEN)
    }

    fun generateSplashAnimation() {
        splashLogoAnimation(TIMEOUT_SPLASH_SCREEN / 2)
        splashAnimateBackground()
    }


    fun splashAnimateBackground(repeat: Int = 9, timeout: Long = TIMEOUT_SPLASH_SCREEN) {

        val objectAnimator = ObjectAnimator.ofObject(
            layout_splash,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(this, R.color.colorAccent),
            ContextCompat.getColor(this, R.color.colorPrimary),
            ContextCompat.getColor(this, R.color.colorPrimaryDark)
//            ContextCompat.getDrawable(this, R.drawable.teste)
        )

        objectAnimator.repeatCount = repeat
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = timeout / repeat

        objectAnimator.start()
    }

    fun splashLogoAnimation(timer: Long, reverse: Boolean = false) {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        val valueAnimator = ValueAnimator.ofFloat(-500f, screenHeight.toFloat())
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            spl_logo_img.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (timer - 100)

        if (reverse) {
            Glide
                .with(this)
                .load("https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=92&direction=1")
                .into(spl_logo_img)
//            spl_logo_img.rotationX = 180f
            valueAnimator.reverse()
        } else {
            valueAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    splashLogoAnimation(TIMEOUT_SPLASH_SCREEN / 2, true)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }
            })
            valueAnimator.start()
        }
    }
}