package com.example.binarchplatinum.ui.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.binarchplatinum.R
import com.example.binarchplatinum.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class CustomDialogAdd : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_list_category, categories)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        setOnClickListener()


    }

    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            submitExpense()
        }
        binding.etDate.setOnClickListener {
            pickDate()
        }
    }

    private fun pickDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.etDate.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun submitExpense() {

        //todo validation check if null or not
        val name = binding.etName.text
        val expense = binding.etExpense.text
        val category = binding.autoCompleteTextView.text
        val date = binding.etDate.text

        //todo save data to database with expense model?
        Toast.makeText(requireActivity(),"$name + $expense + $category + $date",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetDialogBinding.inflate(inflater,container,false)
        return binding.root
    }
}