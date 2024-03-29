package com.example.bookingapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HostViewModel : ViewModel() {

    private val _actionButtonVisible = MutableStateFlow(true)
    private val _actionButtonClicked = MutableSharedFlow<Unit>()
    private val _toolbarTitle = MutableStateFlow("")

    val actionButtonVisible: StateFlow<Boolean> = _actionButtonVisible
    val actionButtonClicked: Flow<Unit> = _actionButtonClicked
    val toolbarTitle: StateFlow<String> = _toolbarTitle

    fun setActionButtonVisible(isVisible: Boolean) {
        _actionButtonVisible.value = isVisible
    }

    fun onActionButtonClicked() {
        viewModelScope.launch {
            _actionButtonClicked.emit(Unit)
        }
    }

    fun setToolbarTitle(title: String) {
       _toolbarTitle.value = title
    }
}