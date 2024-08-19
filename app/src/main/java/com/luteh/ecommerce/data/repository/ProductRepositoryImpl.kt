package com.luteh.ecommerce.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.Either
import com.luteh.ecommerce.data.datasource.mediator.ProductsPagingSource
import com.luteh.ecommerce.data.datasource.remote.ProductRemoteDataSource
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.domain.model.ProductModel
import com.luteh.ecommerce.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productRemoteDataSource: ProductRemoteDataSource) :
    ProductRepository {
    override fun productsPagingDataSource(productName: String): Flow<PagingData<ProductModel>> =
        Pager(
            PagingConfig(pageSize = 20, prefetchDistance = 1)
        ) {
            ProductsPagingSource(productRemoteDataSource, productName = productName)
        }.flow.map { pagingData ->
            pagingData.map { data ->
                ProductModel(
                    id = data.id,
                    thumbnailImageUrl = data.thumbnailImageUrl,
                    name = data.name,
                    price = data.price,
                    shopName = data.shopName,
                    rating = data.rating ?: 0.0,
                    ratingCount = data.ratingCount ?: 0
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getProduct(id: String): Either<Exception, ProductDetailModel> =
        withContext(Dispatchers.IO) {
            try {
                val data = productRemoteDataSource.getProduct(id).let { product ->
                    ProductDetailModel(
                        id = product.id,
                        thumbnailImageUrl = product.thumbnailImageUrl,
                        name = product.name,
                        price = product.price,
                        shopName = product.shopName,
                        rating = product.rating ?: 0.0,
                        ratingCount = product.ratingCount ?: 0,
                        description = product.description,
                        imageUrls = product.productImageUrls?.filterNotNull().orEmpty()
                    )
                }
                Either.Right(data)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }
}