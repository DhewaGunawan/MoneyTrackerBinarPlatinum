package com.example.binarchplatinum.pkg.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.binarchplatinum.R
import com.example.binarchplatinum.data.localpref.UserPreference
import com.example.binarchplatinum.pkg.home.ui.HomeActivity
import com.example.binarchplatinum.pkg.login.LoginActivity
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment

class OnboardingActivity : AppIntro2() {
    companion object {
        private const val TAG = "OnboardingActivity"
    }

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
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.light_grey),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.super_dark_grey)
        )

        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.text_splash_desc_1),
                imageDrawable = R.drawable.ic_splash_image_1,
                descriptionTypefaceFontRes = R.font.inter_medium,
                backgroundColorRes = R.color.color_primary,
                descriptionColorRes = R.color.light_grey
            )
        )

        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.text_splash_desc_2),
                imageDrawable = R.drawable.ic_splash_image_2,
                descriptionTypefaceFontRes = R.font.inter_medium,
                backgroundColorRes = R.color.color_primary,
                descriptionColorRes = R.color.light_grey
            )
        )

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        preference.setSkipIntro(true)
        Intent(this@OnboardingActivity, LoginActivity::class.java).also {
            startActivity(it)
        }

    }


}