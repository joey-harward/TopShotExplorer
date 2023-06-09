package com.joeyinthelab.topshot.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joeyinthelab.topshot.CollectionUiState.Loading
import com.joeyinthelab.topshot.CollectionUiState.Success
import com.joeyinthelab.topshot.MomentCollectionViewModel

@Composable
fun MomentCollection(
	onMomentClick: (String) -> Unit,
	viewModel: MomentCollectionViewModel = hiltViewModel(),
) {
	val collectionUiState by viewModel.collectionUiState.collectAsStateWithLifecycle()
	when (val collectionState = collectionUiState) {
		Loading -> {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier.fillMaxSize()
			) {
				CircularProgressIndicator()
			}
		}
		is Success -> {
			val collection = collectionState.collection
			if (collection.isNotEmpty()) {
				LazyColumn(
					contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
					verticalArrangement = Arrangement.spacedBy(16.dp),
				) {
					items(collection) { moment ->
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
						text = "No Collections Found",
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.titleMedium,
					)
					Spacer(modifier = Modifier.padding(5.dp))
					Text(
						text = "Either the username doesn't exist or hasn't been set yet. You can update your username in Settings.",
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.bodyLarge,
					)
				}
			}
		}
	}
}

