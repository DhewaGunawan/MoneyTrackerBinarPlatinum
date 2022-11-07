package com.example.binarchplatinum.ui.transactionlist.adapter

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
            binding.tvTransactionDate.text = item.expenses.date
            binding.tvCategory.text = item.category.categoryName
        }

        fun getCategoryIcon(name: String) {
            if (name == CategoryConstant.SPORT) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_sport)
            } else if (name == CategoryConstant.ENTERTAINMENT) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_entertainment)
            } else if (name == CategoryConstant.FOOD_AND_DRINK) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_food_drink)
            } else if (name == CategoryConstant.GROCERIES) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_groceries)
            } else if (name == CategoryConstant.HOME_BILLS) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_home_bills)
            } else if (name == CategoryConstant.TRANSPORTATION) {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_transportation)
            } else {
                binding.ivCategory.setImageResource(R.drawable.ic_cat_shopping)
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

