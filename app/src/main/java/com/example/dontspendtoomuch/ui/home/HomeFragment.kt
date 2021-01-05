package com.example.dontspendtoomuch.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.CategoryAdapter
import com.example.dontspendtoomuch.DataModel.Category
import com.example.dontspendtoomuch.DataModel.CategoryModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryDataList: RecyclerView = binding.rvCategories
        categoryDataList.adapter = categoryAdapter
        CategoryModel.addObserver(this)
        initViews()

        binding.fabAddSpending.setOnClickListener {
            findNavController().navigate(R.id.addSpendingFragment)
        }
    }

    private fun initViews() {
        binding.rvCategories.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL,
            false
        )
        binding.rvCategories.adapter = categoryAdapter
        this@HomeFragment.categories.clear()
        SpendingModel.getData()!!
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

    private fun onClickCategory(portal: Category) {
        val args = Bundle()
        var hasSpendings: Boolean = false
        val spendingsData = SpendingModel.getData()!!
        args.putString(ARG_SPENDING_TITLE, portal.categoryTitle)

        for (spending in spendingsData) {
            if (spending.spendingCategory == portal.categoryTitle) {
                hasSpendings = true
            }
        }

        if (hasSpendings) {
            findNavController().navigate(R.id.categoryFragment, args)
        } else {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setMessage(getString(R.string.click_no_spendings_dialog))
            alertDialog.setNegativeButton(getString(R.string.click_no_spendings_button_text)) {
                    _, _ -> }
            alertDialog.create().show()
        }
    }
}