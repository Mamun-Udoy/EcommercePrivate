package com.example.ecommerce.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.db.entities.CheckOutItemEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapter.ItemClickListener {

    private lateinit var binding: FragmentCartBinding

    private val cartAdapter: CartAdapter by lazy { CartAdapter( this) }

    private val viewModel by viewModels<CheckOutViewModel>()

    private val cartViewModel by viewModels<CartViewModel>()

//    private val viewModel2: CartViewModel by viewModels()// we also can decleare in this way



    private val viewModelCheckOutItemDeleteViewModel by activityViewModels<CheckOutItemInsertViewModel>()


    var totalPrice = 0.0f

    var cartItemUpdated: List<CheckOutItemEntity> = arrayListOf()

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
        observeTotalCost()
        // Observe totalPrice LiveData
//        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
//
//            Log.d("total_price_live_data", "totalprice live data: ${formatToTwoDecimalPlaces(totalPrice)}  ")
//            binding.totalCost.text = "Total: ${formatToTwoDecimalPlaces(totalPrice)}"
//        })



        val updatedSize = context?.let { it1 -> viewModel.getCheckoutItemsSize(it1) }
        if (updatedSize != null) {
            viewModelCheckOutItemDeleteViewModel.updateDatabaseSize(updatedSize)
        }

        cartViewModel.countMapLiveData.observe(viewLifecycleOwner) { countMap ->
            // Update your UI with the new count map

            // Call your method to update the UI based on the count map
        }

        binding.checkoutbutton.setOnClickListener {
            requireView().findNavController().navigate(R.id.checkOutFragment)
        }


    }

    private fun observeTotalCost() {
        viewModel.totalCostLiveData.observe(viewLifecycleOwner) {
            binding.totalCost.text = "Total = $it"
        }
    }


    private fun getData() {
        val data = viewModel.readCheckoutItem(requireContext())
        Log.d("adapterGetData", "get the data from retro $data")
        cartAdapter.updateList(data)


        cartItemUpdated = data

        cartAdapter.updateList(cartItemUpdated)


        Log.d("adapterGetData", "Total Price: $totalPrice")


    }

    override fun onItemDeleted(item: CheckOutItemEntity, position: Int) {

        viewModel.deleteChekcoutItem(
            checkOutItemEntity = item,
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

    override fun updateItem(item: CheckOutItemEntity) {
        // update database
        viewModel.updateItem(item,requireContext())
    }

    fun formatToTwoDecimalPlaces(value: Float?): String? {
        return value?.let {
            String.format("%.2f", it)
        }
    }


}