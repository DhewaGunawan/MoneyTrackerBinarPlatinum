package com.example.binarchplatinum.pkg.home.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binarchplatinum.constant.CommonConstant
import com.example.binarchplatinum.data.repository.LocalRepository
import com.example.binarchplatinum.data.room.entity.Category
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.ExpenseWithCategory
import com.example.binarchplatinum.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


typealias InitialDataResultType = Resource<Triple<ExpenseWithCategory?, List<Category>, Int>>

class CustomDialogAddViewModel(private val repository: LocalRepository) : ViewModel() {

    var expenseId: Int = CommonConstant.UNSET_ID
    var selectedCategoryId: Int = CommonConstant.UNSET_ID

    val initialDataResult = MutableLiveData<InitialDataResultType>()

    val insertResult = MutableLiveData<Resource<Number>>()
   /* val deleteResult = MutableLiveData<Resource<Number>>()
    val updateResult = MutableLiveData<Resource<Number>>()*/

    fun getInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            initialDataResult.postValue(Resource.Loading())
            delay(1000)
            val expense = repository.getExpenseById(expenseId).data
            val categories = repository.getAllCategories().data.orEmpty()
            val selectedCategory = expense?.category
            val selectedCategoryIdx = categories.indexOf(selectedCategory)
            selectedCategoryId = selectedCategory?.categoryId ?: CommonConstant.UNSET_ID

            initialDataResult.postValue(
                Resource.Success(
                    Triple(
                        expense,
                        categories,
                        selectedCategoryIdx
                    )
                )
            )
        }
    }


    fun insertNote(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)
            insertResult.postValue(repository.insertExpense(expenses))
        }
    }


   /* fun deleteNote(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteResult.postValue(Resource.Loading())
            delay(1000)
            deleteResult.postValue(repository.deleteExpense(expenses))
        }
    }

    fun updateNote(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            updateResult.postValue(Resource.Loading())
            delay(1000)
            updateResult.postValue(repository.updateExpense(expenses))
        }
    }*/

    fun setIntentData(intent: Intent) {
        expenseId = intent.getIntExtra(CommonConstant.EXTRAS_ID_EXPENSE, CommonConstant.UNSET_ID)
    }
}