package com.example.ultimateaccountmanager.splash

import android.animation.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.MainActivity
import com.example.ultimateaccountmanager.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import timber.log.Timber

class SplashScreenActivity : AppCompatActivity() {

    private val timeoutSplashScreen: Long = 6000 //5 Segundos
    private val imageAnimatorDuration: Long = 3000 //3 Segundos
    private val logoAnimationDuration: Long = 1500 //1.5 Segundos


    private val valueAnimator = ValueAnimator.ofFloat()

    private var currentPlayTime: Long = 0
    private var currentTotalTime: Long = 0

    private var stopSpamminThatShitBro: Boolean = false

    private var screenHeight: Float = 0.0f

    var animationValue: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timber.plant(Timber.DebugTree())

        /** Generate image with Glide */
        generateImageSplash()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()

        Timber.i("onCreate Called. ---> ${screenHeight} ")

        if (savedInstanceState == null) {
            splashLogoAnimation(-500f, screenHeight, imageAnimatorDuration)
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
            }, timeoutSplashScreen)
        } else {
            currentPlayTime = savedInstanceState.getLong("currentPlayTime")
            currentTotalTime = savedInstanceState.getLong("currentTotalTime")
            val animatedValue = savedInstanceState.getFloat("animatedValue")
            val screenHeightAfter = savedInstanceState.getFloat("screenHeight")
            Timber.i("animatedValue >> ${((animatedValue) / (screenHeightAfter / screenHeight))}")
            if (!savedInstanceState.getBoolean("stopSpamminThatShitBro")) {
                splashLogoAnimation(
                    ((animatedValue) / (screenHeightAfter / screenHeight)),
                    screenHeight,
                    (currentTotalTime - currentPlayTime)
                )
            } else {
                spl_logo_img.alpha = 0f
                spl_logo_name.alpha = 1f
                stopSpamminThatShitBro = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
                    imageAnimatorDuration - valueAnimator.currentPlayTime
                )
            }
        }
    }


    private fun generateImageSplash(imageView: ImageView = spl_logo_img) {

        /**
         * Imagem gerada a partir do link
         * https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=${random}&direction=${direction}
         */
        Glide
            .with(this)
            .load(R.drawable.animoutfit1)
            .into(imageView)
    }


    private fun splashAnimateBackground(repeat: Int = 1, timeout: Long = timeoutSplashScreen / 2) {

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

    private fun splashLogoAnimation(startScreen: Float, finishScreen: Float, duration: Long) {

        valueAnimator.setFloatValues(startScreen, finishScreen)

        /**
         * Centralizar um pouco a imagem
         */
        spl_logo_img.translationX = -55f

        valueAnimator.addUpdateListener {
            animationValue = it.animatedValue as Float
            spl_logo_img.translationY = animationValue
        }

        valueAnimator.setupStartValues()

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (duration - 100)

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                stopSpamminThatShitBro = true
                spl_logo_name.startAnimation(fadeInGenerator(logoAnimationDuration))
                spl_logo_name.alpha = 1f
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        valueAnimator.start()
    }
}