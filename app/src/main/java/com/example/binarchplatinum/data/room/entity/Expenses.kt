package com.example.binarchplatinum.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "expenses")
data class Expenses(
    //id autogenerate
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? =  null,

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "Date")
    var date: String? = null,
) : Parcelable