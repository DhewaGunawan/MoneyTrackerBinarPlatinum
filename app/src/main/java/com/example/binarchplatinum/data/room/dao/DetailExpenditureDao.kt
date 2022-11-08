package com.example.binarchplatinum.data.room.dao

import androidx.room.*
import com.example.binarchplatinum.data.room.entity.DetailExpenditure
import com.example.binarchplatinum.data.room.model.DetailExpenditureWithCategory

@Dao
interface DetailExpenditureDao {

    @Query("SELECT * FROM detailexpenditure")
    suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory>

    @Query("SELECT * FROM detailexpenditure WHERE category_id == :categoryId")
    suspend fun getAllDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailExpenditure(DetailExpenditure: DetailExpenditure) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailExpenditures(DetailExpenditure: List<DetailExpenditure>)

    @Delete
    suspend fun deleteDetailExpenditure(DetailExpenditure : DetailExpenditure) : Int

    @Update
    suspend fun updateDetailExpenditure(note : DetailExpenditure) : Int
}