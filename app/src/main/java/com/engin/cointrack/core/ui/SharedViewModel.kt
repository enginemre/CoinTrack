package com.engin.cointrack.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engin.cointrack.core.util.Destinations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _destination = MutableSharedFlow<Destinations>()
    val destination = _destination.asSharedFlow()

    fun navigate(destination: Destinations) {
        viewModelScope.launch { _destination.emit(destination) }
    }

    fun isUserLoggedIn() =
        firebaseAuth.currentUser != null

    fun signOut() {
        firebaseAuth.signOut()
        navigate(destination = Destinations.Login)
    }

}