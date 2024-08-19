package com.luteh.ecommerce.domain.usecase

import arrow.core.Either
import com.luteh.ecommerce.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUsecase @Inject constructor(private val authRepository: AuthRepository) :
    BaseUseCaseWithoutParam<Unit>() {
    override suspend fun execute(): Either<Exception, Unit> {
        authRepository.setLoginSession(false)
        return Either.Right(Unit)
    }
}