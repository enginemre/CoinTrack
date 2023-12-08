package com.engin.cointrack.feature.coin_detail.data.usecase

import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.feature.coin_detail.data.mapper.toCoin
import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import com.engin.cointrack.feature.coin_detail.domain.usecase.AddFavouriteUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddFavouriteUseCaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : AddFavouriteUseCase {
    override fun invoke(coinDetail: CoinDetail) = callbackFlow<Boolean>{
        if (firebaseAuth.currentUser == null){
            trySend(false)
        }
        val coin  = coinDetail.toCoin()
        fireStore.collection(Credentials.FIRE_STORE_FAVOURITES)
            .document(firebaseAuth.currentUser!!.uid)
            .collection(Credentials.FIRE_STORE_COINS)
            .add(coin.copy(isFavourite = true))
            .addOnCompleteListener {task ->
                if (task.isSuccessful)
                    trySend(true)
                else
                    trySend(false)
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)
}