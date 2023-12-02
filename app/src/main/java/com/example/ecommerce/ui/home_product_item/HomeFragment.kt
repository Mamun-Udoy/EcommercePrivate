package com.example.ecommerce.ui.home_product_item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.isConnectedToInternet
import com.example.ecommerce.toProductEntity
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.db.product_view_model.ProductViewModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitViewModel
import com.example.ecommerce.ui.home_product_item.paging.LoaderAdapter
import com.example.ecommerce.ui.home_product_item.paging.PagingAdapter
import com.example.ecommerce.ui.home_product_item.paging.PagingViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), PagingAdapter.CacheInData, PagingAdapter.ItemClickCallback {

    private lateinit var binding: FragmentHomeBinding


    private val viewModel by viewModels<PagingViewModel>()


    private val myAdapter: PagingAdapter by lazy { PagingAdapter(this,this) }

    private val viewModel2 by viewModels<RetrofitViewModel>()

    val productList: ArrayList<ProductEntity> by lazy { arrayListOf() }


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


        if (context?.isConnectedToInternet() == true) {
            init()
            observer()
        }
        else{

        }

    }

    private fun fetchData() {
        if (context?.isConnectedToInternet() == true) {
            viewModel2.getProductInfoList()
        } else {
            init()
//            dbData()
//            getData()


        }
    }
//
//    private fun getData() {
//        val data = viewModel3.getProductItem(requireContext())
//        myAdapter.updateList(data)
//
//    }
//
//    private fun dbData(): List<ProductEntity>? {
//        val dao = ProductDatabase.getDatabase(requireContext())?.productItemDao()
//        val data = dao?.getProductItem()
//        return data
//
//    }

    private fun observer() {
        val pagingData = viewModel.getData().distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collect {
                myAdapter.submitData(it)
            }
        }
    }

    override fun cacheData(item: RetrofitDataModel.Product?) {
        if (item == null) return
        val productItem = item.toProductEntity()
        val dao = ProductDatabase.getDatabase(requireContext())?.productItemDao()
        dao?.insertProductItem(productItem)

    }

    // Assuming you are inside your HomeFragment
    override fun onItemClicked(item: RetrofitDataModel.Product) {
        val bundle = Bundle()
        bundle.putString("data_item",Gson().toJson(item))
        requireView().findNavController().navigate(R.id.productDetails,bundle)

    }


}