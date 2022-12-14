package com.example.binarchplatinum.data.room.datasource

import com.example.binarchplatinum.data.room.dao.ExpensesDao
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.CountAndSumExpenses
import com.example.binarchplatinum.data.room.model.ExpenseWithCategory
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory

interface ExpensesDataSource {
    suspend fun getAllExpenses(): List<ExpensesWithCategory>

    suspend fun getExpensesByCategoryId(categoryId: Int): List<Expenses>

    suspend fun getExpenseById(id: Int): ExpensesWithCategory

    suspend fun insertExpense(expense: Expenses): Long

    suspend fun countAndSumExpenses(): CountAndSumExpenses

    suspend fun deleteExpenseById(id: Int): Int
}

class ExpensesDataSourceImpl(private val dao: ExpensesDao) : ExpensesDataSource {
    override suspend fun getAllExpenses(): List<ExpensesWithCategory> {
        return dao.getAllExpenses()
    }

    override suspend fun getExpensesByCategoryId(id: Int): List<Expenses> {
        return dao.getExpensesByCategoryId(id)
    }

    override suspend fun getExpenseById(id: Int): ExpensesWithCategory {
        return dao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: Expenses): Long {
        return dao.insertExpense(expense)
    }

    override suspend fun countAndSumExpenses(): CountAndSumExpenses {
        return dao.countAndSumExpenses()
    }

    override suspend fun deleteExpenseById(id: Int): Int {
        return dao.deleteExpenseById(id)
    }
}