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
import com.example.ultimateaccountmanager.util.AnimationUtil
import kotlinx.android.synthetic.main.activity_splash_screen.*
import timber.log.Timber

class SplashScreenActivity : AppCompatActivity() {

    private val timeoutSplashScreen: Long = 5000 //5 Segundos
    private val imageAnimatorDuration: Long = 3000 //3 Segundos
    private val logoAnimationDuration: Long = 1500 //1.5 Segundos

    private val animinUtil = AnimationUtil()

    private var currentPlayTime: Long = 0
    private var currentTotalTime: Long = 0


    private var screenHeight: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timber.plant(Timber.DebugTree())

        /** Generate image with Glide */
        animinUtil.generateImageSplash(spl_logo_img, applicationContext)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()

        Timber.i("onCreate Called. ---> ${screenHeight} ")

        if (savedInstanceState == null) {
            animinUtil.splashLogoAnimation(
                -500f,
                screenHeight,
                imageAnimatorDuration,
                spl_logo_img,
                spl_logo_name,
                logoAnimationDuration
            )
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
                animinUtil.splashLogoAnimation(
                    ((animatedValue) / (screenHeightAfter / screenHeight)),
                    screenHeight,
                    (currentTotalTime - currentPlayTime),
                    spl_logo_img,
                    spl_logo_name,
                    logoAnimationDuration
                )
            } else {
                spl_logo_img.alpha = 0f
                spl_logo_name.alpha = 1f
                animinUtil.stopSpamminThatShitBro = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("animatedValue", animinUtil.animationValue)
        outState.putLong("currentPlayTime", animinUtil.valueAnimator.currentPlayTime)
        outState.putBoolean("stopSpamminThatShitBro", animinUtil.stopSpamminThatShitBro)
        outState.putFloat("screenHeight", screenHeight)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            outState.putLong("currentTotalTime", animinUtil.valueAnimator.totalDuration)
        } else {
            if (currentPlayTime.toInt() != 0 && currentTotalTime.toInt() != 0) {
                outState.putLong("currentTotalTime", currentTotalTime - currentPlayTime)
            } else {
                outState.putLong(
                    "currentTotalTime",
                    imageAnimatorDuration - animinUtil.valueAnimator.currentPlayTime
                )
            }
        }
    }
}