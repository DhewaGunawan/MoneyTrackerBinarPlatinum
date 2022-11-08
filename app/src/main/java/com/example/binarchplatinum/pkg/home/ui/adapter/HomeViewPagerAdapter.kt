package com.example.binarchplatinum.pkg.home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.binarchplatinum.constant.CommonConstant
import com.example.binarchplatinum.constant.ExpenseConstant
import com.example.binarchplatinum.pkg.home.ui.transactionlist.TransactionListFragment

class HomeViewPagerAdapter(fa: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fa, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransactionListFragment(CommonConstant.TRANSACTION_TYPE, ExpenseConstant.ALL_TRANSACTION)
            1 -> TransactionListFragment(CommonConstant.TRANSACTION_TYPE, ExpenseConstant.ALL_TRANSACTION_WITH_CATEGORY)
            else -> TransactionListFragment(CommonConstant.TRANSACTION_TYPE, ExpenseConstant.ALL_TRANSACTION)
        }
    }
}