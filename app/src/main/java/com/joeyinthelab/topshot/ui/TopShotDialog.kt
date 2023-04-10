package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopShotDialog(
    momentFlowId: String,
    onDismiss: () -> Unit
) {
    val momentFlowUrl = "https://assets.nbatopshot.com/media/$momentFlowId/video"

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.height(350.dp),
        onDismissRequest = { onDismiss() },
    ) {
        TopShotVideoPlayer(uri = momentFlowUrl)
    }
}
