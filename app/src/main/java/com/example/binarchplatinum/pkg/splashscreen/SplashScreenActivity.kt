package com.example.binarchplatinum.pkg.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.binarchplatinum.R
import com.example.binarchplatinum.pkg.home.data.localpref.UserPreference
import com.example.binarchplatinum.pkg.home.ui.HomeActivity
import com.example.binarchplatinum.pkg.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SplashScreenActivity"
    }

    private var timer: CountDownTimer? = null
    private val preference: UserPreference by lazy {
        UserPreference(this@SplashScreenActivity)
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
                Log.d(TAG, "onFinish: ${preference.isSkipIntro()}")
                val destination = if (preference.isSkipIntro()) HomeActivity::class.java else OnboardingActivity::class.java
                val intent = Intent(this@SplashScreenActivity, destination)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        timer?.start()
    }


}