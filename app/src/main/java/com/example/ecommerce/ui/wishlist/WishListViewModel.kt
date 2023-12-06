package com.example.ecommerce.ui.wishlist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecommerce.ui.check_out_item.CheckOutItem
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.google.gson.Gson

class WishListViewModel: ViewModel() {

    fun insertWishListItem(wishListEntity: WishListEntity,context: Context){
        val dao = ProductDatabase.getDatabase(context)?.wishListItemDao()
        dao?.insertWishListItem(wishListEntity)

    }

    fun deleteWishListItem(wishListEntity: WishListEntity,context: Context){
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