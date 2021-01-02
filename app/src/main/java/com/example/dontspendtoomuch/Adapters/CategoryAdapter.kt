package com.example.dontspendtoomuch.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dontspendtoomuch.DataModel.Category
import com.example.dontspendtoomuch.R
import com.example.dontspendtoomuch.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val clickListener: (Category) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemCategoryBinding.bind(itemView)

        fun databind(category: Category, clickListener: (Category) -> Unit) {
            binding.tvCategoryTitle.text = category.categoryTitle
            itemView.setOnClickListener { clickListener(category) }

        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return categories.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(categories[position], clickListener)
    }
}