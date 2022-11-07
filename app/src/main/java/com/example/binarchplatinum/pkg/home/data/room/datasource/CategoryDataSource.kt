package com.example.binarchplatinum.pkg.home.data.room.datasource

import com.example.binarchplatinum.pkg.home.data.room.dao.CategoriesDao
import com.example.binarchplatinum.pkg.home.data.room.entity.Category

interface CategoryDataSource {
    suspend fun getAllCategory(): List<Category>

    suspend fun getCategoryById(id: Int): Category

    suspend fun insertCategory(category: Category): Long
}


class CategoryDataSourceImpl(private val dao: CategoriesDao) : CategoryDataSource {
    override suspend fun getAllCategory(): List<Category> {
        return dao.getAllCategory()
    }

    override suspend fun getCategoryById(id: Int): Category {
        return dao.getCategoryById(id)
    }

    override suspend fun insertCategory(category: Category): Long {
        return dao.insertCategory(category)
    }

}