package com.example.water_nn.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.ActivityMainBinding
import com.example.water_nn.presentation.main.mainscreen.address.ActionBottom
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main), ToolbarElementControl {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBar(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun setAddressDeliveryView() {
        with(binding.appBarMain.addressDelivery) {
            visibility = View.VISIBLE
            setListener {
                openBottomSheetDialog()
                Toast.makeText(this@MainActivity, "Toolbar clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openBottomSheetDialog() {

        val bottomSheetDialog = ActionBottom.newInstance()
        bottomSheetDialog.show(supportFragmentManager, ActionBottom.TAG )
    }

    override fun removeAddressDeliveryView() {
        binding.appBarMain.addressDelivery.visibility = View.GONE
    }

    override fun showActionBar() {
        binding.appBarMain.toolbar.visibility = View.VISIBLE
    }

    override fun hideActionBar() {
        binding.appBarMain.toolbar.visibility = View.GONE
    }
}