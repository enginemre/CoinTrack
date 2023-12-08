package com.engin.cointrack.feature.coin_detail.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import com.engin.cointrack.feature.coin_detail.domain.usecase.AddFavouriteUseCase
import com.engin.cointrack.feature.coin_detail.domain.usecase.GetCoinDetailUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CoinDetailViewModel @AssistedInject constructor(
    @Assisted(COIN_ID) val id: String,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase
) : ViewModel() {

    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<Throwable>()
    val error = _error.receiveAsFlow()

    private val _snackBar = Channel<String>()
    val snackBar = _snackBar.receiveAsFlow()

    private val _coinDetail = MutableStateFlow<CoinDetail?>(CoinDetail())
    val coinDetail = _coinDetail.asStateFlow()


    private var interval = DEFAULT_INTERVAL

    fun getCoinDetail() {
        getCoinDetailUseCase(id)
            .onStart { _isLoading.update { true } }
            .catch { throwable -> handleError(throwable) }
            .onEach { data ->
                _coinDetail.emit(data)
            }
            .onCompletion { _isLoading.update { false } }
            .launchIn(viewModelScope)
    }

    fun addFavourite() {
        coinDetail.value?.let { coinDetail ->
            addFavouriteUseCase(coinDetail)
                .catch { throwable -> handleError(throwable) }
                .onEach {
                    _snackBar.send(if (it) "Success" else "Failed")
                }
                .launchIn(viewModelScope)
        }
    }

    fun updateInterval(interval: String) {
        try {
            if (interval.length < 3) {
                _error.trySend(Throwable(message = "Interval should be minumum 1 second"))
                return
            } else {
                this.interval = interval.toLong()
                updateSilently()
            }
        } catch (e: Exception) {
            _error.trySend(Throwable(message = "Invalid interval"))
        }
    }


    private fun updateSilently() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = object : Runnable {
            override fun run() {
                getCoinDetailUseCase(id)
                    .catch { it.printStackTrace() }
                    .onEach { data -> _coinDetail.emit(data) }
                    .launchIn(viewModelScope)
                handler.postDelayed(this, interval)
            }
        }
        handler.post(runnable!!)
    }

    fun registerRunnable() {
        updateSilently()
    }

    fun unRegisterRunnable() {
        runnable?.let {
            handler.removeCallbacks(it)
            runnable = null
        }
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
        viewModelScope.launch { _error.send(throwable)  }

    }

    override fun onCleared() {
        super.onCleared()
        unRegisterRunnable()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(COIN_ID) coinID: String): CoinDetailViewModel
    }

    companion object {
        const val COIN_ID = "id"
        const val DEFAULT_INTERVAL = 60000L

        fun provideCoinDetailViewModel(
            factory: Factory,
            id: String
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(id) as T
            }
        }
    }
}