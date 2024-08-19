package com.luteh.ecommerce.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.luteh.ecommerce.common.BaseViewModel
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.domain.repository.AuthRepository
import com.luteh.ecommerce.domain.usecase.GetLoginSessionUsecase
import com.luteh.ecommerce.domain.usecase.SaveLoginSessionUsecase
import com.luteh.ecommerce.domain.usecase.SaveLoginSessionUsecaseParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveLoginSessionUsecase: SaveLoginSessionUsecase,
    private val getloginSessionUsecase: GetLoginSessionUsecase,
    private val authRepository: AuthRepository
) : BaseViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>(State()) {

    init {
        checkLoginSession()
    }

    private fun checkLoginSession() {
        viewModelScope.launch {
            updateState { it.copy(loginState = ResultState.Loading) }
            val isLoggedIn = getloginSessionUsecase().getOrNull() ?: false
            if (isLoggedIn) {
                sendEffect(Effect.NavigateToMainScreen)
            }
            updateState { it.copy(loginState = ResultState.Idle) }
        }
    }

    override fun processEvent(event: Event) {
        when (event) {
            is Event.OnSuccessGoogleSignIn -> {
                saveLoginSession(event.account)
            }

            Event.OnClickGoogleSignInButton -> launchGoogleSignIn()

            is Event.OnFailureGoogleSignIn -> sendErrorGoogleSignIn(event.message)

            is Event.OnChangePasswordText -> changePasswordText(event.value)
            is Event.OnChangeEmailText -> changeEmailText(event.value)
            Event.OnClickLoginButton -> doLogin()
            Event.OnClickRegisterButton -> navigateToRegisterScreen()
        }
    }

    private fun navigateToRegisterScreen() {
        sendEffect(Effect.NavigateToRegisterScreen)
    }

    private fun sendErrorGoogleSignIn(message: String) {
        updateState { it.copy(loginState = ResultState.Error(Exception(message))) }
        sendEffect(Effect.ShowToast(message))
    }

    private fun launchGoogleSignIn() {
        updateState { it.copy(loginState = ResultState.Loading) }
        sendEffect(Effect.LaunchGoogleSignIn)
    }

    private fun doLogin() {
        viewModelScope.launch {
            updateState { it.copy(loginState = ResultState.Loading) }

            authRepository.login(email = state.value.email, password = state.value.password)
                .fold({ exception ->
                    updateState { it.copy(loginState = ResultState.Error(exception)) }
                    sendEffect(Effect.ShowToast(exception.message.toString()))
                }, {
                    updateState { it.copy(loginState = ResultState.Success(Unit)) }
                    sendEffect(Effect.NavigateToMainScreen)
                })
        }
    }

    private fun changePasswordText(value: String) {
        updateState { it.copy(password = value) }
    }

    private fun changeEmailText(value: String) {
        updateState { it.copy(email = value) }
    }

    private fun saveLoginSession(account: GoogleSignInAccount) {
        viewModelScope.launch {
            saveLoginSessionUsecase(SaveLoginSessionUsecaseParam(account)).fold({ exception ->
                updateState { it.copy(loginState = ResultState.Error(exception)) }
                sendEffect(Effect.ShowToast(exception.message.toString()))
            }, {
                updateState { it.copy(loginState = ResultState.Success(Unit)) }
                sendEffect(Effect.NavigateToMainScreen)
            })
        }
    }

    data class State(
        val loginState: ResultState<Unit> = ResultState.Idle,
        val email: String = "",
        val password: String = "",
    )

    sealed interface Event {
        data class OnSuccessGoogleSignIn(val account: GoogleSignInAccount) : Event
        data class OnFailureGoogleSignIn(val message: String) : Event
        data class OnChangeEmailText(val value: String) : Event
        data class OnChangePasswordText(val value: String) : Event
        data object OnClickGoogleSignInButton : Event
        data object OnClickLoginButton : Event
        data object OnClickRegisterButton : Event
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMainScreen : Effect
        data object LaunchGoogleSignIn : Effect
        data object NavigateToRegisterScreen : Effect
    }
}