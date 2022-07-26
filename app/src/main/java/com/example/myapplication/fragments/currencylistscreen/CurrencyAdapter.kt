package com.example.myapplication.fragments.currencylistscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCurrencyBinding
import com.example.myapplication.db.CurrencyEntity

class CurrencyAdapter(
    private val listener: OnCurrencyClickListener
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var listCurrency: MutableList<CurrencyEntity> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<CurrencyEntity>) {
        this.listCurrency = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class CurrencyViewHolder(val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.currencyItem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = listCurrency[position]
                listener.onCurrencyClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = listCurrency[position]
        holder.binding.apply {
            itemCode.text = item.symbol
            itemName.text = item.id
        }
    }

    override fun getItemCount(): Int {
        return listCurrency.size
    }

    interface OnCurrencyClickListener {
        fun onCurrencyClick(id: String)
    }
}