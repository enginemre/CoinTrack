package com.engin.cointrack.feature.home.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.engin.cointrack.R
import com.engin.cointrack.databinding.ItemCoinBinding
import com.engin.cointrack.core.domain.model.Coin

class CoinRecyclerviewAdapter(
    private val coinList: MutableList<Coin>,
    private val onCoinClick: (Coin) -> Unit
) : RecyclerView.Adapter<CoinRecyclerviewAdapter.CoinItemViewHolder>() {

    inner class CoinItemViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Coin) {
            binding.coinSymbolText.text = data.symbol
            binding.coinNameText.text = data.name
            Glide.with(binding.coinImage.context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_coin_dummy)
                .into(binding.coinImage)
            binding.root.setOnClickListener { onCoinClick(data) }
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
        holder.bind(coinList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(coinList : List<Coin>){
        this.coinList.clear()
        this.coinList.addAll(coinList)
        notifyDataSetChanged()
    }
}