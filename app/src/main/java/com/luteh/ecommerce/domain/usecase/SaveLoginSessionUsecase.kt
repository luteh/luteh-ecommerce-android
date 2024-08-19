package com.luteh.ecommerce.domain.usecase

import arrow.core.Either
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.luteh.ecommerce.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

data class SaveLoginSessionUsecaseParam(val account: GoogleSignInAccount)

@Singleton
class SaveLoginSessionUsecase @Inject constructor(private val authRepository: AuthRepository) :
    BaseUseCase<Unit, SaveLoginSessionUsecaseParam>() {
    override suspend fun execute(params: SaveLoginSessionUsecaseParam): Either<Exception, Unit> {
        authRepository.saveAccount(params.account)
        authRepository.setLoginSession(true)
        return Either.Right(Unit)
    }
}