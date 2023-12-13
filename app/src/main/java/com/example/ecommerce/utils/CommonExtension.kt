package com.example.ecommerce.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.NavController
import com.example.ecommerce.db.entities.CheckOutItemEntity
import com.example.ecommerce.db.entities.ProductEntity
import com.example.ecommerce.network.model.ProductsResponse
import com.example.ecommerce.db.entities.WishListEntity
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

fun ProductsResponse.Product.toProductEntity(): ProductEntity {
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

fun WishListEntity.toCheckOutItem(): CheckOutItemEntity {
    return CheckOutItemEntity(
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

fun ProductsResponse.Product.toWishListEntity(): WishListEntity {
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

fun ProductEntity.toRetrofitDataModel(): ProductsResponse.Product {
    return ProductsResponse.Product(
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