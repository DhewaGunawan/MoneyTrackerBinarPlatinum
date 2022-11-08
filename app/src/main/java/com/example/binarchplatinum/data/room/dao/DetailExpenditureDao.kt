package com.example.binarchplatinum.data.room.dao

import androidx.room.*
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.DetailExpenditureWithCategory

@Dao
interface DetailExpenditureDao {

    @Query("SELECT * FROM detailexpenditure")
    suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory>

    @Query("SELECT * FROM detailexpenditure WHERE category_id == :categoryId")
    suspend fun getAllDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailExpenditure(expenses: Expenses) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailExpenditures(expenses: List<Expenses>)

    @Delete
    suspend fun deleteDetailExpenditure(expenses : Expenses) : Int

    @Update
    suspend fun updateDetailExpenditure(expenses : Expenses) : Int
}