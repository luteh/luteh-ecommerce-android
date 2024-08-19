package com.luteh.ecommerce.domain.usecase

import arrow.core.Either
import com.luteh.ecommerce.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLoginSessionUsecase @Inject constructor(private val authRepository: AuthRepository) :
    BaseUseCaseWithoutParam<Boolean>() {
    override suspend fun execute(): Either<Exception, Boolean> {
        return Either.Right(authRepository.getLoginSession())
    }
}