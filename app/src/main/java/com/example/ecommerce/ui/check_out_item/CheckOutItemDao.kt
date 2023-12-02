package com.example.ecommerce.ui.check_out_item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CheckOutItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheckoutItem(checkOutItem: CheckOutItem)

    @Delete
    fun deleteCheckoutItem(checkOutItem: CheckOutItem)

    @Query(value = "SELECT * FROM checkoutitem ")
    fun readCheckoutItem(): List<CheckOutItem>?

    @Query("SELECT COUNT(id) FROM CheckOutItem")
    fun getCheckoutItemsSize(): Int

    @Query("DELETE FROM CheckOutItem")
    fun deleteAllCheckoutItems()





}