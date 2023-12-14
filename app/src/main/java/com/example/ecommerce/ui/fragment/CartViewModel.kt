package com.example.ecommerce.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel() {

    private val _countMapLiveData = MutableLiveData<Map<Int, Int>>()
    val countMapLiveData: LiveData<Map<Int, Int>> = _countMapLiveData


    fun updateCountMap(countMap: Map<Int, Int>) {
        _countMapLiveData.value = countMap
    }


}