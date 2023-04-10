package com.joeyinthelab.topshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeyinthelab.topshot.MainActivityUiState.Loading
import com.joeyinthelab.topshot.MainActivityUiState.Success
import com.joeyinthelab.topshot.model.AppData
import com.joeyinthelab.topshot.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appDataRepository: AppDataRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = appDataRepository.appData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val appData: AppData) : MainActivityUiState
}
