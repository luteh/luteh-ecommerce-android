package com.luteh.ecommerce.data.datasource.remote

import com.apollographql.apollo.ApolloClient
import com.luteh.ecommerce.CreateUserMutation
import com.luteh.ecommerce.GetRolesQuery
import com.luteh.ecommerce.LoginMutation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun login(email: String, password: String): LoginMutation.Data {
        val data = apolloClient.mutation(LoginMutation(password = password, email = email))
            .execute().dataOrThrow()
        if (data.login.isNullOrBlank()) {
            throw RuntimeException("No user was found")
        }
        return data
    }

    suspend fun getRoles(): GetRolesQuery.Data {
        val data = apolloClient.query(GetRolesQuery()).execute().dataOrThrow()
        if (data.getRoles.isNullOrEmpty()) {
            throw RuntimeException("No roles was found")
        }
        return data
    }

    suspend fun createUser(mutation: CreateUserMutation): CreateUserMutation.Data {
        val data = apolloClient.mutation(mutation).execute().dataOrThrow()
        if (data.createUser == null) {
            throw RuntimeException("No user was found")
        }
        return data
    }
}