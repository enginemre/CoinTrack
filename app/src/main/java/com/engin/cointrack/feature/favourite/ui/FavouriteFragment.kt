package com.engin.cointrack.feature.favourite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.engin.cointrack.R
import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.core.ui.SharedViewModel
import com.engin.cointrack.core.util.Destinations
import com.engin.cointrack.core.util.ProgressDialog
import com.engin.cointrack.core.util.showSnackBar
import com.engin.cointrack.databinding.FragmentFavouriteBinding
import com.engin.cointrack.feature.favourite.ui.adapter.FavouriteRecyclerviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouriteViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private lateinit var navController: NavController

    private val favouriteAdapter by lazy {
        FavouriteRecyclerviewAdapter(
            mutableListOf(),
            onFavouriteClickListener = {
                viewModel.removeFavourite(it)
            },
            onCoinClickListener = {
                sharedViewModel.navigate(Destinations.CoinDetail(it.id,it.name))
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllFavouriteItems()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        observeUIAction()
    }

    private fun observeUIAction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.favouriteList.collectLatest {
                        handleList(it)
                    }
                }
                launch {
                    viewModel.isLoading.collectLatest {
                        handleLoading(it)
                    }
                }
                launch {
                    viewModel.error.collectLatest {
                        handleError(it)

                    }
                }
                launch {
                    viewModel.removeFavourite.collectLatest {
                        favouriteAdapter.removeItem(it)
                    }
                }
            }
        }
    }

    private fun handleList(list: List<Coin>) {
        if (list.isEmpty()){
            shouldShowError(true)
        }
        else{
            shouldShowError(false)
            favouriteAdapter.updateList(list)
        }
        binding.swiperefresh.isRefreshing =false
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading)
            ProgressDialog.show(requireContext())
        else
            ProgressDialog.dismiss()
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(binding.root, throwable.message ?: getString(R.string.error_occured))
        shouldShowError(true)
        binding.swiperefresh.isRefreshing =false
    }

    private fun shouldShowError(isError :Boolean){
        binding.emptyView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.favouriteRecyclerview.visibility = if (isError) View.GONE else View.VISIBLE
    }

    private fun bindUI() {
        navController = findNavController()
        binding.favouriteRecyclerview.adapter = favouriteAdapter
        binding.swiperefresh.setOnRefreshListener {
            viewModel.getAllFavouriteItems(
                shouldSilently  = true
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}