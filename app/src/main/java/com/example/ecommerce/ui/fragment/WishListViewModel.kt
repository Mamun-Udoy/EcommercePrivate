package com.example.ecommerce.ui.fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecommerce.db.ProductDatabase
import com.example.ecommerce.db.entities.WishListEntity
import com.google.gson.Gson

class WishListViewModel: ViewModel() {


    fun deleteWishListItem(wishListEntity: WishListEntity, context: Context){
        val dao = ProductDatabase.getDatabase(context)?.wishListItemDao()
        dao?.deleteWishListItem(wishListEntity)
    }

    fun readWishListItem(context: Context): List<WishListEntity> {

        val dao = ProductDatabase.getDatabase(context)?.wishListItemDao()
        val data = dao?.readWishListItem() ?: emptyList()
        Log.d("wishListItem", "read WishListItem data: data: ${Gson().toJson(data)}")
        return data

    }



}