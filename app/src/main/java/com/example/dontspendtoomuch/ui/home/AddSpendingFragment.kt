package com.example.dontspendtoomuch.ui.home

import android.app.AlertDialog
import android.os.Bundle
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
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

private const val TAG: String = "AddSpendingFragment"

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

        binding.btnAddSpending.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setMessage("warning")
            alertDialog.setNegativeButton("no") { _, _ -> }
            alertDialog.setPositiveButton("yes") { _, _ ->
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

        spending.spendingTitle = binding.etSpendingTitle.text.toString()
        spending.spendingDate = dateInputFormat()
        spending.spendingAmount = binding.etSpendingAmount.text.toString().toDouble()

        newSpending.child("spendingTitle").setValue(spending.spendingTitle)
        newSpending.child("spendingDate").setValue(spending.spendingDate)
        newSpending.child("spendingAmount").setValue(spending.spendingAmount)
        newSpending.child("spendingCategory").setValue(category)

        newCategorySpending = database
                .getReference("Category")
                .child(category!!)
                .child("spendings")
        newCategorySpending.child(newSpending.key.toString()).setValue(spending.spendingTitle)
    }

    private fun dateInputFormat(): Date {
        val dateString: String = binding.tilSpendingDay.editText?.text.toString() +
                binding.tilSpendingMonth.editText?.text.toString() +
                binding.tilSpendingYear.editText?.text.toString()

        return SimpleDateFormat("dMyyyy").parse(dateString)
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