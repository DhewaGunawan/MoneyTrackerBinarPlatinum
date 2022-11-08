package com.example.binarchplatinum.data.room.datasource

import com.example.binarchplatinum.data.room.dao.ExpensesDao
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory

interface ExpensesDataSource {
    suspend fun getAllExpenses(): List<ExpensesWithCategory>

    suspend fun getExpensesByCategoryId(categoryId: Int): List<Expenses>

    suspend fun getExpenseById(id: Int): Expenses

    suspend fun insertExpense(expense: Expenses): Long

}

class ExpensesDataSourceImpl(private val dao: ExpensesDao) : ExpensesDataSource {
    override suspend fun getAllExpenses(): List<ExpensesWithCategory> {
        return dao.getAllExpenses()
    }

    override suspend fun getExpensesByCategoryId(id: Int): List<Expenses> {
        return dao.getExpensesByCategoryId(id)
    }

    override suspend fun getExpenseById(id: Int): Expenses {
        return dao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: Expenses): Long {
        return dao.insertExpense(expense)
    }

}