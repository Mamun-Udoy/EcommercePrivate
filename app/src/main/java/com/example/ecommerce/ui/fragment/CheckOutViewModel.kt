package com.example.ecommerce.ui.fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.db.ProductDatabase
import com.example.ecommerce.db.entities.CheckOutItemEntity
import com.google.gson.Gson

class CheckOutViewModel: ViewModel() {

    val totalCostLiveData = MutableLiveData<Int>()

    fun insertCheckoutItem(checkOutItemEntity: CheckOutItemEntity, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.insertCheckoutItem(checkOutItemEntity)

    }

    fun deleteChekcoutItem(checkOutItemEntity: CheckOutItemEntity, context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.deleteCheckoutItem(checkOutItemEntity)

    }

    fun updateItem(item: CheckOutItemEntity,context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        dao?.updateItem(item)
        updateTotalCost(context)
    }

    fun getCheckoutItemsSize(context: Context): Int? {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.getCheckoutItemsSize()
        return data
    }

    fun readCheckoutItem(context: Context): List<CheckOutItemEntity> {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.readCheckoutItem() ?: emptyList()
        totalCostLiveData.value = totalCost(data)
        Log.d("checkoutitem", "read checkoutitem data: data: ${Gson().toJson(data)}")
        return data

    }

    fun updateTotalCost(context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.checkOutItemDao()
        val data = dao?.readCheckoutItem() ?: emptyList()
        totalCostLiveData.value = totalCost(data)
    }

    private fun totalCost(dataList: List<CheckOutItemEntity>): Int {
        var totalCost = 0
        for (item in dataList) {
            totalCost = item.price?.times(item.qty) ?: 0
        }

        return totalCost
    }


}