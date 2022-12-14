package com.example.binarchplatinum.data.room.datasource

import com.example.binarchplatinum.data.room.dao.CategoriesDao
import com.example.binarchplatinum.data.room.entity.Category
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses


interface CategoryDataSource {
    suspend fun getAllCategory(): List<Category>

    suspend fun getAllCategoryWithExpenses(): List<CategoryWithExpenses>

    suspend fun getCategoryById(id: Int): Category

    suspend fun insertCategory(category: Category): Long
}


class CategoryDataSourceImpl(private val dao: CategoriesDao) : CategoryDataSource {
    override suspend fun getAllCategory(): List<Category> {
        return dao.getAllCategory()
    }

    override suspend fun getAllCategoryWithExpenses(): List<CategoryWithExpenses> {
        return dao.getAllCategoryWithExpenses()
    }

    override suspend fun getCategoryById(id: Int): Category {
        return dao.getCategoryById(id)
    }

    override suspend fun insertCategory(category: Category): Long {
        return dao.insertCategory(category)
    }
}