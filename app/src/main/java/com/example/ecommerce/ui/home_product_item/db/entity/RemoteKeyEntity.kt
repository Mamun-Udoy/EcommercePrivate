package com.example.ecommerce.ui.home_product_item.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_table")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val nextPage: Int?,
    val previousPage: Int?
)
