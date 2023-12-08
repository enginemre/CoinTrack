package com.engin.cointrack

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.engin.cointrack.core.ui.SharedViewModel
import com.engin.cointrack.core.util.Destinations
import com.engin.cointrack.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: SharedViewModel by viewModels()

    private val navDestinationChangeListener by lazy {
        NavController.OnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.coinDetailFragment -> {
                    hideBottomBar()
                    setToolbarTitle(args?.getString("name") ?: getString(R.string.coin_detail))
                }
                R.id.loginFragment -> hideBottomBar()
                else -> showBottomBar()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        arrangeLastPosition(savedInstanceState)
        observeUIActions()
    }

    private fun observeUIActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.destination.collectLatest { destination ->
                    when (destination) {
                        is Destinations.CoinDetail -> {
                            navController.navigate(
                                MainGraphDirections.actionGlobalCoinDetailFragment(
                                    destination.id,
                                    destination.name
                                )
                            )
                        }

                        is Destinations.Login -> {
                            navController.navigate(
                                MainGraphDirections.actionGlobalLoginFragment()
                            )
                        }

                        else -> {
                            // no-op
                        }
                    }
                }
            }
        }
    }

    private fun initializeNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.favouriteFragment,
                    R.id.homeFragment,
                    R.id.loginFragment
                )
            )
        setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(navDestinationChangeListener)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBundle("nav_state", navController.saveState())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navDestinationChangeListener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(navDestinationChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(navDestinationChangeListener)
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun arrangeLastPosition(savedInstanceState: Bundle?) {
        if (savedInstanceState?.getBundle("nav_state") != null) {
            navController.restoreState(savedInstanceState.getBundle("nav_state"))
        } else {
            if (viewModel.isUserLoggedIn())
                navController.graph.setStartDestination(R.id.homeFragment)
            else
                navController.graph.setStartDestination(R.id.loginFragment)
        }
    }

    private fun showBottomBar() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomBar() {
        binding.bottomNavigationView.visibility = View.GONE
    }


}