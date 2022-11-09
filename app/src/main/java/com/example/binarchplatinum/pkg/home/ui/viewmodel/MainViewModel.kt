package com.example.binarchplatinum.pkg.home.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binarchplatinum.data.repository.LocalRepository
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.CountAndSumExpenses
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: LocalRepository) : ViewModel() {
    val expenseResult = MutableLiveData<Resource<List<ExpensesWithCategory>>>()
    val categoryWithExpensesResult = MutableLiveData<Resource<List<CategoryWithExpenses>>>()
    val totalItemAndPrice = MutableLiveData<Resource<CountAndSumExpenses>>()
    val deleteResult = MutableLiveData<Resource<Number>>()

    fun getAllExpense() {
        viewModelScope.launch(Dispatchers.IO) {
            expenseResult.postValue(repository.getAllExpenses())
        }
    }

    fun getAllCategoryWithExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryWithExpensesResult.postValue(repository.getAllCategoryWithExpenses())
        }
    }

    fun countAndSumExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            totalItemAndPrice.postValue(repository.countAndSumExpenses())
        }
    }

    fun deleteExpenseById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteResult.postValue(repository.deleteExpenseById(id))
        }
    }
}