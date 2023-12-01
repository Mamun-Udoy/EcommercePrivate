package com.example.ecommerce.ui.home_product_item.db.product_view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.google.gson.Gson

class ProductViewModel:ViewModel() {

    fun getProductItem(context: Context): List<ProductEntity> {
        val dao = ProductDatabase.getDatabase(context)?.productItemDao()
        val data = dao?.getProductItem()?: emptyList()
        return data

    }




}