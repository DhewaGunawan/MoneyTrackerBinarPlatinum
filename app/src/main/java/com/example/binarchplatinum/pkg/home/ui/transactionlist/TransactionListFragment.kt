package com.example.binarchplatinum.pkg.home.ui.transactionlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.binarchplatinum.MainViewModel
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.constant.ExpenseConstant
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.databinding.FragmentTransactionListBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.pkg.home.ui.adapter.GroupTransactionListAdapter
import com.example.binarchplatinum.pkg.home.ui.adapter.TransactionListAdapter
import com.example.binarchplatinum.wrapper.Resource

class TransactionListFragment(transactionType: String, allTransaction: String) : Fragment() {
    private lateinit var binding: FragmentTransactionListBinding

    private var type = transactionType
    private var name = allTransaction

    private val singleListadapter: TransactionListAdapter by lazy {
        TransactionListAdapter()
    }
    private val groupListadapter: GroupTransactionListAdapter by lazy {
        GroupTransactionListAdapter()
    }

//    private var type: String? = null

    private val viewModel: MainViewModel by lazy {
        GenericViewModelFactory(MainViewModel(ServiceLocator.provideLocalRepository(requireContext()))).create(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        type = arguments?.getString(CommonConstant.TRANSACTION_TYPE)

        initData(name)
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun observeData() {
        viewModel.categoryWithExpensesResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                    println("Error ${it.message}")
                }
                is Resource.Loading -> {
                    // do nothing for now
                    println("Loading")
                }
                is Resource.Success -> {
                    showGroupedTransactionList(it.data)
                }
                else -> {}
            }
        }
        viewModel.expenseResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                    println("Error ${it.message}")
                }
                is Resource.Loading -> {
                    // do nothing for now
                    println("Loading")
                }
                is Resource.Success -> {
                    showTransactionList(it.data)
                }
                else -> {}
            }
        }
        viewModel.totalItemAndPrice.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                    println("Error ${it.message}")
                }
                is Resource.Loading -> {
                    // do nothing for now
                    println("Loading")
                }
                is Resource.Success -> {
                    showTotalTransaction(it.data?.count)
                    groupListadapter.getTotalItemCount(it.data)
                }
                else -> {}
            }
        }
    }

    private fun initData(name: String?) {
        Log.d("Testing", "name $name & ${ExpenseConstant.ALL_TRANSACTION_WITH_CATEGORY}")
        if (name == ExpenseConstant.ALL_TRANSACTION_WITH_CATEGORY) {
            viewModel.getAllCategoryWithExpenses()
            viewModel.countAndSumExpenses()
        } else {
            viewModel.getAllExpense()
        }
    }

    private fun initRecyclerView() {
        if (name == ExpenseConstant.ALL_TRANSACTION_WITH_CATEGORY) {
            binding.rvPeople.adapter = this@TransactionListFragment.groupListadapter
        } else {
            binding.rvPeople.adapter = this@TransactionListFragment.singleListadapter
        }
    }

    private fun showTransactionList(data: List<ExpensesWithCategory>?) {
        data?.let { listData ->
            if (listData.isNotEmpty()) {
                singleListadapter.setData(listData)
                showTotalTransaction(singleListadapter.itemCount)
            }
        }
    }

    private fun showGroupedTransactionList(data: List<CategoryWithExpenses>?) {
        data?.let { listData ->
            if (listData.isNotEmpty()) {
                groupListadapter.setGroupedData(listData)
//                groupListadapter.getTotalItemCount()
            }
        }
    }

    private fun showTotalTransaction(totalTransaction: Int?) {
        totalTransaction?.let {
            binding.tvTotalTransaction.text = "$it items"
        }
    }
}