package com.example.financetrackerapp_ctis487_team6.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.financetrackerapp_ctis487_team6.R
import com.example.financetrackerapp_ctis487_team6.db.Customer


class CustomRecyclerViewAdapter(private val context: Context, private val recyclerItemValues: MutableList<Customer>) :
    RecyclerView.Adapter<CustomRecyclerViewAdapter.RecyclerViewItemHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.item_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        myRecyclerViewItemHolder.tvItemCustomerId.text = item.id.toString()
        myRecyclerViewItemHolder.tvItemCustomerName.text = item.name
        myRecyclerViewItemHolder.tvItemCustomerQuantity.text = item.debt.toString()
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var parentLayout: LinearLayout
        lateinit var tvItemCustomerName: TextView
        lateinit var tvItemCustomerId: TextView
        lateinit var tvItemCustomerQuantity: TextView
        init {
            parentLayout = itemView.findViewById(R.id.itemLayout)
            tvItemCustomerId = itemView.findViewById(R.id.tvItemCustomerId)
            tvItemCustomerName = itemView.findViewById(R.id.tvItemCustomerName)
            tvItemCustomerQuantity = itemView.findViewById(R.id.tvItemCustomerQuantity)
        }
    }
}
