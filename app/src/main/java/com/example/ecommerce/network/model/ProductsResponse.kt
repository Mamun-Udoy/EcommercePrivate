package com.example.ecommerce.network.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<Product>
) {
    data class Product(
        @SerializedName("id")
        val id: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("price")
        var price: Int?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("images")
        val images: List<String>? = null,
        @SerializedName("discountPercentage")
        val discount: Double?,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("stock")
        val stock: Int,
        @SerializedName("brand")
        val brand: String?,
        @SerializedName("category")
        val categories: String?
    )
}
