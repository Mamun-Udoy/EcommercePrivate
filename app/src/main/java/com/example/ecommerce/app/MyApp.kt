package com.example.ecommerce.app

import android.app.Application
import android.content.Context
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: MyApp? = null
        val dao = context?.let { ProductDatabase.getDatabase(it)?.productItemDao() }
    }
}