package com.joeyinthelab.topshot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.joeyinthelab.topshot.model.MomentNFT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MomentCard(
    momentNFT: MomentNFT,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onClick(momentNFT.flowId) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.inversePrimary,
        ),
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = momentNFT.flowId,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                contentScale = ContentScale.Fit,
                model = "${momentNFT.assetPathPrefix}Hero_2880_2880_Black.jpg?quality=60&width=480",
                contentDescription = null
            )
        }
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "tier: ${momentNFT.tier}",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "serial number: ${momentNFT.flowSerialNumber}",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "set name: ${momentNFT.setFlowName}",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
