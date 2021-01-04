package com.example.dontspendtoomuch.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.DataModel.Spending
import com.example.dontspendtoomuch.R
import com.example.dontspendtoomuch.databinding.ItemSpendingBinding
import java.text.SimpleDateFormat

class SpendingAdapter(
    private val spendings: List<Spending>
) :
    RecyclerView.Adapter<SpendingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemSpendingBinding.bind(itemView)
        private val datePattern = "d MMMM yyyy"
        var simpleDateFormat = SimpleDateFormat(datePattern)

        fun databind(spending: Spending) {
            binding.tvSpendingTitle.text = spending.spendingTitle
            binding.tvSpendingDate.text = simpleDateFormat.format(spending.spendingDate)
            binding.tvSpendingAmount.text = String.format("€%.2f", spending.spendingAmount.toString()
                .toDouble())
                .replace(".", ",")
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_spending,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return spendings.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(spendings[position])
    }
}