package com.example.ecommerce

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
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