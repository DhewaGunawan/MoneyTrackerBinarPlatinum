package com.example.binarchplatinum.pkg.home.data.room.datasource

import com.example.binarchplatinum.data.room.dao.DetailExpenditureDao
import com.example.binarchplatinum.data.room.entity.DetailExpenditure
import com.example.binarchplatinum.data.room.model.DetailExpenditureWithCategory

interface DetailExpenditureDataSource {
    suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory>

    suspend fun getDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory>

    suspend fun insertDetailExpenditure(note: DetailExpenditure): Long

    suspend fun deleteDetailExpenditure(note: DetailExpenditure): Int

    suspend fun updateDetailExpenditure(note: DetailExpenditure): Int
}

class DetailExpenditureDataSourceImpl(private val dao: DetailExpenditureDao) : DetailExpenditureDataSource {
    override suspend fun getAllDetailExpenditures(): List<DetailExpenditureWithCategory> {
        return dao.getAllDetailExpenditures()
    }

    override suspend fun getDetailExpenditureByCategoryId(categoryId: Int): List<DetailExpenditureWithCategory> {
        return dao.getAllDetailExpenditureByCategoryId(categoryId)
    }

    override suspend fun insertDetailExpenditure(detailExpenditure: DetailExpenditure): Long {
        return dao.insertDetailExpenditure(detailExpenditure)
    }

    override suspend fun deleteDetailExpenditure(detailExpenditure: DetailExpenditure): Int {
        return dao.deleteDetailExpenditure(detailExpenditure)
    }

    override suspend fun updateDetailExpenditure(detailExpenditure: DetailExpenditure): Int {
        return dao.updateDetailExpenditure(detailExpenditure)
    }

}