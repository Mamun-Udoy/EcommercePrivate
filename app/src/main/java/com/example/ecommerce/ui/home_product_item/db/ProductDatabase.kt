package com.example.ecommerce.ui.home_product_item.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.ui.home_product_item.db.dao.ProductItemDao
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.db.entity.RemoteKeyEntity


@Database(entities = [ProductEntity::class], version = 4)
abstract class ProductDatabase : RoomDatabase() {

    abstract val productItemDao: ProductItemDao

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