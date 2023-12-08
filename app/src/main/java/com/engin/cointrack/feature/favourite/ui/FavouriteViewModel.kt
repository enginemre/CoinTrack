package com.engin.cointrack.feature.favourite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.feature.favourite.domain.GetAllFavouritesUseCase
import com.engin.cointrack.feature.favourite.domain.RemoveFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<Throwable>()
    val error = _error.receiveAsFlow()

    private val _snackBar = Channel<String>()
    val snackBar = _snackBar.receiveAsFlow()

    private val _favouriteList = MutableSharedFlow<List<Coin>>(replay = 1)
    val favouriteList = _favouriteList.asSharedFlow()

    private val _removeFavourite = Channel<Coin>()
    val removeFavourite = _removeFavourite.receiveAsFlow()

    fun removeFavourite(coin: Coin) {
        removeFavouriteUseCase(coin)
            .catch { throwable -> handleError(throwable) }
            .onEach {
                if (it)
                    _removeFavourite.send(coin)
                else
                    _snackBar.send("Failed")
            }
            .launchIn(viewModelScope)
    }

    fun getAllFavouriteItems(shouldSilently: Boolean = false) {
        getAllFavouritesUseCase()
            .onStart { if (!shouldSilently) _isLoading.update { true } }
            .catch { throwable -> handleError(throwable) }
            .onEach { data ->
                _favouriteList.emit(data)
                _isLoading.update { false }
            }
            .onCompletion { if (!shouldSilently) _isLoading.update { false } }
            .launchIn(viewModelScope)
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
        _error.trySend(throwable)
    }

}