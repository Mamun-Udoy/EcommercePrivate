
package com.example.ecommerce.ui.home_product_item.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecommerce.ui.home_product_item.db.entity.ProductEntity
import com.example.ecommerce.ui.home_product_item.db.ProductDatabase

const val DATABASE_PAGE_SIZE = 20

class PagingSourceDB(private val database: ProductDatabase) : PagingSource<Int, ProductEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val position = params.key ?: 0
            val pageSize = params.loadSize

            // Calculate the offset based on the position and pageSize
            val offset = position * pageSize

            // Load data from your local database using PagingSource
            val data = database.productItemDao().getPagingSource()

            LoadResult.Page(
                data = data,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (data.isNotEmpty()) position + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}




