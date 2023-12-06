package com.example.ecommerce.ui.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wishlist")
data class WishListEntity(
    @PrimaryKey(autoGenerate = false) var id: Long = 0,

    var title: String? = null,
    var discount: String? = null,
    var rating: Float? = null,
    var description: String? = null,
    var stock: Int? = null,
    var brand: String? = null,
    var category: String? = null,
    var thumbnail: String? = null,
    var price: Int? = null
)


