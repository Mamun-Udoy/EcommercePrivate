package com.example.ecommerce

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.ui.check_out_item.CheckOutItemInsertViewModel
import com.example.ecommerce.ui.check_out_item.CheckOutViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val badge: BadgeDrawable by lazy { BadgeDrawable.create(this) }

    lateinit var navController: NavController

    private val checkOutViewModel by viewModels<CheckOutViewModel> ()

    private val badgeValueViewModel by viewModels<CheckOutItemInsertViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataCount()
        setSupportActionBar(binding.topAppBar)

        badgeValueViewModel.dbSize.observe(this) { size ->
            Log.d("observer11","mainactivity value ${size}")
            updateBadge(size)
        }

        navController = findNavController(R.id.mainfragment)

        bottombar()

        topappbar()
    }

    private fun getDataCount() {
        val count = checkOutViewModel.getCheckoutItemsSize(this)
        updateBadge(count?:0)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        BadgeUtils.attachBadgeDrawable(badge, binding.topAppBar, R.id.fav)
        return super.onCreateOptionsMenu(menu)

    }

    private fun updateBadge(size: Int) {
        // Update the badge with the new database size
        badge.number = size
        Log.d("badgeValue", "check badge value ${badge.number}")
        invalidateOptionsMenu()
    }

    fun topappbar() {

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav -> {
//                    var navController: NavController = findNavController(R.id.mainfragment)
//                    navController.navigateTo(R.id.checkOut)

                    //val navController: NavController = findNavController(R.id.mainfragment)
                    navController.navigate(R.id.cartFragment)
                    true
                }


                else -> false
            }
        }

    }


    private fun bottombar() {

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigateTo(R.id.homeFragment)
                    true
                }

                R.id.person -> {

                    navController.navigate(R.id.userAccount)
                    true
                }
                else -> false
            }
        }


    }
}