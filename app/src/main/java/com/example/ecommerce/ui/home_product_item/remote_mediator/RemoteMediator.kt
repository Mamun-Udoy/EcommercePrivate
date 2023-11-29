package com.example.ecommerce.ui.home_product_item.remote_mediator

import androidx.paging.ExperimentalPagingApi
import com.example.ecommerce.ui.home_product_item.api.PhoneItemApi
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val productDatabase: ProductDatabase,
    private val productItemApi: PhoneItemApi
)  {

    private val productItemDao = productDatabase.productItemDao
//    private val remoteKeyDao = productDatabase.remoteKeyDao
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ProductEntity>
//    ) {
//        return try {
//            val currentPage = when (loadType) {
//                LoadType.REFRESH -> {
//                    val remoteKeys = getRemoteKeyTClosestToCurrentPosition(state)
//
//                }
//
//            }
//        }
//    }


}
