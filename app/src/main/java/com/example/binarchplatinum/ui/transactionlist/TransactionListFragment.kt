package com.example.binarchplatinum.ui.transactionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.binarchplatinum.MainViewModel
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.databinding.FragmentTransactionListBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.ui.transactionlist.adapter.TransactionListAdapter
import com.example.binarchplatinum.wrapper.Resource

class TransactionListFragment : Fragment() {
    private lateinit var binding: FragmentTransactionListBinding

    private val adapter: TransactionListAdapter by lazy {
        TransactionListAdapter()
    }
    private val viewModel: MainViewModel by lazy {
        GenericViewModelFactory(MainViewModel(ServiceLocator.provideLocalRepository(requireContext()))).create(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun observeData() {
        viewModel.expenseResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                }
                is Resource.Loading -> {
                    // do nothing for now
                }
                is Resource.Success -> {
                    showTransactionList(it.data)
                }
            }
        }
    }

    private fun initData() {
        viewModel.getAllExpense()
    }

    private fun initRecyclerView() {
        binding.rvPeople.adapter = this@TransactionListFragment.adapter
    }

    private fun showTransactionList(data: List<ExpensesWithCategory>?) {
        data?.let { listData ->
            if (listData.isNotEmpty()) {
                adapter.setData(listData)
                showTotalTransaction(adapter.itemCount)
            }
        }
    }

    private fun showTotalTransaction(totalTransaction: Int) {
        binding.tvTotalTransaction.text = "${totalTransaction.toString()} items"
    }

}