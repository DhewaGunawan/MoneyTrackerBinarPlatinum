package com.example.binarchplatinum.ui.transactionlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.binarchplatinum.R
import com.example.binarchplatinum.constant.CategoryConstant
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.data.room.model.CategoryWithExpenses
import com.example.binarchplatinum.data.room.model.CountAndSumExpenses
import com.example.binarchplatinum.databinding.ItemGroupTransactionBinding

class GroupTransactionListAdapter() :
    RecyclerView.Adapter<GroupTransactionListAdapter.TransactionListViewHolder>() {

    private var groupedTransactionList: MutableList<CategoryWithExpenses> = mutableListOf()
    private var totalItemAndPrice: MutableList<CountAndSumExpenses> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding =
            ItemGroupTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.bindView(groupedTransactionList[position])
        holder.getCategoryIcon(groupedTransactionList[position].category.categoryName)
        holder.getTotalPrice(groupedTransactionList[position], totalItemAndPrice[0].sum.toInt())
    }

    class TransactionListViewHolder(private val binding: ItemGroupTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: CategoryWithExpenses) {
            binding.tvCategory.text = item.category.categoryName
            binding.tvTotalTransaction.text = "${item.expenses.size.toString()} transactions"
            val totalPrice = getTotalPriceByCategory(item.expenses)
            binding.tvTransactionTotalAmount.text = totalPrice.toString()
        }

        fun getCategoryIcon(name: String) {
            if (name == CategoryConstant.SPORT) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_sport)
            } else if (name == CategoryConstant.ENTERTAINMENT) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_entertainment)
            } else if (name == CategoryConstant.FOOD_AND_DRINK) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_food_drink)
            } else if (name == CategoryConstant.GROCERIES) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_groceries)
            } else if (name == CategoryConstant.HOME_BILLS) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_home_bills)
            } else if (name == CategoryConstant.TRANSPORTATION) {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_transportation)
            } else {
                binding.ivIcCategory.setImageResource(R.drawable.ic_cat_shopping)
            }
        }

        fun getTotalPriceByCategory(expenses: List<Expenses>): Int {
            var total = 0
            for (item in expenses) {
                total += (item.price).toInt()
            }
            return total
        }

        fun getTotalPrice(item: CategoryWithExpenses, data: Int?) {
            data?.let {
                if (data !== null) {
                    val percentage =
                        (getTotalPriceByCategory(item.expenses).toDouble() / data.toDouble()) * 100
                    binding.tvTransactionPercentage.text = "${Math.round(percentage)}%"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return groupedTransactionList.size
    }

    fun getTotalItemCount(data: CountAndSumExpenses?) {
        data?.let {
            if (data !== null) {
                totalItemAndPrice.clear()
                totalItemAndPrice.add(data)
                notifyDataSetChanged()
            }
        }

    }

    fun setGroupedData(list: List<CategoryWithExpenses>) {
        groupedTransactionList.clear()
        groupedTransactionList.addAll(list)
        notifyDataSetChanged()
    }

}

