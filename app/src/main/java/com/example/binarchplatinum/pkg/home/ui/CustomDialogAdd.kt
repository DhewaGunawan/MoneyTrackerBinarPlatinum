package com.example.binarchplatinum.pkg.home.ui

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
import com.example.binarchplatinum.databinding.BottomSheetDialogBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.pkg.home.ui.viewmodel.CustomDialogAddViewModel
import com.example.binarchplatinum.utils.*
import com.example.binarchplatinum.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CustomDialogAdd : BottomSheetDialogFragment() {
    companion object {
        private const val TAG = "CustomDialogAdd"
    }

    private val viewModel: CustomDialogAddViewModel by lazy {
        GenericViewModelFactory(
            CustomDialogAddViewModel(
                ServiceLocator.provideLocalRepository(
                    requireContext()
                )
            )
        ).create(
            CustomDialogAddViewModel::class.java
        )
    }

    private lateinit var categories: List<Category>
    private var isItemSelected: Boolean = false

    private var _binding: BottomSheetDialogBinding? = null
    private val binding: BottomSheetDialogBinding get() = _binding!!
    private var dateExpense: Long = 0
    private val datePicker by lazy { DateTimePick() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        datePicker.onItemClick = {
            dateExpense = it
            val dateData = Converters.longToDate(it)
            binding.etDate.setText(dateData)
            Log.d("Test", "Converter: $dateExpense}")
        }
        setOnClickListener()
        binding.etExpense.addCurrencyFormatter("Rp.")
        binding.etExpense.setText("0")

        getInitialData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getInitialData() {
        viewModel.getInitialData()
    }

    private fun observeData() {
        viewModel.initialDataResult.observe(this) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Idle -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    categories = it.data?.second!!
                    initCategory(
                        categories
                    )
                }
            }
        }

        viewModel.insertResult.observe(this) {
            when (it) {
                is Resource.Idle -> {
                    binding.apply {
                        isItemSelected = false
                        etName.setText("")
                        etExpense.text = null
                        autoCompleteTextView.text = null
                        initCategory(
                            categories
                        )
                        etDate.setText("")
                    }
                }
                is Resource.Loading -> {
                    LoadingDialog.startLoading(requireActivity())
                }
                is Resource.Success -> {
                    LoadingDialog.hideLoading()
                    Toast.makeText(requireActivity(), "Insert data Success", Toast.LENGTH_SHORT)
                        .show()
                    (activity as HomeActivity).refreshData()
                    dismiss()
                }
                is Resource.Error -> {
                    LoadingDialog.hideLoading()
                    Toast.makeText(requireActivity(), "Error when insert data", Toast.LENGTH_SHORT)
                        .show()
                    dismiss()
                }
            }
        }
    }

    private fun initCategory(categories: List<Category>?) {
        val categoriesTitle = categories?.map { category -> category.categoryName }.orEmpty()
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_list_category,
            categoriesTitle
        )
        binding.autoCompleteTextView.setOnItemClickListener { _, _, pos, _ ->
            viewModel.selectedCategoryId =
                categories?.get(pos)?.categoryId ?: CommonConstant.UNSET_ID
            isItemSelected = true
        }
        binding.autoCompleteTextView.setAdapter(adapter)
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
        if (!isAllFieldsChecked) {
            if (!isEditAction()) viewModel.insertExpense(parseFormIntoEntity())
        } else {
            Toast.makeText(requireActivity(), "All these field are required", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun parseFormIntoEntity(): Expenses {
        val nameExpense = binding.etName.text.toString().trim()
        val priceWithFormat = binding.etExpense.text.toString().trim()
        val cleanPrice = priceWithFormat.replace("[Rp,. ]".toRegex(), "")

        return Expenses(
            name = nameExpense,
            price = cleanPrice.toBigDecimal(),
            categoryId = viewModel.selectedCategoryId,
            date = Converters.fromTimestamp(dateExpense)
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
        val expense = binding.etExpense.text.toString()
        var isErrorName = false
        var isErrorExpense = false
        var isErrorCategory = false
        var isErrorDate = false
        binding.apply {
            if (etName.text.toString().isBlank() or etName.text.toString().isEmpty()) {
                tilName.error = "Name Can't be empty"
                tilName.isErrorEnabled = true
                isErrorName = true
            } else {
                tilName.error = null
                tilName.isErrorEnabled = false
                isErrorName = false
            }

            if (autoCompleteTextView.text.toString()
                    .isBlank() || autoCompleteTextView.text.toString().isEmpty()
            ) {
                tilCategory.error = "Must select an category"
                tilCategory.isErrorEnabled = true
                isErrorCategory = true
            } else {
                tilCategory.error = null
                tilCategory.isErrorEnabled = false
                isErrorCategory = false
            }

            if (binding.etDate.text.toString().isBlank() || binding.etDate.text.toString()
                    .isEmpty()
            ) {
                tilDate.error = "Date Can't be empty"
                tilDate.isErrorEnabled = true
                isErrorDate = true
            } else {
                tilDate.error = null
                tilDate.isErrorEnabled = false
                isErrorDate = false
            }

            if (expense == "Rp.0") {
                tilExpense.error = "Expense Can't be empty"
                tilExpense.isErrorEnabled = true
                isErrorExpense = true
            } else {
                tilExpense.error = null
                tilExpense.isErrorEnabled = false
                isErrorExpense = false
            }

            return isErrorName || isErrorExpense || isErrorCategory || isErrorDate

        }

//                !(binding.etName.isNotNullOrEmpty("Name Can't be empty") and
//                        binding.etExpense.isNotNullOrEmpty("Expense Can't be empty") and
//                        binding.autoCompleteTextView.isNotNullOrEmpty("Must select an category") and
//                        binding.etDate.isNotNullOrEmpty("Date Can't be empty"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}