package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
)
@Composable
fun TopShotScaffolding(
) {
    val showSettingsDialog = remember { mutableStateOf(false) }
    val topShotMomentId = remember { mutableStateOf("") }
    val showTopShotDialog = remember { mutableStateOf(false) }

    if (showSettingsDialog.value) {
        SettingsDialog(
            onDismiss = { showSettingsDialog.value = false },
        )
    }

    if (showTopShotDialog.value) {
        TopShotDialog(
            momentFlowId = topShotMomentId.value,
            onDismiss = { showTopShotDialog.value = false },
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Column {
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
            CenterAlignedTopAppBar(
                title = { Text(text = "Your Top Shot Moments") },
                actions = {
                    IconButton(onClick = { showSettingsDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )

            TopShotCollection(onMomentClick = { momentFlowId ->
                run {
                    topShotMomentId.value = momentFlowId
                    showTopShotDialog.value = true
                }
            })
        }
    }
}
