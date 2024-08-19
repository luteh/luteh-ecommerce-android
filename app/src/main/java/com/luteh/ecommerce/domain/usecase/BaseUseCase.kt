package com.luteh.ecommerce.domain.usecase

import arrow.core.Either
import com.luteh.ecommerce.BuildConfig

abstract class BaseUseCase<out Result, in Params> {
    suspend operator fun invoke(params: Params): Either<Exception, Result> {
        return try {
            execute(params)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            Either.Left(e)
        }
    }

    abstract suspend fun execute(params: Params): Either<Exception, Result>
}

abstract class BaseUseCaseWithoutParam<out Result> {
    suspend operator fun invoke(): Either<Exception, Result> {
        return try {
            execute()
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            Either.Left(e)
        }
    }

    abstract suspend fun execute(): Either<Exception, Result>
}