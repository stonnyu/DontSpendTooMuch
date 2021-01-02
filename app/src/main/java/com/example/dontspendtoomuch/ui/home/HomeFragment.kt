package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.CategoryAdapter
import com.example.dontspendtoomuch.Adapters.SpendingAdapter
import com.example.dontspendtoomuch.DataModel.Category
import com.example.dontspendtoomuch.DataModel.CategoryModel
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.R
import com.example.dontspendtoomuch.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(), Observer {

    private lateinit var binding: FragmentHomeBinding
    private var categories = arrayListOf<Category>()
    private var categoryAdapter = CategoryAdapter(categories) { portal: Category ->
        onClickCategory(
            portal
        )
    }

    private var spendings = arrayListOf<Spending>()
    private var spendingAdapter = SpendingAdapter(spendings) { portal: Spending ->
        onClickSpending(
            portal
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val spendingDataList: RecyclerView = binding.rvSpendings
//        spendingDataList.adapter = spendingAdapter
//        SpendingModel.addObserver(this)

        val categoryDataList: RecyclerView = binding.rvCategories
        categoryDataList.adapter = spendingAdapter
        CategoryModel.addObserver(this)
        initViews()

        binding.fabAddSpending.setOnClickListener {
            findNavController().navigate(R.id.addSpendingFragment)
        }
    }

    private fun initViews() {
//        binding.rvSpendings.layoutManager = LinearLayoutManager(context,
//            RecyclerView.VERTICAL,
//            false
//        )
//        binding.rvSpendings.adapter = spendingAdapter
//        this@HomeFragment.spendings.clear()
//        spendings.addAll(SpendingModel.getData()!!)
//        spendingAdapter.notifyDataSetChanged()

        binding.rvCategories.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL,
            false
        )
        binding.rvCategories.adapter = categoryAdapter
        this@HomeFragment.categories.clear()
        categories.addAll(CategoryModel.getData()!!)
        categoryAdapter.notifyDataSetChanged()
    }

    override fun update(p0: Observable?, p1: Any?) {
        this@HomeFragment.categories.clear()

        val categoryData = CategoryModel.getData()!!

        if (categoryData != null) {
            categories.addAll(categoryData)
        }

        categoryAdapter.notifyDataSetChanged()
    }

    private fun onClickSpending(spending: Spending) {
        TODO("Not yet implemented")
    }

    private fun onClickCategory(portal: Category) {
        findNavController().navigate(R.id.categoryFragment)
    }
}