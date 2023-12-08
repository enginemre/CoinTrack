package com.engin.cointrack.feature.favourite.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.engin.cointrack.R
import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.databinding.ItemCoinBinding

class FavouriteRecyclerviewAdapter(
    private val favouriteCoins: MutableList<Coin>,
    private val onFavouriteClickListener: (Coin) -> Unit,
    private val onCoinClickListener: (Coin) -> Unit
) : RecyclerView.Adapter<FavouriteRecyclerviewAdapter.FavouriteCoinItemViewHolder>() {

    inner class FavouriteCoinItemViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Coin, position: Int) {
            binding.arrowRight.visibility = View.GONE
            binding.coinFavouriteImage.visibility = View.VISIBLE
            binding.coinSymbolText.updateLayoutParams<ConstraintLayout.LayoutParams> {
                endToStart = binding.coinFavouriteImage.id
            }
            Glide.with(binding.coinImage.context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_coin_dummy)
                .into(binding.coinImage)
            binding.coinFavouriteImage.isActivated = data.isFavourite
            binding.coinSymbolText.text = data.symbol
            binding.coinNameText.text = data.name
            binding.coinFavouriteImage.setOnClickListener {
                onFavouriteClickListener(data)
            }
            binding.root.setOnClickListener { onCoinClickListener(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCoinItemViewHolder {
        return FavouriteCoinItemViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = favouriteCoins.size

    override fun onBindViewHolder(holder: FavouriteCoinItemViewHolder, position: Int) {
        holder.bind(favouriteCoins[position], position)
    }

    fun removeItem(coin: Coin) {
        val index = favouriteCoins.indexOf(coin)
        favouriteCoins.remove(coin)
        notifyItemRemoved(index)
    }

    fun updateList(list: List<Coin>) {
        favouriteCoins.clear()
        favouriteCoins.addAll(list)
        notifyDataSetChanged()
    }

}