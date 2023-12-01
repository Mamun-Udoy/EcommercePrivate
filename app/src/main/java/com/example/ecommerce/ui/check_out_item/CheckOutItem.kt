package com.example.ecommerce.ui.check_out_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CheckOutItem")
data class CheckOutItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var itemId: String,
    var discount: Float? = null,
    var rating: Float? = null,
    var stock: Int? = null,
    var brand: String? = null,
    var category: String? = null
)

