package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dontspendtoomuch.DataModel.CategoryModel
import com.example.dontspendtoomuch.databinding.FragmentAddSpendingBinding
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import java.util.*

private const val TAG: String = "AddSpendingFragment"

class AddSpendingFragment : Fragment() {

    private lateinit var binding: FragmentAddSpendingBinding
    private var category: String? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddSpendingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSpinner()
    }

    private fun loadSpinner() {
        val dynamicSpinner = binding.dynamicSpinner

        val categories = CategoryModel.getData()!!

        dynamicSpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categories
        )

        dynamicSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long,
            ) {
                category = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "No item selected in spinner.")
            }
        }
    }
}