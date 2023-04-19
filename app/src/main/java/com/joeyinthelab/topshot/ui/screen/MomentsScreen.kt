package com.joeyinthelab.topshot.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.joeyinthelab.topshot.ui.component.MomentCollection
import com.joeyinthelab.topshot.ui.dialog.MomentVideoDialog

@Composable
fun MomentsScreen() {
    val topShotMomentId = remember { mutableStateOf("") }
    val showTopShotDialog = remember { mutableStateOf(false) }

    if (showTopShotDialog.value) {
        MomentVideoDialog(
            momentFlowId = topShotMomentId.value,
            onDismiss = { showTopShotDialog.value = false },
        )
    }

    MomentCollection(onMomentClick = { momentFlowId ->
        run {
            topShotMomentId.value = momentFlowId
            showTopShotDialog.value = true
        }
    })
}
