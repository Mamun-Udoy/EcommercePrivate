package com.example.ecommerce.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.ui.check_out_item.CheckOutItem
import com.example.ecommerce.ui.check_out_item.CheckOutItemInsertViewModel
import com.example.ecommerce.ui.check_out_item.CheckOutViewModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitViewModel
import com.example.ecommerce.ui.home_product_item.paging.PagingAdapter


class CartFragment : Fragment(), CartAdapter.ItemClickListener {

    private lateinit var binding: FragmentCartBinding

    private val cartAdapter: CartAdapter by lazy { CartAdapter(arrayListOf(), this) }

    private val viewModel by viewModels<CheckOutViewModel>()

    private val viewModelCheckOutItemDeleteViewModel by activityViewModels<CheckOutItemInsertViewModel>()

    private fun init() {

        binding.cartItemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartItemRecyclerView.adapter = cartAdapter


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        val updatedSize = context?.let { it1 -> viewModel.getCheckoutItemsSize(it1) }
        if (updatedSize != null) {
            viewModelCheckOutItemDeleteViewModel.updateDatabaseSize(updatedSize)
        }
    }

    private fun getData() {
        val data = viewModel.readCheckoutItem(requireContext())
        Log.d("adapterGetData", "get the data from retro $data")
        cartAdapter.updateList(data)


    }

    override fun onItemDeleted(item: CheckOutItem, position: Int) {

        viewModel.deleteChekcoutItem(
            checkOutItem = item,
            context = requireContext()
        )
        getData()
        val updatedSize = context?.let { it1 -> viewModel.getCheckoutItemsSize(it1) }
        if (updatedSize != null) {
            viewModelCheckOutItemDeleteViewModel.updateDatabaseSize(updatedSize)
        }
    }

//    private fun getCheckoutItem(item: RetrofitDataModel.Product): CheckOutItem {
//        return CheckOutItem(
//            itemId = item.id,
//            discount = item.discount.toString().toDouble(),
//            rating = item.rating.toString().toFloat(),
//            stock = item.stock,
//            brand = item.brand,
//            category = item.categories,
//            title = item.title
//
//
//        )
//    }


}