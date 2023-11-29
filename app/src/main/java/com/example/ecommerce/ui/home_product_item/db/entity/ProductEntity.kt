package com.example.ecommerce.ui.home_product_item.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ProductItem")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val brand: String?,
    val category: String?,
    val description: String?,
    val discountPercentage: Double?,
    val images: String,
    val price: Int?,
    val rating: Double?,
    val stock: Int?,
    val thumbnail: String?,
    val title: String?


)
