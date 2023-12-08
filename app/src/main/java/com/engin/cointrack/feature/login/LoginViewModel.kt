package com.engin.cointrack.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engin.cointrack.feature.login.domain.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _loggedIn = Channel<Unit>()
    val loggedIn = _loggedIn.receiveAsFlow()

    fun login(email: String, password: String) {
        loginUserUseCase(email, password)
            .onStart {
                _isLoading.update { true }
            }.onEach {
                _loggedIn.trySend(Unit)
                _isLoading.update { false }
            }
            .catch {
                _error.send("")
                _isLoading.update { false }
            }
            .launchIn(viewModelScope)
    }
}