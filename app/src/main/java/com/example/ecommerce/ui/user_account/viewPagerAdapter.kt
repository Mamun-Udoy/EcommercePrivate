package com.example.ecommerce.ui.user_account

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle
import com.example.ecommerce.ui.order_history.OrderHistoryFragment
import com.example.ecommerce.ui.wishlist.WishListFragment

class viewPagerAdapter(fragmentManager: FragmentManager,lifecycle: androidx.lifecycle.Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
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