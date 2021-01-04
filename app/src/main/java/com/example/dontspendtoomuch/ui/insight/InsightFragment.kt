package com.example.dontspendtoomuch.ui.insight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.dontspendtoomuch.DataModel.Category
import com.example.dontspendtoomuch.DataModel.CategoryModel
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.databinding.FragmentInsightBinding

class InsightFragment : Fragment() {

    private lateinit var binding: FragmentInsightBinding
    private val categoriesData = CategoryModel.getData()!!
    private val spendingsData = SpendingModel.getData()!!
    private var spendingAmountSum = 0.0
    private val map : HashMap<String, Double>  = hashMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInsightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChart()
    }

    private fun initChart() {
        var anyChartView: AnyChartView = binding.anyPiechartView
        var dataEntries = arrayListOf<DataEntry>()
        var pieChart: Pie = AnyChart.pie()

        for (categories in categoriesData) {
            spendingAmountSum = 0.0
            for (spending in spendingsData) {
                if (spending.spendingCategory == categories.categoryTitle) {
                    spendingAmountSum += spending.spendingAmount
                }

            }
            map.put(categories.categoryTitle, spendingAmountSum)
        }

        map.forEach {(key, value) ->
            dataEntries.add(ValueDataEntry(key, value))
        }

        pieChart.data(dataEntries)
        anyChartView.setChart(pieChart)
    }
}