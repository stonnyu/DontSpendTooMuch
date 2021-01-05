package com.example.dontspendtoomuch.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.SpendingAdapter
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.R
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import com.google.firebase.database.FirebaseDatabase
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

        createItemTouchHelper().attachToRecyclerView(binding.rvSpendings)
    }

    private fun loadSpendings() {
        val categoryTitle = arguments?.getString(ARG_SPENDING_TITLE)
        val spendingsData = SpendingModel.getData()!!

        spendingAmountSum = 0.0

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

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val spending = spendings.get(position)

                spendings.removeAt(position)

                val spendingIDs = SpendingModel.getIDs()
                val database = FirebaseDatabase.getInstance()
                database.getReference("Spending")
                    .child(spendingIDs.getValue(spending.spendingTitle)).removeValue()

                database.getReference("Category")
                    .child(spending.spendingCategory)
                    .child("spendings")
                    .child(spendingIDs.getValue(spending.spendingTitle)).removeValue()

                for (item in spendingIDs!!) {
                    Log.i("helpmijdan", spendingIDs.toString())
                }

                spendingAdapter.notifyDataSetChanged()
                initViews()

                Toast.makeText(context, "Spending deleted.",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_home)
            }
        }
        return ItemTouchHelper(callback)
    }
}