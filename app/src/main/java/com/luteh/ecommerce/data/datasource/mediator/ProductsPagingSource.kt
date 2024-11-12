package com.luteh.ecommerce.data.datasource.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luteh.ecommerce.GetProductsQuery
import com.luteh.ecommerce.data.datasource.remote.ProductRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productName: String
) : PagingSource<Int, GetProductsQuery.Product>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, GetProductsQuery.Product> {
        try {
            // Start refresh at page 0 if undefined.
            delay(1000L)
            val nextPageNumber = params.key ?: 0
            val data =
                productRemoteDataSource.getProducts(
                    limit = 20,
                    offset = nextPageNumber * 20,
                    productName = productName
                )
            val isLastPage = data.size < 20
            val nextKey = if (isLastPage) null else nextPageNumber + 1
            return LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetProductsQuery.Product>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}