package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopShotCard(
    momentFlowId: String,
    momentFlowUrl: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onClick(momentFlowId) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = momentFlowId,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        AsyncImage(
            contentScale = ContentScale.Fit,
            model = momentFlowUrl,
            contentDescription = null
        )
    }
}
