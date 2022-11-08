package com.example.binarchplatinum.data.repository

import com.example.binarchplatinum.data.room.datasource.CategoryDataSource
import com.example.binarchplatinum.data.room.datasource.ExpensesDataSource
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.CountAndSumExpenses
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.wrapper.Resource

interface LocalRepository {
    suspend fun getAllExpenses(): Resource<List<ExpensesWithCategory>>
    suspend fun getAllCategoryWithExpenses(): Resource<List<CategoryWithExpenses>>
    suspend fun countAndSumExpenses(): Resource<CountAndSumExpenses>
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

    override suspend fun countAndSumExpenses(): Resource<CountAndSumExpenses> {
        return proceed { expensesDataSource.countAndSumExpenses() }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(message = exception.message)
        }
    }
}