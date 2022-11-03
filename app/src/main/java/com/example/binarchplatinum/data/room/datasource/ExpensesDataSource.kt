package com.example.binarchplatinum.data.room.datasource

import com.example.binarchplatinum.data.room.entity.Expenses

interface ExpensesDataSource {
    suspend fun getAllExpenses(): List<Expenses>

    suspend fun getExpensesByCategoryId(categoryId: Int): List<Expenses>

    suspend fun getExpenseById(id: Int): Expenses

    suspend fun insertExpense(expense: Expenses): Long

    suspend fun deleteExpense(expense: Expenses): Int

    suspend fun updateExpense(expense: Expenses): Int
}