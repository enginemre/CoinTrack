package com.engin.cointrack.feature.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _loggedIn = Channel<Unit>()
    val loggedIn = _loggedIn.receiveAsFlow()


    fun login(email: String, password: String) {
        if (email.isBlank()){
            _error.trySend("Email couldn't be empty")
            return
        }
        if (password.isBlank()){
            _error.trySend("Password Couldn't be empty")
            return
        }
        _isLoading.update { true }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _isLoading.update { false }
            _loggedIn.trySend(Unit)
        }.addOnFailureListener {
            _isLoading.update { false }
            _error.trySend(it.message.toString())
        }
    }
}