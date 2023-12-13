package com.example.ecommerce.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentWishListBinding
import com.example.ecommerce.db.entities.WishListEntity
import com.example.ecommerce.utils.toCheckOutItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WishListFragment : Fragment(), WishListAdapter.ItemClickListener, WishListAdapter.AddtoCart {


    private lateinit var binding: FragmentWishListBinding

    private val wishListAdapter: WishListAdapter by lazy {
        WishListAdapter(
            this,
            this
        )
    }

    private val viewModel by viewModels<WishListViewModel>()

    private val viewModel2 by viewModels<CheckOutViewModel>()


    private val viewModelCheckOutItemInsertViewModel by activityViewModels<CheckOutItemInsertViewModel>()


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

    override fun onAddToCart(item: WishListEntity, position: Int) {

        val addtocart = item.toCheckOutItem()

        viewModel2.insertCheckoutItem(
            checkOutItemEntity = addtocart,
            context = requireContext()
        )
        val updatedSize = context?.let { it1 -> viewModel2.getCheckoutItemsSize(it1) }
        if (updatedSize != null) {
            viewModelCheckOutItemInsertViewModel.updateDatabaseSize(updatedSize)
        }

    }


}