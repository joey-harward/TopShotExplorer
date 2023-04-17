package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joeyinthelab.topshot.CollectionUiState.Loading
import com.joeyinthelab.topshot.CollectionUiState.Success
import com.joeyinthelab.topshot.TopShotCollectionViewModel

@Composable
fun TopShotCollection(
    onMomentClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopShotCollectionViewModel = viewModel(),
) {
    val collectionState by viewModel.collectionUiState.collectAsStateWithLifecycle()
    when (collectionState) {
        Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is Success -> {
            val collection = (collectionState as Success).collection
            if (collection.isNotEmpty()) {
                LazyColumn {
                    items(collection.toList()) { momentFlowId ->
                        val momentFlowUrl = "https://assets.nbatopshot.com/media/$momentFlowId/transparent"
                        TopShotCard(
                            momentFlowId = momentFlowId,
                            momentFlowUrl = momentFlowUrl,
                            onClick = onMomentClick,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                        )
                    }
                }
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No Collections Found",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

