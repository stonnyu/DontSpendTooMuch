package com.example.dontspendtoomuch.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dontspendtoomuch.DataModel.CategoryModel
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.R
import com.example.dontspendtoomuch.databinding.FragmentAddSpendingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat

private const val TAG: String = "AddSpendingFragment"
private const val MIN_VALUE: Int = 1
private const val MIN_YEARS: Int = 2015
private const val MAX_DAYS: Int = 31
private const val MAX_MONTHS: Int = 12
private const val MAX_YEARS: Int = 2025
private const val DATE_PATTERN: String = "d-M-yyyy"

class AddSpendingFragment : Fragment() {

    private lateinit var binding: FragmentAddSpendingBinding
    private var database = FirebaseDatabase.getInstance()
    private var spendingReference = database.getReference("Spending")
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
        setupNumberPicker()

        binding.btnAddSpending.setOnClickListener {

            // Confirmation dialog to check if user wants to add this spending
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setMessage(getString(R.string.add_spending_dialog))
            alertDialog.setNegativeButton(getString(R.string.add_spending_cancel)) { _, _ -> }
            alertDialog.setPositiveButton(getString(R.string.add_spending_confirm)) { _, _ ->
                addSpending()
                findNavController().navigate(R.id.navigation_home)
            }
            alertDialog.create().show()
        }
    }

    private fun addSpending() {
        val spending = Spending(null)
        val newCategorySpending: DatabaseReference
        val newSpending = spendingReference.child("").push()

        // Gets user input
        spending.spendingTitle = binding.etSpendingTitle.text.toString()
        spending.spendingDate = dateInputFormat()
        spending.spendingAmount = binding.etSpendingAmount.text.toString().toDouble()

        // Pushes user input to Spending table
        newSpending.child("spendingTitle").setValue(spending.spendingTitle)
        newSpending.child("spendingDate").setValue(spending.spendingDate)
        newSpending.child("spendingAmount").setValue(spending.spendingAmount)
        newSpending.child("spendingCategory").setValue(category)

        newCategorySpending = database
                .getReference("Category")
                .child(category!!)
                .child("spendings")

        // Pushes user input to Category table
        newCategorySpending.child(newSpending.key.toString()).setValue(spending.spendingTitle)
    }

    /**
     * Formats user date input into timestamp
     */
    private fun dateInputFormat(): Long {
        val dateString: String = binding.npDay.value.toString() + "-" +
                binding.npMonth.value.toString() + "-" +
                binding.npYear.value.toString()

        return SimpleDateFormat(DATE_PATTERN).parse(dateString).time
    }

    /**
     * Fills the dropdown menu with categories
     */
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

    private fun setupNumberPicker() {
        binding.npDay.minValue = MIN_VALUE
        binding.npDay.maxValue = MAX_DAYS

        binding.npMonth.minValue = MIN_VALUE
        binding.npMonth.maxValue = MAX_MONTHS

        binding.npYear.minValue = MIN_YEARS
        binding.npYear.maxValue = MAX_YEARS
    }
}