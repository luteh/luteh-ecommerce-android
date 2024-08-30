package com.luteh.ecommerce.ui.screen.register

import arrow.core.Either
import com.luteh.ecommerce.MainDispatcherRule
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RegisterViewModel

    private val authRepository: AuthRepository = mock()

    //    @Before
    private fun setUp() {
        viewModel = RegisterViewModel(authRepository = authRepository)
    }

    @Test
    fun `when getUserRoles is failed, then return error result`() = runTest {
        whenever(authRepository.getUserRoles()).thenReturn(Either.Left(RuntimeException()))

        setUp()

        verify(authRepository, times(1)).getUserRoles()
        assertTrue(viewModel.state.value.getRolesResult is ResultState.Error)
        assertTrue(viewModel.effect.first() is RegisterViewModel.Effect.ShowToast)
    }

    @Test
    fun `when getUserRoles is success, then return success result`() = runTest {
        whenever(authRepository.getUserRoles()).thenReturn(Either.Right(emptyList()))

        setUp()

        verify(authRepository, times(1)).getUserRoles()
        assertTrue(viewModel.state.value.getRolesResult is ResultState.Success)
    }
}