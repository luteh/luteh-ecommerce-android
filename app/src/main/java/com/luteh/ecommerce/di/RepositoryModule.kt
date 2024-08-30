package com.luteh.ecommerce.di

import com.luteh.ecommerce.data.repository.AuthRepositoryImpl
import com.luteh.ecommerce.data.repository.ProductRepositoryImpl
import com.luteh.ecommerce.domain.repository.AuthRepository
import com.luteh.ecommerce.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

}