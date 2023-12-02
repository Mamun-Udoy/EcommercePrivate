package com.example.ecommerce.ui.check_out_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CheckOutItem")
data class CheckOutItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,

    var itemId: String,
    var title: String? = null,
    var discount: String? = null,
    var rating: Float? = null,
    var stock: Int? = null,
    var brand: String? = null,
    var category: String? = null,
    var thumbnail: String? = null,
    var price: Int? = null
)

