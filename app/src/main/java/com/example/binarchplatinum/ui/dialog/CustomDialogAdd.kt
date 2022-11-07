package com.example.binarchplatinum.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.binarchplatinum.R
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.constant.CommonConstant
import com.example.binarchplatinum.data.room.entity.Category
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.ExpenseWithCategory
import com.example.binarchplatinum.databinding.BottomSheetDialogBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.utils.*
import com.example.binarchplatinum.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CustomDialogAdd : BottomSheetDialogFragment() {

    private val viewModel: CustomDialogAddViewModel by lazy {
        GenericViewModelFactory(CustomDialogAddViewModel(ServiceLocator.provideLocalRepository(requireContext()))).create(
            CustomDialogAddViewModel::class.java
        )
    }

    /*override fun onStart() {
        super.onStart()
        *//*viewModel.setIntentData(intent)*//*
    }*/

    private lateinit var binding: BottomSheetDialogBinding
    private var dateExpense : Long = 0
    private val datePicker by lazy { DateTimePick() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        datePicker.onItemClick = {
            dateExpense = it
            val dateData = DateConverters.longToDate(it)
            binding.etDate.setText(dateData)
            Log.d("Test", "Converter: $dateExpense}")
        }
        setOnClickListener()
        binding.etExpense.addCurrencyFormatter("Rp.")

        getInitialData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun getInitialData() {
        viewModel.getInitialData()
    }

    private fun observeData() {
        viewModel.initialDataResult.observe(this) {
            when(it){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val expense = it.data?.first
                    val categories = it.data?.second
                    val selectedPos = it.data?.third ?: 0
                    initCategory(
                        categories,
                        selectedPos
                    )
                    Log.d("TAG",it.data.toString())
                    bindDataToForm(expense)
                }
            }
        }

        viewModel.insertResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    LoadingDialog.startLoading(requireActivity())
                   /* setFormEnabled(false)
                    binding.pbForm.isVisible = true*/
                }
                is Resource.Success -> {
                    LoadingDialog.hideLoading()
                  /*  setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()*/

                    Toast.makeText(requireActivity(), "Insert data Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                is Resource.Error -> {
                    LoadingDialog.hideLoading()
                    /*setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()*/
                    Toast.makeText(requireActivity(), "Error when insert data", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    private fun bindDataToForm(data: ExpenseWithCategory?) {
        data?.let {

            val dateLong = DateConverters.dateToTimestamp(data.expenses.date)
            val dateString = DateConverters.longToDate(dateLong)

            binding.etName.setText(data.expenses.name)
            binding.etExpense.setText(data.expenses.price.toString())
            binding.etDate.setText(dateString)

        }
    }

    private fun initCategory(categories: List<Category>?, selectedPos: Int) {
        val categoriesTitle = categories?.map { category -> category.categoryName }.orEmpty()
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_list_category,
            categoriesTitle
        )
        binding.autoCompleteTextView.setOnItemClickListener { _, _, pos, _ ->
            viewModel.selectedCategoryId = categories?.get(pos)?.categoryId ?: CommonConstant.UNSET_ID
        }
        binding.autoCompleteTextView.setAdapter(adapter)
        if(selectedPos != -1){
            binding.autoCompleteTextView.setText(
                binding.autoCompleteTextView.adapter.getItem(selectedPos).toString(),
                false
            )
        }
    }

    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            submitExpense()
        }
        binding.etDate.setOnClickListener {
           datePicker.show(childFragmentManager)
        }
    }


    private fun submitExpense() {
        val isAllFieldsChecked = checkAllFields()
       /* */
        if (!isAllFieldsChecked) {
            if (isEditAction()) {
                /*viewModel.updateNote(parseFormIntoEntity())*/
            } else {
                viewModel.insertNote(parseFormIntoEntity())

            }
           /* if (binding.etExpense.inputType == InputType.TYPE_CLASS_NUMBER){
                expense.let { expense ->
                    expense?.name = name
                    expense?.price = cleanPrice.toDouble()
                    expense?.categoryId = positionCategory
                    expense?.date = DateConverters.fromTimestamp(date)
                }
            } else
                binding.tilExpense.error = "Number Only"*/

        } else {
            Toast.makeText(requireActivity(),"All these field are required",Toast.LENGTH_SHORT).show()
        }

    }

    private fun parseFormIntoEntity(): Expenses {
        val nameExpense = binding.etName.text.toString().trim()
        val priceWithFormat  = binding.etExpense.text.toString().trim()
        val cleanPrice = priceWithFormat.replace("[Rp,. ]".toRegex(), "")

        return Expenses(
            name = nameExpense,
            price = cleanPrice.toDouble(),
            categoryId = viewModel.selectedCategoryId,
            date = DateConverters.fromTimestamp(dateExpense)
        ).apply {
            Log.d("TAG", this.toString())
            if (isEditAction()) {
                id = viewModel.expenseId
            }
        }

    }

    private fun isEditAction(): Boolean {
        return viewModel.expenseId != CommonConstant.UNSET_ID
    }

    private fun checkAllFields(): Boolean {
        return !(binding.etName.isNotNullOrEmpty("Name Can't be empty ") and
                binding.etExpense.isNotNullOrEmpty("Expense Can't be empty") and
                binding.autoCompleteTextView.isNotNullOrEmpty("Must select an category") and
                binding.etDate.isNotNullOrEmpty("Date Can't be empty"))
    }

}