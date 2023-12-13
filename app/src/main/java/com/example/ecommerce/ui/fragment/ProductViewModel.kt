package com.example.ecommerce.ui.fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ecommerce.db.ProductDatabase
import com.example.ecommerce.db.entities.ProductEntity

class ProductViewModel:ViewModel() {


    fun deleteAllCheckoutItems( context: Context){
        val dao = ProductDatabase.getDatabase(context)?.productItemDao()
        dao?.deleteAllCheckoutItems()
    }




}