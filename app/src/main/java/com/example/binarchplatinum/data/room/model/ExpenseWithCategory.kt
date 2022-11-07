package com.example.binarchplatinum.data.room.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.binarchplatinum.data.room.entity.Category
import com.example.binarchplatinum.data.room.entity.Expenses
import kotlinx.parcelize.Parcelize

@Parcelize
class ExpenseWithCategory(
    @Embedded
    val expenses: Expenses,
    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: Category
): Parcelable