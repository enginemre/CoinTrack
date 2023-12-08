package com.engin.cointrack.feature.login.data

import com.engin.cointrack.feature.login.domain.LoginUserUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LoginUserUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginUserUseCase {
    override fun invoke(email: String, password: String) = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            trySend(true)
        }.addOnFailureListener {
            trySend(false)
        }
        awaitClose()
    }
}