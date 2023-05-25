package com.joeyinthelab.topshot.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joeyinthelab.topshot.PlayersUiState
import com.joeyinthelab.topshot.PlayersViewModel

@Composable
fun PlayerMomentCollection(
	onMomentClick: (String) -> Unit,
	viewModel: PlayersViewModel = hiltViewModel(),
) {
	val playersUiState by viewModel.playersUiState.collectAsStateWithLifecycle()

	var query by remember { mutableStateOf("") }

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		OutlinedTextField(
			modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 16.dp),
			label = { Text(text = "Search Player Moments") },
			value = query,
			singleLine = true,
			onValueChange = {
				query = it
				viewModel.resetPlayerState()
			},
			enabled = playersUiState != PlayersUiState.Loading,
			trailingIcon = {
				IconButton(onClick = { viewModel.playerMomentSearch(query) }){
					Icon(
						imageVector = Icons.Filled.Search,
						contentDescription = "Search"
					)
				}
			},
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Done,
				keyboardType = KeyboardType.Text
			),
			keyboardActions = KeyboardActions(
				onDone = { viewModel.playerMomentSearch(query) }
			),
		)

		when (val playersState = playersUiState) {
			PlayersUiState.Loading -> {
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier.fillMaxSize()
				) {
					CircularProgressIndicator()
				}
			}

			is PlayersUiState.Success -> {
				val playerMoments = playersState.playerMoments
				if (playerMoments.isNotEmpty()) {
					LazyColumn(
						contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
						verticalArrangement = Arrangement.spacedBy(16.dp),
					) {
						items(playerMoments) { moment ->
							MomentCard(
								moment = moment,
								onClick = onMomentClick,
							)
						}
					}
				} else {
					Column(
						modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "No NBA Players found that match that query",
							textAlign = TextAlign.Center,
							style = MaterialTheme.typography.titleMedium,
						)
						Spacer(modifier = Modifier.padding(5.dp))
					}
				}
			}
			else -> {}
		}
	}
}

