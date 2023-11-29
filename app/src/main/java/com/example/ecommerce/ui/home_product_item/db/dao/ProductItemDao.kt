package com.example.ecommerce.ui.home_product_item.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity

@Dao
interface ProductItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertProductItem(productEntity: ProductEntity)

    @Delete
     fun deleteProductItem(productEntity: ProductEntity)

    @Query(value = "SELECT * FROM productitem ")
    fun readProductItem(): List<ProductEntity>?

    @Query("SELECT COUNT(id) FROM productitem")
    fun getProductItemsSize(): Int



}