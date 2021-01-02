package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.Adapters.SpendingAdapter
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.DataModel.SpendingModel
import com.example.dontspendtoomuch.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(), Observer {

    private lateinit var binding: FragmentHomeBinding
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
        this@HomeFragment.spendings.clear()
        spendings.addAll(SpendingModel.getData()!!)
        spendingAdapter.notifyDataSetChanged()
    }

    /**
     * Update topics and recent articles if there is a change in the database.
     */
    override fun update(p0: Observable?, p1: Any?) {
        this@HomeFragment.spendings.clear()

        val spendingData = SpendingModel.getData()!!

        if (spendingData != null) {
            spendings.addAll(spendingData)
        }

        Log.i("HELPMIJDAN", spendings.size.toString())
        spendingAdapter.notifyDataSetChanged()
    }

    private fun onClickSpending(spending: Spending) {
        TODO("Not yet implemented")
    }
}