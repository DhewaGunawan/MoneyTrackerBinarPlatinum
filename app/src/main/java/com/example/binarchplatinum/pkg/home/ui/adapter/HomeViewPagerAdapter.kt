package com.example.binarchplatinum.pkg.home.ui.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.binarchplatinum.pkg.home.ui.HomeDayFragment
import com.example.binarchplatinum.pkg.home.ui.HomeMonthFragment
import com.example.binarchplatinum.pkg.home.ui.HomeWeekFragment

class HomeViewPagerAdapter(fa: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fa, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeDayFragment()
            1 -> HomeWeekFragment()
            2 -> HomeMonthFragment()
            else -> HomeDayFragment()
        }
    }
}