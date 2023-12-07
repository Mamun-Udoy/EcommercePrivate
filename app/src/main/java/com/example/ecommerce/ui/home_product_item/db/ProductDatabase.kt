package com.example.ecommerce.ui.home_product_item.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.ui.check_out_item.CheckOutItem
import com.example.ecommerce.ui.check_out_item.CheckOutItemDao
import com.example.ecommerce.ui.home_product_item.db.dao.ProductItemDao
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.db.entity.RemoteKeyEntity
import com.example.ecommerce.ui.wishlist.WishListEntity
import com.example.ecommerce.ui.wishlist.WishListItemDao


@Database(
    entities = [ProductEntity::class, CheckOutItem::class, WishListEntity::class],
    version = 15
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productItemDao(): ProductItemDao

    abstract fun checkOutItemDao(): CheckOutItemDao

    abstract fun wishListItemDao(): WishListItemDao

    companion object {
        @Volatile
        private var instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "Product_Database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                return instance
            }
            return instance

        }


    }
}