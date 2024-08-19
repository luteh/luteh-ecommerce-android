package com.luteh.ecommerce.domain.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun productsPagingDataSource(productName: String): Flow<PagingData<ProductModel>>
    suspend fun getProduct(id: String): Either<Exception, ProductDetailModel>
}