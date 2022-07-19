package com.example.bookingapp.app

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val hostViewModel by viewModels<HostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionButton.setOnClickListener {
            hostViewModel.onActionButtonClicked()
        }
        lifecycleScope.launch {
            hostViewModel.actionButtonVisible.flowWithLifecycle(lifecycle).collect { isVisible ->
                if (isVisible)
                    binding.actionButton.show()
                else
                    binding.actionButton.hide()
            }
        }

        //issue #10 https://github.com/sv-bubenschikov/booking/projects/1#card-84219457
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        visibilityNavElements(navController)

        setupActionBarWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * определяет на каких фрагментах скрывать actionBar и floating button
     */
    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_sign_in,
                R.id.navigation_register -> { supportActionBar?.hide(); hostViewModel.setActionButtonVisible(false) } // почему actionButton не скрывается? даже по id view.Gone не работает
                else -> supportActionBar?.show()
            }
        }
    }
}