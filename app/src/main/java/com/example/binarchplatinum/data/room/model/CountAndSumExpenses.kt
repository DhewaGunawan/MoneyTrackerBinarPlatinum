package com.example.binarchplatinum.data.room.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountAndSumExpenses(
    val count: Int,
    val sum: Double
) : Parcelable