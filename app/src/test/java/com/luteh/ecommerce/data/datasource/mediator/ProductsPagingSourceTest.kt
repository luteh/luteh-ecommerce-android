package com.luteh.ecommerce.data.datasource.mediator

import androidx.paging.PagingSource
import com.luteh.ecommerce.GetProductsQuery
import com.luteh.ecommerce.data.datasource.remote.ProductRemoteDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class ProductsPagingSourceTest {

    private val productRemoteDataSource: ProductRemoteDataSource = mock()
    private lateinit var productsPagingSource: ProductsPagingSource

    @Before
    fun setUp() {
        productsPagingSource = ProductsPagingSource(productRemoteDataSource, "")
    }

    @Test
    fun `load returns LoadResult Page with list when data is loaded successfully`() = runTest {
        val product = GetProductsQuery.Product(
            id = "",
            name = "",
            price = 0.0,
            shopName = "",
            thumbnailImageUrl = "",
            rating = null,
            ratingCount = null
        )
        whenever(
            productRemoteDataSource.getProducts(
                limit = 20,
                offset = 0,
                productName = ""
            )
        ).thenReturn(
            List(20) { product }
        )
        val result = productsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        Assert.assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(1, (result as PagingSource.LoadResult.Page).nextKey)
        verify(productRemoteDataSource).getProducts(limit = 20, offset = 0, productName = "")
    }

    @Test
    fun `load returns LoadResult Page with empty list when data is loaded successfully`() =
        runTest {
            whenever(
                productRemoteDataSource.getProducts(
                    limit = 20,
                    offset = 0,
                    productName = ""
                )
            ).thenReturn(listOf())
            val result = productsPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )

            Assert.assertTrue(result is PagingSource.LoadResult.Page)
            assertEquals(null, (result as PagingSource.LoadResult.Page).nextKey)
            verify(productRemoteDataSource).getProducts(limit = 20, offset = 0, productName = "")
        }

    @Test
    fun `load returns LoadResult Error when data loading fails`() = runTest {
        whenever(
            productRemoteDataSource.getProducts(
                limit = 20,
                offset = 0,
                productName = ""
            )
        ).thenThrow(RuntimeException())
        val result = productsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        Assert.assertTrue(result is PagingSource.LoadResult.Error)
        verify(productRemoteDataSource).getProducts(limit = 20, offset = 0, productName = "")
    }
}