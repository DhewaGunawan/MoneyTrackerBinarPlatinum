package com.example.binarchplatinum.data.repository

import com.example.binarchplatinum.data.room.datasource.CategoryDataSource
import com.example.binarchplatinum.data.room.datasource.ExpensesDataSource
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.ExpenseWithCategory
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.pkg.home.data.room.entity.Category
import com.example.binarchplatinum.wrapper.Resource

interface LocalRepository {
    suspend fun getAllExpenses(): Resource<List<ExpensesWithCategory>>
    suspend fun getAllCategoryWithExpenses(): Resource<List<CategoryWithExpenses>>
    suspend fun getExpenseById(id : Int): Resource<ExpenseWithCategory>
    suspend fun getAllCategories(): Resource<List<Category>>
    suspend fun insertExpense(expenses: Expenses): Resource<Number>
}

class LocalRepositoryImpl(
    private val expensesDataSource: ExpensesDataSource,
    private val categoryDataSource: CategoryDataSource
) : LocalRepository {
    override suspend fun getAllExpenses(): Resource<List<ExpensesWithCategory>> {
        return proceed { expensesDataSource.getAllExpenses() }
    }

    override suspend fun getAllCategoryWithExpenses(): Resource<List<CategoryWithExpenses>> {
        return proceed { categoryDataSource.getAllCategoryWithExpenses() }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(message = exception.message)
        }
    }

    override suspend fun getExpenseById(id: Int): Resource<ExpenseWithCategory> {
        return proceed { expensesDataSource.getExpenseById(id) }
    }

    override suspend fun getAllCategories(): Resource<List<Category>> {
        return proceed { categoryDataSource.getAllCategory() }
    }

    override suspend fun insertExpense(expenses: Expenses): Resource<Number> {
        return proceed { expensesDataSource.insertExpense(expenses) }
    }
}