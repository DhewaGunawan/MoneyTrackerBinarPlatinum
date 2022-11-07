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

   /* fun provideUserPreference(context: Context): UserPreference {
        return UserPreference(context)
    }
*/
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideExpenseDao(context: Context): ExpensesDao {
        return provideAppDatabase(context).expensesDao()
    }

    fun provideCategoryDao(context: Context): CategoriesDao {
        return provideAppDatabase(context).categoriesDao()
    }

    fun provideExpensesDataSource(context: Context): ExpensesDataSource {
        return ExpensesDataSourceImpl(provideExpenseDao(context))
    }

    fun provideCategoryDataSource(context: Context): CategoryDataSource {
        return CategoryDataSourceImpl(provideCategoryDao(context))
    }

   /* fun provideUserPreferenceDataSource(context: Context): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(provideUserPreference(context))
    }*/

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(
          /*  provideUserPreferenceDataSource(context),*/
            provideExpensesDataSource(context),
            provideCategoryDataSource(context)
        )
    }
}