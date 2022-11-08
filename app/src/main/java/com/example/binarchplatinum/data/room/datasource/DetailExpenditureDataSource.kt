package com.example.binarchplatinum.pkg.home.data.room.datasource

import com.example.binarchplatinum.data.room.dao.DetailExpenditureDao
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.DetailExpenditureWithCategory

interface DetailExpenditureDataSource {
    suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory>

    suspend fun getDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory>

    suspend fun insertDetailExpenditure(note: Expenses): Long

    suspend fun deleteDetailExpenditure(note: Expenses): Int

    suspend fun updateDetailExpenditure(note: Expenses): Int
}

class DetailExpenditureDataSourceImpl(private val dao: DetailExpenditureDao) : DetailExpenditureDataSource {
    override suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory> {
        return dao.getAllDetailExpenditures()
    }

    override suspend fun getDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory> {
        return dao.getAllDetailExpenditureByCategoryId(categoryId)
    }

    override suspend fun insertDetailExpenditure(expenses: Expenses): Long {
        return dao.insertDetailExpenditure(expenses)
    }

    override suspend fun deleteDetailExpenditure(expenses: Expenses): Int {
        return dao.deleteDetailExpenditure(expenses)
    }

    override suspend fun updateDetailExpenditure(expenses: Expenses): Int {
        return dao.updateDetailExpenditure(expenses)
    }

}