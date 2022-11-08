package com.example.binarchplatinum.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker

class DateTimePick {

    companion object {
        private const val TAG = "DateTimePick"
    }

    var onItemClick: ((Long) -> Unit)? = null

    private val constraintBuilder = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointBackward.now())

    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select Date")
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .setCalendarConstraints(constraintBuilder.build())
        .build().also { picker ->
            picker.addOnPositiveButtonClickListener {
                onItemClick?.invoke(it)
            }

            picker.addOnCancelListener {
                picker.dismiss()
            }
        }

    fun show(fragmentManager: FragmentManager) {
        datePicker.show(fragmentManager, TAG)
    }
}