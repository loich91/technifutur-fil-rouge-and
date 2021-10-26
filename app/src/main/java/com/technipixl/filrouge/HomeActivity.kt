package com.technipixl.filrouge

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.technipixl.filrouge.FoodFragment.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.technipixl.filrouge.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    var selectedHomeCardIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = Navigation.findNavController(this, binding.navHostFragment.id)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==LOCATION_PERMISSION_REQUEST_CODE && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            val fragment = supportFragmentManager.findFragmentById(R.id.foodFragment) as FoodFragment?
            fragment?.onPermissionGranted()
        }
    }
}