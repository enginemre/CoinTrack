package com.engin.cointrack.feature.favourite.data.usecase

import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.feature.favourite.domain.GetAllFavouritesUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllFavouritesUseCaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : GetAllFavouritesUseCase {
    override fun invoke() = callbackFlow {
        if (firebaseAuth.currentUser == null) {
            trySend(emptyList())
        }
        fireStore.collection(Credentials.FIRE_STORE_FAVOURITES)
            .document(firebaseAuth.currentUser!!.uid)
            .collection(Credentials.FIRE_STORE_COINS)
            .get()
            .addOnSuccessListener { snapShot ->
                val list = snapShot.map {
                    Coin(
                        id = (it.data["id"] as? String).orEmpty(),
                        name = (it.data["name"] as? String).orEmpty(),
                        symbol = (it.data["symbol"] as? String).orEmpty(),
                        imageUrl = (it.data["imageUrl"] as? String).orEmpty(),
                        isFavourite = (it.data["favourite"] as? Boolean) ?: false
                    )
                }
                trySend(list)
            }
            .addOnFailureListener {
                throw it
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)
}