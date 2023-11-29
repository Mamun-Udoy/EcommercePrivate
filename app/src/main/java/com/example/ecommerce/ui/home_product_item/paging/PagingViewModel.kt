package com.example.ecommerce.ui.home_product_item.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.navifationview.paging.PagingSource

class PagingViewModel:ViewModel() {
    fun getData() = androidx.paging.Pager(
        config = PagingConfig(pageSize = 1, maxSize = 5),
        pagingSourceFactory = { PagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)

}