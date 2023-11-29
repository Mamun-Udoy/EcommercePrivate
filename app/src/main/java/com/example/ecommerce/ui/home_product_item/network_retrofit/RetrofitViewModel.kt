package com.example.ecommerce.ui.home_product_item.network_retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RetrofitViewModel: ViewModel() {

    val productInfo: MutableLiveData<List<RetrofitDataModel.Product>> = MutableLiveData()

    fun getProductInfoList() = viewModelScope.launch {
        val response = RetrofitInstance.api.getProductData()
        if(response.isSuccessful){
            val data = response.body()
            productInfo.value = data?.products
        }
        else {
            val code = response.code()
            val error = response.errorBody().toString()
            Log.d("error_retrofit", "productInfoList: error: error code: $code, $error")
        }

    }



}