package com.example.binarchplatinum.data.room.dao

import androidx.room.*
import com.example.binarchplatinum.data.room.entity.Expenses

@Dao
interface ExpensesDao {
/*
    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses() : List<Expenses>

    @Query("SELECT * FROM expenses WHERE category_id == :categoryId")
    suspend fun getExpensesByCategoryId(categoryId : Int) : List<Expenses>

    @Query("SELECT * FROM expenses WHERE id == :id")
    suspend fun getExpenseById(id: Int): Expenses
*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expenses) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenses(expense: List<Expenses>)
/*
    @Delete
    suspend fun deleteExpense(expense: Expenses) : Int

    @Update
    suspend fun updateExpense(expense: Expenses) : Int*/
}