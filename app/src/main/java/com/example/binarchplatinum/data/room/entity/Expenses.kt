package com.example.binarchplatinum.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
@Entity(tableName = "expenses")
data class Expenses(
    //id autogenerate
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? =  null,

    @ColumnInfo(name = "price")
    var price: BigDecimal,

    @ColumnInfo(name = "Date")
    var date: Date? = null,
) : Parcelable