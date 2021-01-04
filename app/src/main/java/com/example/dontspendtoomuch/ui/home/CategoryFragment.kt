package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.SpendingAdapter
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import java.util.*

const val ARG_SPENDING_TITLE = "arg_spending_title"

class CategoryFragment : Fragment(), Observer {

    private lateinit var binding: FragmentCategoryBinding
    private var spendings = arrayListOf<Spending>()
    private var spendingAdapter = SpendingAdapter(spendings)
    private var spendingAmountSum : Double = 0.0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spendingDataList: RecyclerView = binding.rvSpendings
        spendingDataList.adapter = spendingAdapter

        SpendingModel.addObserver(this)
        initViews()
    }

    private fun initViews() {
        binding.rvSpendings.layoutManager = LinearLayoutManager(context,
                RecyclerView.VERTICAL,
                false
        )
        binding.rvSpendings.adapter = spendingAdapter

        loadSpendings()
        binding.tvTotalSpendingAmount.text = String
            .format("Total: €%.2f", spendingAmountSum.toString()
            .toDouble())
            .replace(".", ",")
    }

    private fun loadSpendings() {
        val categoryTitle = arguments?.getString(ARG_SPENDING_TITLE)
        val spendingsData = SpendingModel.getData()!!

        for (spending in spendingsData) {
            if (spending.spendingCategory == categoryTitle) {
                spendingAmountSum += spending.spendingAmount
                spendings.add(spending)
            }
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        this@CategoryFragment.spendings.clear()
        loadSpendings()
        spendingAdapter.notifyDataSetChanged()
    }
}