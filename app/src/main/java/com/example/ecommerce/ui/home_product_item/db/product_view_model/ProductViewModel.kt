package com.example.ecommerce.ui.home_product_item.db.product_view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity

class ProductViewModel:ViewModel() {

    fun insertProductItem(productEntity: ProductEntity,context: Context) {
        val dao = ProductDatabase.getDatabase(context)?.productItemDao
        dao?.insertProductItem(productEntity)

    }

//    fun deleteProdcutItem(productEntity: ProductEntity)


}