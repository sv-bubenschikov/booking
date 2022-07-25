package com.example.bookingapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import com.example.bookingapp.domain.usecases.user.UpdateCurrentUserRefUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val getCurrentUserRefUseCase: GetCurrentUserRefUseCase,
    private val updateCurrentUserRefUseCase: UpdateCurrentUserRefUseCase,
) : ViewModel() {

    private val _currentUserRef = MutableStateFlow<FirebaseUser?>(null)
    private val _actionButtonVisible = MutableStateFlow(true)
    private val _actionButtonClicked = MutableSharedFlow<Unit>()
    private val _toolbarTitle = MutableStateFlow("")

    val currentUserRef: StateFlow<FirebaseUser?> = _currentUserRef
    val actionButtonVisible: StateFlow<Boolean> = _actionButtonVisible
    val actionButtonClicked: Flow<Unit> = _actionButtonClicked
    val toolbarTitle: StateFlow<String> = _toolbarTitle

    fun setActionButtonVisible(isVisible: Boolean) {
        _actionButtonVisible.value = isVisible
    }

    fun updateCurrentUserRef(): Job {
        return viewModelScope.launch {
            updateCurrentUserRefUseCase()
            _currentUserRef.value = getCurrentUserRefUseCase()
        }
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