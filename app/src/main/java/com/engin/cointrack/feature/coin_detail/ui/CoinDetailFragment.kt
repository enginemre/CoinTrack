package com.engin.cointrack.feature.coin_detail.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.engin.cointrack.R
import com.engin.cointrack.core.util.ProgressDialog
import com.engin.cointrack.core.util.hideKeyboard
import com.engin.cointrack.databinding.FragmentCoinDetailBinding
import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CoinDetailViewModel.Factory

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!

    private val args: CoinDetailFragmentArgs by navArgs()


    private val viewModel: CoinDetailViewModel by viewModels {
        CoinDetailViewModel.provideCoinDetailViewModel(viewModelFactory, args.id)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getCoinDetail()
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        observeUIActions()
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerRunnable()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unRegisterRunnable()
    }

    private fun observeUIActions() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.coinDetail.collect {
                        handleCoinDetail(it)
                    }
                }
                launch {
                    viewModel.isLoading.collectLatest {
                        handleLoading(it)
                    }
                }
                launch {
                    viewModel.snackBar.collectLatest {
                        showSnackBar(it)
                    }
                }
                launch {
                    viewModel.error.collectLatest {
                        handleError(it)
                    }
                }
            }
        }
    }

    private fun handleCoinDetail(data: CoinDetail?) {
        data?.let { bindCoinDetail(it) }
            ?: run { shouldShowErrorView(true) }
    }

    private fun shouldShowErrorView(isError :Boolean) {
        // TODO hide component and show error view
        val status = if (isError) View.GONE else View.VISIBLE
        binding.rowHash.visibility = status
        binding.button.visibility = status
        binding.coinImage.visibility = status
        binding.description.visibility = status
        binding.descriptionLabel.visibility = status
        binding.intervalInputLayout.visibility = status
        binding.currentPriceText.visibility = status
        binding.linearLayout.visibility = status
        binding.currentPriceText.visibility = status
        binding.emptyView.visibility = if (isError) View.VISIBLE else View.GONE
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(throwable.message ?: getString(R.string.error_occured),getString(R.string.retry)){
            viewModel.getCoinDetail()
        }
        shouldShowErrorView(true)
    }

    private fun showSnackBar(message: String,actionText : String? = null,action : (() -> Unit)? =null) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(actionText) {
                action?.invoke()
            }
            .setDuration(Snackbar.LENGTH_LONG)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.holo_red_dark
                )
            )
            .show()
    }


    private fun handleLoading(isLoading: Boolean) {
        if (isLoading)
            ProgressDialog.show(requireContext())
        else
            ProgressDialog.dismiss()
    }

    @SuppressLint("SetTextI18n")
    private fun bindCoinDetail(data: CoinDetail) {
        shouldShowErrorView(false)
        with(binding) {
            val priceText = getString(R.string.priceText)
            currentPriceText.text =
                String.format(priceText, data.marketData.currentPrice.toString().dropLast(2))
            Glide.with(this@CoinDetailFragment)
                .load(data.imageUrl)
                .error(R.drawable.ic_coin_dummy)
                .placeholder(R.drawable.ic_placeholder)
                .into(coinImage)
            priceChange24h.text =
                "%" +  data.marketData.priceChangePercentage24h.toString()
            if (data.description.isEmpty()) {
                descriptionLabel.visibility = View.GONE
                description.visibility = View.GONE
            } else {
                description.text = data.description
            }
            if (data.hashingAlgorithm.isEmpty()) {
                rowHash.visibility = View.GONE
            } else
                hashingAlgorithm.text = data.hashingAlgorithm
        }
    }

    private fun bindUI() {
        bindIntervalLayout()
        bindAddFavourite()
    }

    private fun bindIntervalLayout() {
        binding.intervalInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.updateInterval(binding.intervalInput.text.toString())
                binding.intervalInput.text?.clear()
                binding.intervalInput.hideKeyboard()
                binding.intervalInput.clearFocus()
            }
            true
        }
    }

    private fun bindAddFavourite() {
        binding.button.setOnClickListener {
            viewModel.addFavourite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}