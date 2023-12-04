package com.example.ecommerce.ui.home_product_item.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class PagingViewModel : ViewModel() {
    fun getData(category: String = "") = androidx.paging.Pager(
        config = PagingConfig(pageSize =5, maxSize = 25),
        pagingSourceFactory = { PagingSource(category) }
    )
        .flow
        .cachedIn(viewModelScope)

}