package com.example.binarchplatinum.pkg.home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.binarchplatinum.pkg.home.ui.HomeAllExpensesFragment
import com.example.binarchplatinum.pkg.home.ui.HomeCategoryFragment

class HomeViewPagerAdapter(fa: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fa, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeAllExpensesFragment()
            1 -> HomeCategoryFragment()
            else -> HomeAllExpensesFragment()
        }
    }
}