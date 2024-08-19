package com.luteh.ecommerce.data.repository

import arrow.core.Either
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.luteh.ecommerce.CreateUserMutation
import com.luteh.ecommerce.data.datasource.local.AuthLocalDataSource
import com.luteh.ecommerce.data.datasource.remote.AuthRemoteDataSource
import com.luteh.ecommerce.domain.model.RegisterParam
import com.luteh.ecommerce.domain.model.UserRole
import com.luteh.ecommerce.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun setLoginSession(isLoggedIn: Boolean) {
        authLocalDataSource.setLoginSession(isLoggedIn)
    }

    override suspend fun saveAccount(account: GoogleSignInAccount?) {
        authLocalDataSource.saveAccount(account)
    }

    override suspend fun getLoginSession(): Boolean {
        return authLocalDataSource.getLoginSession()
    }

    override suspend fun getAccount(): String {
        return authLocalDataSource.getAccount();
    }

    override suspend fun login(email: String, password: String): Either<Exception, Unit> = try {
        authRemoteDataSource.login(email = email, password = password)
        setLoginSession(true)
        Either.Right(Unit)
    } catch (e: Exception) {
        Either.Left(e)
    }

    override suspend fun getUserRoles(): Either<Exception, List<UserRole>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val data = authRemoteDataSource.getRoles().getRoles!!.map {
                    UserRole(it!!.id, it.name)
                }
                Either.Right(data)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override suspend fun register(param: RegisterParam): Either<Exception, Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                authRemoteDataSource.createUser(
                    CreateUserMutation(
                        email = param.email,
                        password = param.password,
                        name = param.name,
                        phone = param.phone,
                        role_id = param.roleId.toInt()
                    )
                )
                Either.Right(Unit)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }
}