package com.example.ecommerce.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
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


    var totalPrice = 0.0f

    var cartItemUpdated: List<CheckOutItem> = arrayListOf()

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


        binding.checkoutbutton.setOnClickListener {
            requireView().findNavController().navigate(R.id.checkOutFragment)
        }


    }


    private fun getData() {
        val data = viewModel.readCheckoutItem(requireContext())
        Log.d("adapterGetData", "get the data from retro $data")
        cartAdapter.updateList(data)


        cartItemUpdated = data

        cartAdapter.updateList(cartItemUpdated)

        val totalPrice = totalCost()

        Log.d("adapterGetData", "Total Price: $totalPrice")


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
        if (cartAdapter.itemCount < 1) {
            requireView().findNavController().navigate(R.id.homeFragment)
        }
    }

    var count = 1
    override fun increment(item: CheckOutItem, position: Int) {
//        var save = item.discount?.let {
//            item.price?.toFloat()?.div(100)?.times(item!!.discount!!.toFloat())
//        }
//
//        var discountedPrice = save?.let { item?.price?.minus(it) }
//        count++
//        if (count < 11) {
//            totalPrice += discountedPrice!!
//            binding.totalCost.text = "Total: " + totalPrice.toString()
//
//        } else
//            count--

        totalCost()


    }

    override fun decrement(item: CheckOutItem, position: Int) {
//        var save = item.discount?.let {
//            item.price?.toFloat()?.div(100)?.times(item!!.discount!!.toFloat())
//        }
//
//        var discountedPrice = save?.let { item?.price?.minus(it) }
//        count--
//        if (count > 0) {
//            totalPrice -= discountedPrice!!
//            binding.totalCost.text = "Total: " + totalPrice.toString()
//        }
//        else count++
        totalCost()


    }


    private fun totalCost() {

//        for (item in items) {
//            val itemPrice = item.price?.toFloat() ?: 0.0f
//            val itemDiscount = item.discount?.toFloat() ?: 0.0f
//
//            val discountAmount = (itemPrice / 100) * itemDiscount
//            val discountedPrice = itemPrice - discountAmount
//
//            totalPrice += discountedPrice
//        }
//
//        binding.totalCost.text = totalPrice.toString()
//        Log.d("totalprice", "totalCost: ${totalPrice}")
//        Log.d("checkoutitem", "item size ")

        var totalPrice = 0.0f

        // Assuming viewModel.readCheckoutItem returns a list of items
        val data = viewModel.readCheckoutItem(requireContext())

        for (item in data) {
            val save = item.discount?.let {
                item.price?.toFloat()?.div(100)?.times(item.discount!!.toFloat())
            }

            val discountedPrice = save?.let { item.price?.minus(it) }
            val count = cartAdapter.getCountMap().getOrDefault(data.indexOf(item), 1)

            totalPrice += discountedPrice!! * count
        }
        binding.totalCost.text= "Total: "+"${formatToTwoDecimalPlaces(totalPrice)}"
    }

    fun formatToTwoDecimalPlaces(value: Float?): String? {
        return value?.let {
            String.format("%.2f", it)
        }
    }


}