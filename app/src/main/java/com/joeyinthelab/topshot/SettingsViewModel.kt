package com.joeyinthelab.topshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeyinthelab.topshot.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        appDataRepository.appData
            .map { appData ->
                SettingsUiState.Success(
                    settings = EditableAppData(
                        username = appData.username,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading,
            )

    fun updateUsername(username: String) {
        if (username.isNotEmpty()) {
            viewModelScope.launch {
                appDataRepository.setUsername(username)
            }
        }
    }
}

data class EditableAppData(
    val username: String
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: EditableAppData) : SettingsUiState
}
