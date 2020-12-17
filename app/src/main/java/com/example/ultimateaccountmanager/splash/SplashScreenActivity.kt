package com.example.ultimateaccountmanager.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.ultimateaccountmanager.AccountDetails
import com.example.ultimateaccountmanager.MainActivity
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.repository.AppRepository
import com.example.ultimateaccountmanager.util.AnimationUtil
import com.example.ultimateaccountmanager.util.SharedPreference
import kotlinx.android.synthetic.main.activity_splash_screen.*
import timber.log.Timber

class SplashScreenActivity : AppCompatActivity() {

    private var timeoutSplashScreen: Long = 3000 //1000 = 1 Segundo

    private val animationUtil = AnimationUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timber.plant(Timber.DebugTree())

        /** Generate image with Glide */
        animationUtil.generateImageSplash(spl_logo_img, applicationContext)
        animationUtil.imageAnimationDuration = 1500
        animationUtil.textLogoAnimationDuration = 800.0

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        animationUtil.screenHeight = displayMetrics.heightPixels.toFloat()

        if (savedInstanceState == null) {
            animationUtil.splashLogoAnimation(
                -500f,
                animationUtil.screenHeight,
                animationUtil.imageAnimationDuration,
                spl_logo_img,
                spl_logo_name
            )
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, timeoutSplashScreen)
        } else {
            /** Get saved values from savedInstanceState */
            animationUtil.currentPlayTime = savedInstanceState.getLong("currentPlayTime")
            animationUtil.currentTotalTime = savedInstanceState.getLong("currentTotalTime")
            val animatedValue = savedInstanceState.getFloat("animatedValue")
            val screenHeightAfter = savedInstanceState.getFloat("screenHeight")

            /** Proporcion to make animate 'responsive' when app rotate*/
            val animatinScreenProporcion =
                ((animatedValue) / (screenHeightAfter / animationUtil.screenHeight))

            if (!savedInstanceState.getBoolean("stopSpamminThatShitBro")) {
                animationUtil.splashLogoAnimation(
                    animatinScreenProporcion,
                    animationUtil.screenHeight,
                    (animationUtil.currentTotalTime - animationUtil.currentPlayTime),
                    spl_logo_img,
                    spl_logo_name
                )
            } else {
                spl_logo_img.alpha = 0f
                spl_logo_name.alpha = 1f
                animationUtil.stopSpamminThatShitBro = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        animationUtil.saveAnimationStates(outState)
    }
}