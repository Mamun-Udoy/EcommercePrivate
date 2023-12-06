package com.example.ecommerce.ui.wishlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce.ui.check_out_item.CheckOutItem


@Dao
interface WishListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWishListItem(wishListEntity: WishListEntity)

    @Delete
    fun deleteWishListItem(wishListEntity: WishListEntity)

    @Query(value = "SELECT * FROM wishlist ")
    fun readWishListItem(): List<WishListEntity>?

    @Query("SELECT COUNT(id) FROM wishlist")
    fun getWishListItemSize(): Int



}