package com.example.ecommerce.utils.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecommerce.network.ApiClient
import com.example.ecommerce.network.model.ProductsResponse


const val NETWORK_PAGE_SIZE = 10

class PagingSource(private val category: String) : PagingSource<Int, ProductsResponse.Product>() {

    override fun getRefreshKey(state: PagingState<Int, ProductsResponse.Product>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Product> {
        try {
            val position = params.key ?: 1
            val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 10 else NETWORK_PAGE_SIZE

            Log.d("offset_db", "load: pos: $offset")
            val response = if (category.isEmpty() || category.lowercase() == "default")
                                ApiClient.api.getProductData(limit = offset)
                            else ApiClient.api.getCategoryProductData(category)

            if (response.isSuccessful) {
                val data = response.body()?.products ?: emptyList()
                val prevKey = if (position == 1) null else position - 1
                val nextKey = if (data.isEmpty()) null else position + 1

                return LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(Exception("Failed to load data"))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}