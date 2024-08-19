package com.luteh.ecommerce.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.luteh.ecommerce.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            val account = authRepository.getAccount()
            _state.value = _state.value.copy(email = account)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            googleSignInClient.signOut()
            authRepository.setLoginSession(false)
            _effect.send(Effect.NavigateToLoginScreen)
        }
    }

    data class State(
        val email: String = ""
    )

    sealed interface Effect {
        data object NavigateToLoginScreen : Effect
    }
}