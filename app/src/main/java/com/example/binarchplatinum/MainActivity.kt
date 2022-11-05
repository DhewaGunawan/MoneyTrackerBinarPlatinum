package com.example.binarchplatinum

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.binarchplatinum.base.BaseActivity
import com.example.binarchplatinum.databinding.ActivityMainBinding
import com.example.binarchplatinum.ui.transactionlist.TransactionListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        initTransactionListFragment()
    }

    private fun initTransactionListFragment() {
        val transactionListFragment = TransactionListFragment()
        val transactionListFragmentManager: FragmentManager = supportFragmentManager
        val transactionListFragmentTransaction: FragmentTransaction =
            transactionListFragmentManager
                .beginTransaction()
        transactionListFragmentTransaction.replace(
            R.id.cl_transaction_list,
            transactionListFragment
        ).commit()
    }

}
