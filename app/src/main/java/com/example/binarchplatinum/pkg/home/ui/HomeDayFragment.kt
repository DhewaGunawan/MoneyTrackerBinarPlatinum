package com.example.binarchplatinum.pkg.home.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.binarchplatinum.base.GenericViewModelFactory
import com.example.binarchplatinum.databinding.FragmentHomeDayBinding
import com.example.binarchplatinum.pkg.home.ui.viewmodel.HomeViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class HomeDayFragment : Fragment() {
    companion object {
        private const val TAG = "HomeDayFragment"
    }

    private var _binding: FragmentHomeDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by lazy {
        GenericViewModelFactory(HomeViewModel()).create(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.someValue.observe(requireActivity()) {
            binding.title.text = it
        }
        viewModel.doSomeWork()
        subscribeObserver()
//        lifecycleScope.launch {
//            val notes = AppDatabase.getInstance(requireContext()).notesDao().getAllNotes()
//            Toast.makeText(requireContext(), notes.size.toString(), Toast.LENGTH_SHORT).show()
//        }
    }

    private fun subscribeObserver() {
        setupPieChart()
        loadPieChart()
    }

    private fun setupPieChart() {
        binding.apply {
            includeChartView.apply {
                piechart.apply {
                    isDrawHoleEnabled = true
                    setUsePercentValues(true)
                    setEntryLabelTextSize(6f)
                    setEntryLabelColor(Color.BLACK)
                    centerText = "Spending by Category"
                    setCenterTextSize(8f)
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

    private fun loadPieChart() {
        Log.d(TAG, "loadPieChart: masuk")
        binding.apply {
            includeChartView.apply {
                val entries: ArrayList<PieEntry> = ArrayList()
                entries.apply {
                    add(PieEntry(0.10f, "Shopping"))
                    add(PieEntry(0.10f, "Sports"))
                    add(PieEntry(0.05f, "Food $ Drink"))
                    add(PieEntry(0.2f, "Transportation"))
                    add(PieEntry(0.15f, "Groceries"))
                    add(PieEntry(0.05f, "Home Bills"))
                    add(PieEntry(0.05f, "Entertainment"))
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
}