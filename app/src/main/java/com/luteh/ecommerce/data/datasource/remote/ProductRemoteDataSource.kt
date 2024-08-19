package com.luteh.ecommerce.data.datasource.remote

import com.apollographql.apollo.ApolloClient
import com.luteh.ecommerce.GetProductQuery
import com.luteh.ecommerce.GetProductQuery.GetProduct
import com.luteh.ecommerce.GetProductsQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getProducts(
        limit: Int,
        offset: Int,
        productName: String
    ): List<GetProductsQuery.Product> {
        val data = apolloClient.query(
            GetProductsQuery(
                limit = limit,
                offset = offset,
                productName = productName
            )
        ).execute()
            .dataOrThrow()
        if (data.getProducts?.products == null) {
            throw RuntimeException("No product was found")
        }
        return data.getProducts.products.filterNotNull()
    }

    suspend fun getProduct(id: String): GetProduct {
        val data = apolloClient.query(GetProductQuery(id)).execute().dataOrThrow()
        if (data.getProduct == null) {
            throw RuntimeException("No product was found")
        }
        return data.getProduct
    }
}