package com.example.ecommerce.ui.fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.db.ProductDatabase
import com.example.ecommerce.db.entities.CheckOutItemEntity
import com.google.gson.Gson

class CheckOutViewModel: ViewModel() {
    fun insertCheckoutItem(checkOutItemEntity: CheckOutItemEntity, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.insertCheckoutItem(checkOutItemEntity)

    }

    fun deleteChekcoutItem(checkOutItemEntity: CheckOutItemEntity, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.deleteCheckoutItem(checkOutItemEntity)

    }

    fun getCheckoutItemsSize(context: Context): Int? {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.getCheckoutItemsSize()
        return data
    }

    fun readCheckoutItem(context: Context): List<CheckOutItemEntity> {

        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.readCheckoutItem() ?: emptyList()
        Log.d("checkoutitem", "read checkoutitem data: data: ${Gson().toJson(data)}")
        return data

    }



}