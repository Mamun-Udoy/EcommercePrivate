package com.example.ecommerce.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.db.entities.CheckOutItemEntity
import com.example.ecommerce.db.dao.CheckOutItemDao
import com.example.ecommerce.db.dao.ProductItemDao
import com.example.ecommerce.db.entities.ProductEntity
import com.example.ecommerce.db.entities.WishListEntity
import com.example.ecommerce.db.dao.WishListItemDao


@Database(
    entities = [ProductEntity::class, CheckOutItemEntity::class, WishListEntity::class],
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

            }
            return instance

        }


    }
}