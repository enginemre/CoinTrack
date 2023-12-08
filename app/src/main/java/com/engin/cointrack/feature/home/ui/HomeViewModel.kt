package com.engin.cointrack.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsByNameAndSlug
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsFromDBUseCase
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsUseCase
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
class HomeViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getCoinsByNameAndSlug: GetCoinsByNameAndSlug,
    private val getCoinsFromDBUseCase: GetCoinsFromDBUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<Throwable>()
    val error = _error.receiveAsFlow()

    private val _coinList = MutableSharedFlow<List<Coin>>(replay = 1)
    val coinList = _coinList.asSharedFlow()

    private val _originalCoinList = MutableStateFlow<List<Coin>>(mutableListOf())
    private val originalCoinList = _originalCoinList.asStateFlow()

    private var initRequest = true
    fun getCoins(isRefresh: Boolean = false) {
        if (initRequest || isRefresh) {
            getCoinsUseCase()
                .onStart { _isLoading.update { true } }
                .catch { throwable -> handleError(throwable) }
                .onEach { data ->
                    _coinList.emit(data)
                    _originalCoinList.update { data }
                }
                .onCompletion { _isLoading.update { false } }
                .launchIn(viewModelScope)
            if (initRequest) initRequest = false
        }
    }

    fun getCoinsWithQuery(query: String?) {
        if (query == null) return
        if (query.isEmpty()) {
            _coinList.tryEmit(originalCoinList.value)
            return
        }
        getCoinsByNameAndSlug(query)
            .onStart { _isLoading.update { true } }
            .catch { throwable -> handleError(throwable) }
            .onEach { data ->
                _coinList.emit(data)
            }.onCompletion { _isLoading.update { false } }
            .launchIn(viewModelScope)
    }

    fun getCoinsFromLocalDB() {
        if (originalCoinList.value.isEmpty())
            getCoinsFromDBUseCase()
                .onStart { _isLoading.update { true } }
                .catch { throwable -> handleError(throwable) }
                .onEach { data ->
                    _coinList.emit(data)
                }.onCompletion { _isLoading.update { false } }
                .launchIn(viewModelScope)
        else
            _coinList.tryEmit(originalCoinList.value)
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
        _error.trySend(throwable)
    }
}