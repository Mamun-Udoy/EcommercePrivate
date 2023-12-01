package com.example.ecommerce.ui.check_out_item

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckOutItemInsertViewModel: ViewModel() {

    // MutableLiveData to observe the database size
    val dbSize = MutableLiveData<Int>()

    // Function to update the database size
    fun updateDatabaseSize(size: Int) {
        dbSize.value = size
        Log.d("db_size12", "print the db size ${dbSize.value}")
    }
}