package com.joeyinthelab.topshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeyinthelab.topshot.CollectionUiState.Loading
import com.joeyinthelab.topshot.CollectionUiState.Success
import com.joeyinthelab.topshot.model.Moment
import com.joeyinthelab.topshot.repository.AppDataRepository
import com.joeyinthelab.topshot.repository.TopShotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MomentCollectionViewModel @Inject constructor(
	appDataRepository: AppDataRepository,
	private val topShotRepository: TopShotRepository,
) : ViewModel() {
	val collectionUiState: StateFlow<CollectionUiState> =
		appDataRepository.appData
			.map {
				val collection = topShotRepository.getUserMoments(it.username)
				Success(collection)
			}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5_000),
				initialValue = Loading,
			)
}

sealed interface CollectionUiState {
	object Loading : CollectionUiState
	data class Success(val collection: List<Moment>) : CollectionUiState
}
