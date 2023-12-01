package com.example.ecommerce.ui.home_product_item.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.ecommerce.ui.home_product_item.api.PhoneItemApi
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity

//@OptIn(ExperimentalPagingApi::class)
//class ProductRemoteMediator(
//    private val productDatabase: ProductDatabase,
//    private val productItemApi: PhoneItemApi
//): RemoteMediator<Int,ProductEntity>()  {
//
//    private val productItemDao = productDatabase.productItemDao()
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ProductEntity>
//    ) {
//    }
//
//
//}
