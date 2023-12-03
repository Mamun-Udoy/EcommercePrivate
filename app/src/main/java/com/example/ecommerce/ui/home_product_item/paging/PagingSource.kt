package com.example.navifationview.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitInstance


const val NETWORK_PAGE_SIZE = 5
class PagingSource : PagingSource<Int, RetrofitDataModel.Product>() {

    override fun getRefreshKey(state: PagingState<Int, RetrofitDataModel.Product>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RetrofitDataModel.Product> {
        try {
            val position = params.key ?: 1
            val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 5 else NETWORK_PAGE_SIZE

            Log.d("offset_db", "load: pos: $offset")
            val response = RetrofitInstance.api.getProductData(limit = offset)

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