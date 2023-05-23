package com.joeyinthelab.topshot.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.joeyinthelab.topshot.ui.component.PlayerMomentCollection
import com.joeyinthelab.topshot.ui.dialog.MomentVideoDialog

@Composable
fun PlayersScreen() {
	var topShotMomentId by remember { mutableStateOf("") }
	var showTopShotDialog by remember { mutableStateOf(false) }

	if (showTopShotDialog) {
		MomentVideoDialog(
			momentFlowId = topShotMomentId,
			onDismiss = { showTopShotDialog = false },
		)
	}

	PlayerMomentCollection(
		onMomentClick = { momentFlowId ->
			run {
				topShotMomentId = momentFlowId
				showTopShotDialog = true
			}
		}
	)
}
