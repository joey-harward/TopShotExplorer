package com.joeyinthelab.topshot.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.joeyinthelab.topshot.ui.component.MomentCollection
import com.joeyinthelab.topshot.ui.dialog.MomentVideoDialog

@Composable
fun MomentsScreen() {
	var topShotMomentId by remember { mutableStateOf("") }
	var showTopShotDialog by remember { mutableStateOf(false) }

	if (showTopShotDialog) {
		MomentVideoDialog(
			momentFlowId = topShotMomentId,
			onDismiss = { showTopShotDialog = false },
		)
	}

	MomentCollection(onMomentClick = { momentFlowId ->
		run {
			topShotMomentId = momentFlowId
			showTopShotDialog = true
		}
	})
}
