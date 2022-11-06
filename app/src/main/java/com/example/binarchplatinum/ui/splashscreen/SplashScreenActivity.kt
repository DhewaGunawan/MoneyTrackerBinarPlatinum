package com.example.binarchplatinum.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.binarchplatinum.MainActivity
import com.example.binarchplatinum.R
import com.example.binarchplatinum.ui.onboarding.OnboardingActivity

class SplashScreenActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private val preference: UserPreference by lazy {
        UserPreference(this@OnboardingActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        setTimerSplashScreen()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    private fun setTimerSplashScreen() {
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                val destination = if (preference.isSkipIntro()) MainActivity::class.java else OnboardingActivity::class.java
                val intent = Intent(this@SplashScreenActivity, destination)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        timer?.start()
    }


}