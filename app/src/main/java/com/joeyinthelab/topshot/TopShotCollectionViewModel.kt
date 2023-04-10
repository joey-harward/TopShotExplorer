package com.joeyinthelab.topshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeyinthelab.topshot.CollectionUiState.Loading
import com.joeyinthelab.topshot.CollectionUiState.Success
import com.joeyinthelab.topshot.repository.AppDataRepository
import com.joeyinthelab.topshot.repository.TopShotCollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TopShotCollectionViewModel @Inject constructor(
    appDataRepository: AppDataRepository,
    collectionRepository: TopShotCollectionRepository,
) : ViewModel() {
    private val accountAddress: Flow<String> =
        appDataRepository.appData.map { it.accountAddress }

    val collections: StateFlow<CollectionUiState> =
        collectionRepository.collection.map {
            Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Loading,
        )
}

sealed interface CollectionUiState {
    object Loading : CollectionUiState
    data class Success(val collection: List<String>) : CollectionUiState
}
