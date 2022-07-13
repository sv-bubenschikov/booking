package com.example.bookingapp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.fragment_container_view)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        visibilityNavElements(navController)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null)
            navController.navigate(R.id.action_navigation_home_to_navigation_sign_in)
        else {
            currentUser.getIdToken(true).addOnCompleteListener {
                if(!it.isSuccessful) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_sign_in)
                }
            }
        }
    }

    /**
     * определяет на каких фрагментах показывать toolbar
     */
    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_sign_in,
                R.id.navigation_register -> binding.toolbar.visibility = View.GONE
                else -> binding.toolbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}