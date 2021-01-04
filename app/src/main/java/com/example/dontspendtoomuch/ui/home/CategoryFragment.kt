package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.SpendingAdapter
import com.example.dontspendtoomuch.DataModel.CategoryModel
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*

const val ARG_SPENDING_TITLE = "arg_spending_title"

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private var spendings = arrayListOf<Spending>()
    private var spendingAdapter = SpendingAdapter(spendings)

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

        initViews()
    }

    private fun initViews() {
        binding.rvSpendings.layoutManager = LinearLayoutManager(context,
                RecyclerView.VERTICAL,
                false
        )
        binding.rvSpendings.adapter = spendingAdapter
        this@CategoryFragment.spendings.clear()
        spendings.addAll(SpendingModel.getData()!!)

        setSpendings()

        spendingAdapter.notifyDataSetChanged()
    }

    private fun setSpendings() {
        val categoryTitle = arguments?.getString(ARG_SPENDING_TITLE)

        val spendingsData = SpendingModel.getData()!!

        for (spending in spendingsData) {
            if (spending.spendingCategory == categoryTitle) {
                spendings.add(spending)
            }
        }
    }
}