package com.example.ecommerce.ui.check_out_item

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase

class CheckOutViewModel: ViewModel() {
    fun insertCheckoutItem(checkOutItem: CheckOutItem, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.insertCheckoutItem(checkOutItem)

    }

    fun deleteChekcoutItem(checkOutItem: CheckOutItem, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.deleteCheckoutItem(checkOutItem)

    }

    fun getCheckoutItemsSize(context: Context): Int? {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.getCheckoutItemsSize()
        return data
    }

    val dbSize = MutableLiveData<Int>()

    // Function to update the database size
    fun updateDatabaseSize(size: Int) {
        dbSize.value = size
        Log.d("db_size12", "print the db size ${dbSize.value}")
    }

}