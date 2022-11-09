package com.example.binarchplatinum.pkg.home.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.binarchplatinum.pkg.home.ui.viewmodel.MainViewModel
import com.example.binarchplatinum.R
import com.example.binarchplatinum.base.BaseActivity
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.constant.CategoryConstant
import com.example.binarchplatinum.data.localpref.UserPreference
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.CountAndSumExpenses
import com.example.binarchplatinum.databinding.ActivityHomeBinding
import com.example.binarchplatinum.di.ServiceLocator
import com.example.binarchplatinum.pkg.home.ui.adapter.HomeViewPagerAdapter
import com.example.binarchplatinum.pkg.login.LoginActivity
import com.example.binarchplatinum.wrapper.Resource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import java.text.NumberFormat
import java.util.*

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    companion object {
        private const val EXTRAS_NAME = "EXTRAS_NAME"

        fun startActivity(context: Context, name: String) {
            context.startActivity(Intent(context, HomeActivity::class.java).apply {
                putExtra(EXTRAS_NAME, name)
            })
        }

        private const val TAG = "HomeActivity"
    }

    private val dialogLogout by lazy {
        MaterialAlertDialogBuilder(this@HomeActivity)
            .setMessage(getString(R.string.logout_text))
            .setNegativeButton(getString(R.string.lbl_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl_yes) { _, _ ->
                preference.clearUserToken()
                Intent(this@HomeActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
    }

    private val viewModel: MainViewModel by lazy {
        GenericViewModelFactory(MainViewModel(ServiceLocator.provideLocalRepository(this@HomeActivity))).create(
            MainViewModel::class.java
        )
    }

    private var _viewPagerAdapter: HomeViewPagerAdapter? = null
    private val viewPagerAdapter get() = _viewPagerAdapter!!

    private var groupedTransactionList: MutableList<CategoryWithExpenses> = mutableListOf()
    private var totalItemAndPrice: MutableList<CountAndSumExpenses> = mutableListOf()

    private val preference: UserPreference by lazy {
        UserPreference(this@HomeActivity)
    }

    private val addExpenseBottomDialog by lazy {
        CustomDialogAdd()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        binding.apply {
            includeToolbar.apply {
                //TODO: TEXT FETCH FROM LOCAL STORAGE
                Log.d("Testing", "onCreate: ${preference.getUserToken()}")
                titleName.text = preference.getUserToken()
                btnLogout.setOnClickListener {
                    dialogLogout.show()
                }
            }

            initViewPagerAdapter()

            includeBottomBtn.apply {
                cvAddData.setOnClickListener {
                    addExpenseBottomDialog.show(
                        supportFragmentManager,
                        addExpenseBottomDialog.tag
                    )
                }
            }

            includeChartView.apply {
                ivHighest.visibility = View.INVISIBLE
            }
        }

        subscribeObserver()
    }

    private fun initViewPagerAdapter() {
        binding.apply {
            includeListFilter.apply {
                _viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, lifecycle)
                viewPager.adapter = viewPagerAdapter
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text =
                        when (position) {
                            0 -> getString(R.string.filter_all_expense)
                            1 -> getString(R.string.filter_category)
                            else -> ({
                            }).toString()
                        }
                }.attach()
            }
        }
    }

    fun refreshData() {
        initViewPagerAdapter()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun subscribeObserver() {
        observeData()
    }

    private fun observeData() {
        viewModel.getAllCategoryWithExpenses()
        viewModel.categoryWithExpensesResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                    println("Error ${it.message}")
                }
                is Resource.Loading -> {
                    // do nothing for now
                    println("Loading")
                }
                is Resource.Success -> {
                    showGroupedTransactionList(it.data)
                    binding.apply {
                        includeChartView.apply {
                            val entries: ArrayList<PieEntry> = ArrayList()
                            entries.apply {
                                var highestPrice = 0
                                for (item in groupedTransactionList) {
//                                    Log.d(TAG, "item : ${getTotalPriceByCategory(item.expenses)}")
                                    if (getTotalPriceByCategory(item.expenses) == 0) else {
                                        if (highestPrice < getTotalPriceByCategory(item.expenses)) {
                                            highestPrice = getTotalPriceByCategory(item.expenses)
                                            getCategoryIcon(item.category.categoryName)
                                        }
                                        val persetage =
                                            getPercentage(item, totalItemAndPrice[0].sum.toInt())
//                                        Log.d(TAG, "observeData: $persetage & $highestPrice")
                                        add(
                                            PieEntry(
                                                persetage!!,
                                                item.category.categoryName,
                                                getTotalPriceByCategory(item.expenses)
                                            )
                                        )
                                    }
                                }
                                highestPrice = 0
                            }
                            val colors: ArrayList<Int> = ArrayList()
                            for (color in ColorTemplate.MATERIAL_COLORS) {
                                colors.add(color)
                            }
                            for (color in ColorTemplate.VORDIPLOM_COLORS) {
                                colors.add(color)
                            }
                            val dataSet = PieDataSet(entries, "Expense Category")
                            dataSet.colors = colors
                            val data = PieData(dataSet)
                            data.apply {
                                setDrawValues(true)
                                setValueFormatter(PercentFormatter(piechart))
                                setValueTextSize(12f)
                                setValueTextColor(Color.BLACK)
                            }
                            piechart.data = data
                            piechart.invalidate()
                            piechart.animateY(1400, Easing.EaseInOutQuad)
                        }
                    }
                }
                else -> {}
            }
        }

        viewModel.countAndSumExpenses()
        viewModel.totalItemAndPrice.observe(this) {
            when (it) {
                is Resource.Error -> {
                    // do nothing for now
                    println("Error ${it.message}")
                }
                is Resource.Loading -> {
                    // do nothing for now
                    println("Loading")
                }
                is Resource.Success -> {
                    getTotalItemCount(it.data)
                    binding.apply {
                        includeChartView.apply {
                            val formatPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                            tvTotalPrice.text = formatPrice.format(totalItemAndPrice[0].sum.toInt())
                        }
                    }
                }
                else -> {}
            }
        }
        setupPieChart()
    }

    private fun setupPieChart() {
        binding.apply {
            includeChartView.apply {
                piechart.apply {
                    isDrawHoleEnabled = true
                    setUsePercentValues(true)
                    setEntryLabelTextSize(10f)
                    setEntryLabelColor(Color.BLACK)
                    centerText = "Spending by Category"
                    setCenterTextSize(6f)
                    description.isEnabled = false
                    val legend = legend
                    legend.apply {
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                        orientation = Legend.LegendOrientation.VERTICAL
                        setDrawInside(false)
                        isEnabled = false
                    }
                }
            }
        }
    }

    private fun getCategoryIcon(name: String) {
        binding.apply {
            includeChartView.apply {
                ivHighest.visibility = View.VISIBLE
                when (name) {
                    CategoryConstant.SPORT -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_sport)
                        tvHighest.text = name
                    }
                    CategoryConstant.ENTERTAINMENT -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_entertainment)
                        tvHighest.text = name
                    }
                    CategoryConstant.FOOD_AND_DRINK -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_food_drink)
                        tvHighest.text = name
                    }
                    CategoryConstant.GROCERIES -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_groceries)
                        tvHighest.text = name
                    }
                    CategoryConstant.HOME_BILLS -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_home_bills)
                        tvHighest.text = name
                    }
                    CategoryConstant.TRANSPORTATION -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_transportation)
                        tvHighest.text = name
                    }
                    else -> {
                        ivHighest.setImageResource(R.drawable.ic_cat_shopping)
                        tvHighest.text = name
                    }
                }
            }
        }
    }

    private fun getTotalPriceByCategory(expenses: List<Expenses>): Int {
        var total = 0
        for (item in expenses) {
            total += (item.price).toInt()
        }
        return total
    }

    private fun showGroupedTransactionList(data: List<CategoryWithExpenses>?) {
        data?.let { listData ->
            if (listData.isNotEmpty()) {
                setGroupedData(listData)
            }
        }
    }

    private fun getTotalItemCount(data: CountAndSumExpenses?) {
        data?.let {
            totalItemAndPrice.clear()
            totalItemAndPrice.add(data)
        }

    }

    private fun setGroupedData(list: List<CategoryWithExpenses>) {
        groupedTransactionList.clear()
        groupedTransactionList.addAll(list)
    }

    private fun getPercentage(item: CategoryWithExpenses, data: Int?): Float? {
        val let = data?.let {
            ((getTotalPriceByCategory(item.expenses).toDouble() / data.toDouble()) * 100).toFloat()
        }
        return let
    }
}