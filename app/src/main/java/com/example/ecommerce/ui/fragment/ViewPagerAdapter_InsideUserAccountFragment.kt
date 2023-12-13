package com.example.ecommerce.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter_InsideUserAccountFragment(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0){
            WishListFragment()
        }
        else{
            OrderHistoryFragment()
        }
    }
}