package com.example.ecommerce.ui.wishlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentWishListBinding
import com.example.ecommerce.ui.cart.CartAdapter
import com.example.ecommerce.ui.check_out_item.CheckOutItem
import com.example.ecommerce.ui.check_out_item.CheckOutViewModel


class WishListFragment : Fragment(), WishListAdapter.ItemClickListener {


    private lateinit var binding: FragmentWishListBinding

    private val wishListAdapter: WishListAdapter by lazy { WishListAdapter(arrayListOf(), this) }

    private val viewModel by viewModels<WishListViewModel>()

    private fun init() {

        binding.wishListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.wishListRecyclerView.adapter = wishListAdapter


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWishListBinding.inflate(inflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getData()
    }
    private fun getData() {
        val data = viewModel.readWishListItem(requireContext())
        Log.d("adapterGetData", "get the data from retro $data")
        wishListAdapter.updateList(data)


    }

    override fun onItemDeleted(item: WishListEntity, position: Int) {

        viewModel.deleteWishListItem(
            wishListEntity = item,
            context = requireContext()
        )
        getData()


    }



}