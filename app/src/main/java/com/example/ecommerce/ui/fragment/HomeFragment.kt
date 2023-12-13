package com.example.ecommerce.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.utils.isConnectedToInternet
import com.example.ecommerce.utils.toProductEntity
import com.example.ecommerce.utils.toRetrofitDataModel
import com.example.ecommerce.utils.toWishListEntity
import com.example.ecommerce.db.ProductDatabase
import com.example.ecommerce.db.entities.ProductEntity
import com.example.ecommerce.network.model.ProductsResponse
import com.example.ecommerce.utils.paging.LoaderAdapter
import com.example.ecommerce.utils.paging.PagingAdapter
import com.example.ecommerce.utils.paging.PagingViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), PagingAdapter.CacheInData, PagingAdapter.ItemClickCallback, PagingAdapter.WishListCallBack {

    private lateinit var binding: FragmentHomeBinding


    private val viewModel by viewModels<PagingViewModel>()


    private val myAdapter: PagingAdapter by lazy { PagingAdapter(this, this, this) }

    private lateinit var offlineAdapter: ProductOfflineAdapter

    private val productViewModel by viewModels<ProductViewModel>()




    private fun init() {
        binding.productItemRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = myAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            "internet_",
            "onViewCreated: isInternetAvailable: ${context?.isConnectedToInternet()}"
        )
//        // this is for spinner adapter
//        val categories = resources.getStringArray(R.array.categories)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, categories)
//        binding.autoCompleteTextView.setAdapter(arrayAdapter)
//        var selectedValue = "default"
//
//        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
//            selectedValue = parent.getItemAtPosition(position).toString().lowercase()
//            fetchData(selectedValue)
//            Log.d("spinner_value", "print the string ${selectedValue}")
//
//            // Now you have the selected value, and you can store it or perform any action
//            // For example, you can pass it to your interface if you are using one
////            spinnerItemSelectedListener?.onItemSelected(selectedValue)
//        }





        if (context?.isConnectedToInternet() == true) {

            productViewModel.deleteAllCheckoutItems(context = requireContext())
            init()
            fetchData()

        } else {
            //showing product item showing in offline
            val dao = ProductDatabase.getDatabase(requireContext())?.productItemDao()
            val data = dao?.getProductItem() ?: emptyList()
     

            val dataList = data.map {
                it.toRetrofitDataModel()
            }

            Log.d("oofline_data_size", "onViewCreated: data: ${dataList.size}")

            offlineAdapter = ProductOfflineAdapter(ArrayList(dataList))
            binding.productItemRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.productItemRecyclerview.adapter = offlineAdapter

        }

    }

    override fun onResume() {
        super.onResume()
        // this is for spinner adapter
        binding.autoCompleteTextView.setText("Chose Your " +
                "Item")
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, categories)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        var selectedValue = "default"



        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            selectedValue = parent.getItemAtPosition(position).toString().lowercase()
            fetchData(selectedValue)
            Log.d("spinner_value", "print the string ${selectedValue}")

            // Now you have the selected value, and you can store it or perform any action
            // For example, you can pass it to your interface if you are using one
//            spinnerItemSelectedListener?.onItemSelected(selectedValue)
        }


    }


    private fun fetchData(category: String = "") {
        val pagingData = viewModel.getData(category).distinctUntilChanged()
        lifecycleScope.launch {
            pagingData.collect {
                myAdapter.submitData(it)
            }
        }
    }

    override fun cacheData(item: ProductsResponse.Product?) {
        if (item == null) return
        val productItem = item.toProductEntity()
        val dao = ProductDatabase.getDatabase(requireContext())?.productItemDao()
        viewModel.viewModelScope.launch {
            dao?.insertProductItem(productItem)
        }

    }

    // Assuming you are inside your HomeFragment
    override fun onItemClicked(item: ProductsResponse.Product) {
        val bundle = Bundle()
        bundle.putString("data_item", Gson().toJson(item))
        requireView().findNavController().navigate(R.id.productDetails, bundle)

    }

    override fun wishListClicked(item: ProductsResponse.Product) {

        Log.d("wishlistclicked", "wishListClicked: favorite ")



        val wishListItem = item.toWishListEntity()
        val dao = ProductDatabase.getDatabase(requireContext())?.wishListItemDao()
        dao?.insertWishListItem(wishListItem)
    }


}