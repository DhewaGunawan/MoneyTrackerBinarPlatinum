package com.example.binarchplatinum.pkg.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.binarchplatinum.R
import com.example.binarchplatinum.constant.CategoryConstant
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.databinding.ItemSingleTransactionBinding
import java.text.NumberFormat
import java.util.*

class TransactionListAdapter() :
    RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>() {

    private var transactionList: MutableList<ExpensesWithCategory> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding =
            ItemSingleTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.bindView(transactionList[position])
        holder.getCategoryIcon(transactionList[position].category.categoryName)
    }

    class TransactionListViewHolder(private val binding: ItemSingleTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ExpensesWithCategory) {
            binding.tvTransactionTitle.text = item.expenses.name
            val formatPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            binding.tvTransactionAmount.text = formatPrice.format(item.expenses.price)
            binding.tvTransactionDate.text = item.expenses.date.toString()
            binding.tvCategory.text = item.category.categoryName
        }

        fun getCategoryIcon(name: String) {
            when (name) {
                CategoryConstant.SPORT -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_sport)
                }
                CategoryConstant.ENTERTAINMENT -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_entertainment)
                }
                CategoryConstant.FOOD_AND_DRINK -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_food_drink)
                }
                CategoryConstant.GROCERIES -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_groceries)
                }
                CategoryConstant.HOME_BILLS -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_home_bills)
                }
                CategoryConstant.TRANSPORTATION -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_transportation)
                }
                else -> {
                    binding.ivCategory.setImageResource(R.drawable.ic_cat_shopping)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    fun setData(list: List<ExpensesWithCategory>) {
        transactionList.clear()
        transactionList.addAll(list)
        notifyDataSetChanged()
    }

}

