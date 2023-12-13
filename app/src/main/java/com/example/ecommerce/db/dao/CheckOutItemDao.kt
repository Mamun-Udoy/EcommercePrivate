package com.example.ecommerce.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce.db.entities.CheckOutItemEntity

@Dao
interface CheckOutItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheckoutItem(checkOutItemEntity: CheckOutItemEntity)

    @Delete
    fun deleteCheckoutItem(checkOutItemEntity: CheckOutItemEntity)

    @Query(value = "SELECT * FROM checkoutitem ")
    fun readCheckoutItem(): List<CheckOutItemEntity>?

    @Query("SELECT COUNT(id) FROM CheckOutItem")
    fun getCheckoutItemsSize(): Int

    @Query("DELETE FROM CheckOutItem")
    fun deleteAllCheckoutItems()





}