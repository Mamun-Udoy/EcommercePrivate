package com.example.ecommerce.ui.home_product_item.product_data_model


import com.google.gson.annotations.SerializedName


data class ProductDataClass(
    @SerializedName("products")
    val products: List<Product>
) {
    data class Product(
        @SerializedName("id")
        val id: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("price")
        val price: Int?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("images")
        val images: List<String>? = null,
        @SerializedName("discountPercentage")
        val discount: Float,
        @SerializedName("rating")
        val rating: Float,
        @SerializedName("stock")
        val stock: Int,
        @SerializedName("brand")
        val brand: String?,
        @SerializedName("category")
        val categories: String?
    )
}