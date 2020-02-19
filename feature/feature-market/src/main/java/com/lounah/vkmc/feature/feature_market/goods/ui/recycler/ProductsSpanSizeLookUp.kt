package com.lounah.vkmc.feature.feature_market.goods.ui.recycler

import androidx.recyclerview.widget.GridLayoutManager
import com.lounah.vkmc.core.recycler.base.ViewTyped
import com.lounah.vkmc.feature.feature_market.R


class ProductsSpanSizeLookUp
    (private val items: () -> List<ViewTyped>) :
    GridLayoutManager.SpanSizeLookup() {
    override fun isSpanIndexCacheEnabled(): Boolean {
        return false
    }
    override fun getSpanSize(position: Int): Int {
        val isProgress = items()[position].viewType == R.layout.item_paging_loading
        val isErrorLoading = items()[position].viewType == R.layout.item_paging_error
        val isFullScreenError = items()[position].viewType == R.layout.item_error

        return if (isFullScreenError || isProgress || isErrorLoading) 2 else 1
    }
}