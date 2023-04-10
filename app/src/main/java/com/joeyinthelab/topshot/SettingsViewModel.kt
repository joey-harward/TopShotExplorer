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
                        isTestnet = appData.isTestnet,
                        accountAddress = appData.accountAddress,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading,
            )

    fun updateIsTestnet(isTestnet: Boolean) {
        viewModelScope.launch {
            appDataRepository.setIsTestnet(isTestnet)
        }
    }

    fun updateAccountAddress(accountAddress: String) {
        viewModelScope.launch {
            appDataRepository.setAccountAddress(accountAddress)
        }
    }
}

data class EditableAppData(
    val isTestnet: Boolean,
    val accountAddress: String,
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: EditableAppData) : SettingsUiState
}
