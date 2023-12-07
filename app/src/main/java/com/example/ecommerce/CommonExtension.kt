package com.example.ecommerce

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import com.example.ecommerce.ui.check_out_item.CheckOutItem
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.wishlist.WishListEntity
import com.google.gson.Gson

fun NavController.navigateTo(destinationResId: Int) {

    if (currentDestination == null) {
        navigate(destinationResId)
    } else {
        currentDestination?.let {
            if (it.id != destinationResId) {
                navigate(destinationResId)
            }
        }
    }
}

fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo

    return activeNetwork?.isConnected == true
}

fun RetrofitDataModel.Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id.toInt(),
//        id = id,
        brand = brand,
        description = description,
        stock = stock,
        category = categories,
        rating = rating,
        discountPercentage = discount,
        images = Gson().toJson(images),
        price = price,
        thumbnail = thumbnail,
        title = title
    )

}

fun WishListEntity.toCheckOutItem():CheckOutItem{
    return CheckOutItem(
        id = id.toLong(),
        brand = brand,
        stock = stock,
        rating = rating?.toFloat(),
        discount = discount?.toString(),
        price = price,
        thumbnail = thumbnail,
        title = title.toString(),
        itemId = ""
    )
}

fun RetrofitDataModel.Product.toWishListEntity(): WishListEntity {
    return WishListEntity(
        id = id.toLong(),
        brand = brand,
        description = description,
        stock = stock,
        category = categories,
        rating = rating.toFloat(),
        discount = discount.toString(),
        price = price,
        thumbnail = thumbnail,
        title = title.toString()

    )

}

fun ProductEntity.toRetrofitDataModel(): RetrofitDataModel.Product {
    return RetrofitDataModel.Product(
        id = id.toString(),
        brand = brand,
        description = description,
        stock = stock?:0,
        categories = category,
        rating = rating?:0.0,
        discount = discountPercentage,
        images = null,
        price = price,
        thumbnail = thumbnail,
        title = title
    )

}