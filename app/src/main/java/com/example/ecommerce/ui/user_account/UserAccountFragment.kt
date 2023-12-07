package com.example.ecommerce.ui.user_account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R

import com.example.ecommerce.databinding.FragmentUserAccountBinding
import com.example.ecommerce.session.SharedPreferencesManager
import com.example.ecommerce.ui.login.LoginActivity
import com.example.ecommerce.ui.signup.SignUpActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class UserAccountFragment : Fragment() {


    private lateinit var binding: FragmentUserAccountBinding

    lateinit var navController: NavController

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private lateinit var myadapter: viewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferencesManager = SharedPreferencesManager(requireContext())
        navController = findNavController()

        binding.logouticon.setOnClickListener {

            sharedPreferencesManager.clearLoggedIn()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }

//        binding.orderHistoryPage.setOnClickListener {
//            navController = findNavController()
//            findNavController().navigate(R.id.action_wishListFragment_to_orderHistoryFragment)
//        }

        myadapter = viewPagerAdapter(childFragmentManager,lifecycle)

        viewPager2 = binding.viewPager
        tabLayout = binding.tabLayout

        // Set up ViewPager2 with the adapter
        viewPager2.adapter = myadapter

        // Use TabLayoutMediator to link TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Wishlist"
                1 -> tab.text = "Order History"
            }
        }.attach()




    }


}



//tabLayout =binding.tabLayout
//viewPager2=binding.viewPager
//
//tabLayout.addTab(tabLayout.newTab().setText("first"))
//tabLayout.addTab(tabLayout.newTab().setText("second"))
//
//viewPager2.adapter = myadapter
//
//tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//    override fun onTabSelected(tab: TabLayout.Tab?) {
//        if (tab != null) {
//            viewPager2.currentItem =tab.position
//        }
//    }
//
//    override fun onTabUnselected(tab: TabLayout.Tab?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onTabReselected(tab: TabLayout.Tab?) {
//        TODO("Not yet implemented")
//    }
//
//})
//
//viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
//    override fun onPageSelected(position: Int) {
//        super.onPageSelected(position)
//        tabLayout.selectTab(tabLayout.getTabAt(position))
//    }
//})