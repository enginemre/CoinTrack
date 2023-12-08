package com.engin.cointrack.feature.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.engin.cointrack.R
import com.engin.cointrack.core.ui.SharedViewModel
import com.engin.cointrack.core.util.Destinations
import com.engin.cointrack.core.util.ProgressDialog
import com.engin.cointrack.databinding.FragmentHomeBinding
import com.engin.cointrack.feature.home.ui.adapter.CoinRecyclerviewAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    private var coinAdapter: CoinRecyclerviewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCoins()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        observeUIAction()
    }

    private fun observeUIAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.coinList.collectLatest {
                        coinAdapter?.updateList(it)
                        showComponents()
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
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(throwable.localizedMessage ?: getString(R.string.error_occured))
        hideComponents()
    }

    private fun showComponents(){
        binding.searchView.visibility = View.VISIBLE
        binding.mainRecyclerView.visibility = View.VISIBLE
        binding.emptyView.visibility = View.GONE
    }

    private fun hideComponents(){
        binding.searchView.visibility = View.GONE
        binding.mainRecyclerView.visibility = View.GONE
        binding.emptyView.visibility = View.VISIBLE
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading)
            ProgressDialog.show(requireContext())
        else
            ProgressDialog.dismiss()
    }

    private fun bindUI() {
        bindRecyclerView()
        bindSearchView()
    }

    private fun bindSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isEmpty() == true) return false
                viewModel.getCoinsWithQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getCoinsWithQuery(newText)
                return false
            }
        })
        binding.searchView.setOnCloseListener {
            viewModel.getCoinsFromLocalDB()
            false
        }
    }

    private fun bindRecyclerView() {
        coinAdapter = CoinRecyclerviewAdapter(
            coinList = mutableListOf(),
            onCoinClick = {sharedViewModel.navigate(Destinations.CoinDetail(it.id,it.name)) })
        binding.mainRecyclerView.adapter = coinAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                viewModel.getCoins(true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}