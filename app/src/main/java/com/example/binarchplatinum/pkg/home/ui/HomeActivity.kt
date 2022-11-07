package com.example.binarchplatinum.pkg.home.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.binarchplatinum.R
import com.example.binarchplatinum.base.BaseActivity
import com.example.binarchplatinum.databinding.ActivityHomeBinding
import com.example.binarchplatinum.pkg.home.ui.adapter.HomeViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    companion object {
        private const val TAG = "HomeActivity"
    }

    private var _viewPagerAdapter: HomeViewPagerAdapter? = null
    private val viewPagerAdapter get() = _viewPagerAdapter!!

    private val addExpenseBottomDialog by lazy {
        CustomDialogAdd()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        binding.apply {
            includeToolbar.apply {
                //TODO: TEXT FETCH FROM LOCAL STORAGE
                titleName.text = "Keanu"
                btnLogout.setOnClickListener {
                    //TODO: GO TO INPUT USERNAME MENU
                    Toast.makeText(this@HomeActivity, "STILL IN PROGRESS", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            includeListFilter.apply {
                _viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, lifecycle)
                viewPager.adapter = viewPagerAdapter
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text =
                        when (position) {
                            0 -> getString(R.string.today)
                            1 -> getString(R.string.this_week)
                            2 -> getString(R.string.this_month)
                            else -> ({
                            }).toString()}
                }.attach()
            }

            includeBottomBtn.apply {
                cvAddData.setOnClickListener {
                    //TODO: OPEN CUSTOM DIALOG
                    addExpenseBottomDialog.show(
                        supportFragmentManager,
                        addExpenseBottomDialog.tag
                    )
                }
            }
        }
    }
}