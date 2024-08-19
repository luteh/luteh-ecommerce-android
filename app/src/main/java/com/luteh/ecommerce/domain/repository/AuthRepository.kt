package com.luteh.ecommerce.domain.repository

import arrow.core.Either
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.luteh.ecommerce.domain.model.RegisterParam
import com.luteh.ecommerce.domain.model.UserRole

interface AuthRepository {
    suspend fun setLoginSession(isLoggedIn: Boolean)
    suspend fun getLoginSession(): Boolean

    suspend fun saveAccount(account: GoogleSignInAccount?)
    suspend fun getAccount(): String
    suspend fun login(email: String, password: String): Either<Exception, Unit>
    suspend fun getUserRoles(): Either<Exception, List<UserRole>>
    suspend fun register(
        param: RegisterParam
    ): Either<Exception, Unit>
}