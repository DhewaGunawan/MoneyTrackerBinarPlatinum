package com.example.binarchplatinum.pkg.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.binarchplatinum.R
import com.example.binarchplatinum.constant.CategoryConstant
import com.example.binarchplatinum.data.room.model.ExpensesWithCategory
import com.example.binarchplatinum.databinding.ItemSingleTransactionBinding
import com.example.binarchplatinum.pkg.home.ui.transactionlist.DeleteExpenseListener
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TransactionListAdapter() :
    RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>() {

    private var transactionList: MutableList<ExpensesWithCategory> = mutableListOf()
    var deleteListener: DeleteExpenseListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding =
            ItemSingleTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.bindView(transactionList[position])
        holder.getCategoryIcon(transactionList[position].category.categoryName)
        holder.deleteItem(transactionList[position].expenses.id, deleteListener)
    }

    class TransactionListViewHolder(private val binding: ItemSingleTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ExpensesWithCategory) {
            binding.tvTransactionTitle.text = item.expenses.name
            val formatPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            binding.tvTransactionAmount.text = formatPrice.format(item.expenses.price)
            val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            binding.tvTransactionDate.text = formatDate.format(item.expenses.date)
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

        fun deleteItem(id: Int, listener: DeleteExpenseListener?) {
            binding.cvIcDelete.setOnClickListener {
                listener.let {
                    if (listener !== null) {
                        listener.onClickDelete(id)
                    }
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

