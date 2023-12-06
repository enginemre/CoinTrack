package com.engin.cointrack.feature.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engin.cointrack.databinding.ItemCoinBinding

class CoinRecyclerviewAdapter(
    private val coinList: MutableList<Any>,
) : RecyclerView.Adapter<CoinRecyclerviewAdapter.CoinItemViewHolder>() {


    inner class CoinItemViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        return CoinItemViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = coinList.size

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(coinList : List<Any>){
        this.coinList.clear()
        this.coinList.addAll(coinList)
        notifyDataSetChanged()
    }
}