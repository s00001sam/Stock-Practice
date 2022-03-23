package com.sam.stockassignment.view.edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(): ViewModel() {

    private val _leaveControl = MutableStateFlow<Boolean>(false)
    val leaveControl : StateFlow<Boolean> = _leaveControl

    val edit = MutableStateFlow<String>("")
    val isShowRed = MutableStateFlow<Boolean>(true)

    fun leave() {
        _leaveControl.value = true
    }

    fun nothing() {}

    fun leaveComplete() {
        _leaveControl.value = false
    }

}