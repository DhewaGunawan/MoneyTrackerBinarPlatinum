package com.example.binarchplatinum.di

import android.content.Context
import com.example.binarchplatinum.data.repository.LocalRepository
import com.example.binarchplatinum.data.repository.LocalRepositoryImpl
import com.example.binarchplatinum.data.room.AppDatabase
import com.example.binarchplatinum.data.room.dao.CategoriesDao
import com.example.binarchplatinum.data.room.dao.ExpensesDao
import com.example.binarchplatinum.data.room.datasource.CategoryDataSource
import com.example.binarchplatinum.data.room.datasource.CategoryDataSourceImpl
import com.example.binarchplatinum.data.room.datasource.ExpensesDataSource
import com.example.binarchplatinum.data.room.datasource.ExpensesDataSourceImpl

object ServiceLocator {

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideExpensesDao(context: Context): ExpensesDao {
        return provideAppDatabase(context).expensesDao()
    }

    fun provideCategoryDao(context: Context): CategoriesDao {
        return provideAppDatabase(context).categoriesDao()
    }

    fun provideExpenseDataSource(context: Context): ExpensesDataSource {
        return ExpensesDataSourceImpl(provideExpensesDao(context))
    }

    fun provideCategoryDataSource(context: Context): CategoryDataSource {
        return CategoryDataSourceImpl(provideCategoryDao(context))
    }

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideExpenseDataSource(context),
            provideCategoryDataSource(context)
        )
    }
}