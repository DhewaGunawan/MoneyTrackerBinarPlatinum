package com.example.binarchplatinum.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.binarchplatinum.data.room.entity.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    suspend fun getAllCategory() : List<Category>

    @Query("SELECT * FROM categories WHERE category_id == :id")
    suspend fun getCategoryById(id : Int) : Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(category: List<Category>)
}