package com.example.ecommerce.app

import android.app.Application
import com.example.ecommerce.db.ProductDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
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