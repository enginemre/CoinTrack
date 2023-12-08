package com.engin.cointrack.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.engin.cointrack.R
import com.engin.cointrack.core.ui.SharedViewModel
import com.engin.cointrack.core.util.Destinations
import com.engin.cointrack.core.util.ProgressDialog
import com.engin.cointrack.core.util.handleLoading
import com.engin.cointrack.core.util.showSnackBar
import com.engin.cointrack.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        observeUIAction()
    }

    private fun bindUI() {
        navController = findNavController()
        binding.login.setOnClickListener {
            viewModel.login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    private fun observeUIAction(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.isLoading.collectLatest {
                        requireContext().handleLoading(it)
                    }
                }
                launch {
                    viewModel.error.collectLatest {
                        handleError(it)
                    }
                }
                launch {
                    viewModel.loggedIn.collectLatest {
                        navController.navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        )
                    }
                }
            }
        }
    }
    private fun handleError(message: String) {
        showSnackBar(binding.root,message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}