package com.engin.cointrack.feature.favourite.data.usecase

import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.feature.coin_detail.data.mapper.toCoin
import com.engin.cointrack.feature.favourite.domain.RemoveFavouriteUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoveFavouriteUseCaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : RemoveFavouriteUseCase {
    override fun invoke(coin: Coin) = callbackFlow{
        if (firebaseAuth.currentUser == null){
            trySend(false)
        }
        fireStore.collection(Credentials.FIRE_STORE_FAVOURITES)
            .document(firebaseAuth.currentUser!!.uid)
            .collection(Credentials.FIRE_STORE_COINS)
            .whereEqualTo("id",coin.id)
            .get().addOnSuccessListener { result->
                result.documents.forEach{
                    fireStore.collection(Credentials.FIRE_STORE_FAVOURITES)
                        .document(firebaseAuth.currentUser!!.uid)
                        .collection(Credentials.FIRE_STORE_COINS)
                        .document(it.id).delete()
                        .addOnCompleteListener {task ->
                            if (task.isSuccessful)
                                trySend(true)
                            else
                                trySend(false)
                        }
                }
            }
            .addOnFailureListener {
                trySend(false)
            }
        awaitClose()
    }

}