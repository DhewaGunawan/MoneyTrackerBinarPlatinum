package com.example.binarchplatinum

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.binarchplatinum.base.BaseActivity
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.constant.CommonConstant
import com.example.binarchplatinum.constant.ExpenseConstant
import com.example.binarchplatinum.databinding.ActivityMainBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.ui.transactionlist.TransactionListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        setMenuClickListener()
        initTransactionListFragment(ExpenseConstant.ALL_TRANSACTION)
    }

    private fun initTransactionListFragment(transactionType: String) {
        val transactionListFragment = TransactionListFragment()

        val bundle = Bundle()
        bundle.putString(CommonConstant.TRANSACTION_TYPE, transactionType)
        transactionListFragment.arguments = bundle

        val transactionListFragmentManager: FragmentManager = supportFragmentManager
        val transactionListFragmentTransaction: FragmentTransaction =
            transactionListFragmentManager
                .beginTransaction()
        transactionListFragmentTransaction.replace(
            R.id.cl_transaction_list,
            transactionListFragment
        ).commit()
    }

    private fun setMenuClickListener() {
        binding.tvAllTransaction.setOnClickListener {
            binding.tvAllTransaction.setTextColor(ContextCompat.getColor(this, R.color.off_white_400))
            binding.tvByCategory.setTextColor(ContextCompat.getColor(this, R.color.grey_400))
            initTransactionListFragment(ExpenseConstant.ALL_TRANSACTION)
        }
        binding.tvByCategory.setOnClickListener {
            binding.tvAllTransaction.setTextColor(ContextCompat.getColor(this, R.color.grey_400))
            binding.tvByCategory.setTextColor(ContextCompat.getColor(this, R.color.off_white_400))
            initTransactionListFragment(ExpenseConstant.ALL_TRANSACTION_WITH_CATEGORY)
        }
    }

}
