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
import com.joeyinthelab.topshot.model.Moment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MomentCard(
	moment: Moment,
	onClick: (String) -> Unit,
	modifier: Modifier = Modifier,
) {
    val momentAssetUrl = "https://assets.nbatopshot.com/media/${moment.flowId}/transparent"
    Card(
        onClick = { onClick(moment.flowId) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.secondary,
        ),
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = moment.playHeadline,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            AsyncImage(
                contentScale = ContentScale.Fit,
                //model = "${moment.assetPathPrefix}Hero_2880_2880_Black.jpg?quality=60&width=480",
                model = momentAssetUrl,
                contentDescription = null
            )
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = moment.playShortDescription,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "From Set: ${moment.setFlowName}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Serial Number: ${moment.flowSerialNumber}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
