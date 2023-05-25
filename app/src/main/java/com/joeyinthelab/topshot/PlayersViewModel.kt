package com.joeyinthelab.topshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.joeyinthelab.topshot.model.Moment
import com.joeyinthelab.topshot.repository.TopShotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
	private val topShotRepository: TopShotRepository,
) : ViewModel() {
	private val openAI = OpenAI(BuildConfig.OPENAI_TOKEN)

	private val _playersUiState = MutableStateFlow<PlayersUiState>(PlayersUiState.Idle)
	val playersUiState: StateFlow<PlayersUiState>
		get() = _playersUiState

	fun resetPlayerState() {
		_playersUiState.value = PlayersUiState.Idle
	}

	@OptIn(BetaOpenAI::class)
	fun playerMomentSearch(query: String) {
		_playersUiState.value = PlayersUiState.Loading

		viewModelScope.launch {
			val chatCompletionRequest = ChatCompletionRequest(
				model = ModelId("gpt-3.5-turbo-0301"),
				messages = listOf(
					ChatMessage(
						role = ChatRole.User,
						content = "comma separated list of NBA players $query"
					)
				)
			)

			val collegePlayers: List<String> = try {
				val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
				val playerCSV = completion.choices[0].message?.content.orEmpty()

				playerCSV.split(",").map { it.trim() }
			} catch (e: Exception) {
				emptyList()
			}

			val playerMoments = mutableListOf<Moment>()

			try {
				val allPlayerIds = mutableListOf<String>()
				if (collegePlayers.isNotEmpty()) {
					collegePlayers.forEach { player ->
						if (player.isNotEmpty()) {
							val suggestedPlayers = topShotRepository.searchPlayers(player)
							if (suggestedPlayers.isNotEmpty()) {
								allPlayerIds.addAll(suggestedPlayers.map { it.id })
							}
						}
					}
				}

				if (allPlayerIds.isNotEmpty()) {
					allPlayerIds.forEach {
						val latestPlayerMoment = topShotRepository.getPlayerMoments(listOf(it))
						playerMoments.add(latestPlayerMoment.first())
					}
				}
			} catch (_: Exception) {}

			_playersUiState.value = PlayersUiState.Success(playerMoments)
		}
	}
}

sealed interface PlayersUiState {
	object Idle : PlayersUiState
	object Loading : PlayersUiState
	data class Success(val playerMoments: List<Moment>) : PlayersUiState
}
