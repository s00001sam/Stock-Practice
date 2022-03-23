package com.sam.stockassignment.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.stockassignment.MyApplication
import com.sam.stockassignment.R
import com.sam.stockassignment.data.State
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.repo.Repository
import com.sam.stockassignment.repo.usecase.GetStocks
import com.sam.stockassignment.util.Logger
import com.sam.stockassignment.util.SpUtil
import com.sam.stockassignment.util.SpUtil.KEY_STOCKS
import com.sam.stockassignment.util.toStockLocalDatas
import com.sam.stockassignment.util.toYrMonDayStr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val getStocks: GetStocks
): ViewModel() {

    private val _apiStockResult = MutableStateFlow<State<List<StockLocalData>>>(State.nothing())
    val apiStockResult: StateFlow<State<List<StockLocalData>>> = _apiStockResult

    private val _needTimer = MutableStateFlow<Boolean>(false)
    val needTimer: StateFlow<Boolean> = _needTimer

    var hasGetDB = false

    val dbStocks = repository.getAll()

    fun completeGetDB() {
        hasGetDB = true
    }

    fun getStocks() {
        viewModelScope.launch {
            try {
                getStocks.getFlow(null)
                    .onStart { _apiStockResult.value = State.loading() }
                    .collect {
                        if (it?.isSuccess() == true && it.data != null) {
                            val localStocks = it.toStockLocalDatas()
                            _apiStockResult.value = State.success(localStocks)
                            repository.insertStocks(localStocks ?: listOf())
                            SpUtil.putString(KEY_STOCKS, System.currentTimeMillis().toYrMonDayStr())
                        } else {
                            _apiStockResult.value = State.error(MyApplication.appContext.getString(R.string.get_data_error))
                        }
                    }
            } catch (e: Exception) {
                _apiStockResult.value = State.error(e.localizedMessage)
                Logger.d("getStocks error=${e.localizedMessage}")
            }

        }
    }

    fun apiStockResultComplete() {
        _apiStockResult.value = State.nothing()
    }

    fun triggerTimer() {
        _needTimer.value = true
    }

    fun triggerTimerComplete() {
        _needTimer.value = false
    }
}