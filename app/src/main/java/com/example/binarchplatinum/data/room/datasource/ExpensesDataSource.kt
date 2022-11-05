package com.example.binarchplatinum.data.room.datasource

import com.example.binarchplatinum.data.room.dao.ExpensesDao
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.ExpenseWithCategory

interface ExpensesDataSource {

    // Kalau mau ganti ke ExpenseWithCategory

    /*suspend fun getAllExpenses(): List<ExpenseWithCategory>

    suspend fun getExpensesByCategoryId(categoryId: Int): List<ExpenseWithCategory>*/

    suspend fun getExpenseById(id: Int): ExpenseWithCategory

    suspend fun insertExpense(expense: Expenses): Long

    //Kalau mau nambah fitur delete dan update

 /*   suspend fun deleteExpense(expense: Expenses): Int

    suspend fun updateExpense(expense: Expenses): Int*/
}

class ExpensesDataSourceImpl(private val dao: ExpensesDao) : ExpensesDataSource {
  /*  override suspend fun getAllExpenses(): List<ExpenseWithCategory> {
        return dao.getAllExpenses()
    }

    override suspend fun getExpensesByCategoryId(categoryId: Int): List<ExpenseWithCategory> {
        return dao.getExpensesByCategoryId(categoryId)
    }*/

    override suspend fun getExpenseById(id: Int): ExpenseWithCategory {
        return dao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: Expenses): Long {
        return dao.insertExpense(expense)
    }

/*
    override suspend fun deleteExpense(expense: Expenses): Int {
        return dao.deleteExpense(expense)
    }

    override suspend fun updateExpense(expense: Expenses): Int {
        return dao.updateExpense(expense)
    }
*/

}