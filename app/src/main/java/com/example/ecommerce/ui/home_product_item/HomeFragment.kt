package com.example.ecommerce.ui.home_product_item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.isConnectedToInternet
import com.example.ecommerce.toProductEntity
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.example.ecommerce.ui.home_product_item.db.product_view_model.ProductViewModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitViewModel
import com.example.ecommerce.ui.home_product_item.paging.LoaderAdapter
import com.example.ecommerce.ui.home_product_item.paging.PagingAdapter
import com.example.ecommerce.ui.home_product_item.paging.PagingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), PagingAdapter.CacheInData {

    private lateinit var binding: FragmentHomeBinding



    private val viewModel by viewModels<PagingViewModel>()

    private val myAdapter: PagingAdapter by lazy { PagingAdapter(this) }

    private val viewModel2 by viewModels<RetrofitViewModel>()




    private fun init() {
        binding.productItemRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = myAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


        val internetConnection = context?.isConnectedToInternet()
        if (internetConnection == true) {

            init()
            observer()
            fetchData()
        }
        else{

        }

    }

    private fun fetchData() {
        if (context?.isConnectedToInternet() == true) {
            viewModel2.getProductInfoList()
        } else {

        }
    }

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
        val dao = ProductDatabase.getDatabase(requireContext())?.productItemDao
        dao?.insertProductItem(productItem)

    }


}