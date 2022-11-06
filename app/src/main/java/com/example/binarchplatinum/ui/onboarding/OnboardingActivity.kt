package com.example.binarchplatinum.ui.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.binarchplatinum.MainActivity
import com.example.binarchplatinum.R
import com.example.binarchplatinum.ui.login.LoginActivity
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment

class OnboardingActivity: AppIntro2() {
    private val preference: UserPreference by lazy {
        UserPreference(this@OnboardingActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setupSliderFragment()
    }

    private fun setupSliderFragment() {
        isSkipButtonEnabled = false

        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this,R.color.off_white_400),
            unselectedIndicatorColor = ContextCompat.getColor(this,R.color.grey_600)
        )

        addSlide(AppIntroFragment.createInstance(
            description = getString(R.string.text_splash_desc_1),
            imageDrawable = R.drawable.ic_splash_image_1,
            descriptionTypefaceFontRes = R.font.inter_medium,
            backgroundColorRes = R.color.dark_blue_600,
            descriptionColorRes = R.color.off_white_400
        ))

        addSlide(AppIntroFragment.createInstance(
            description = getString(R.string.text_splash_desc_2),
            imageDrawable = R.drawable.ic_splash_image_2,
            descriptionTypefaceFontRes = R.font.inter_medium,
            backgroundColorRes = R.color.dark_blue_600,
            descriptionColorRes = R.color.off_white_400
        ))

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        if (preference.isSkipIntro()) {
            Intent(this@OnboardingActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        } else {
            Intent(this@OnboardingActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}