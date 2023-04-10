package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joeyinthelab.topshot.EditableAppData
import com.joeyinthelab.topshot.SettingsUiState
import com.joeyinthelab.topshot.SettingsViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = viewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    SettingsDialog(
        onDismiss = onDismiss,
        settingsUiState = settingsUiState,
        onChangeIsTestnet = viewModel::updateIsTestnet,
        onChangeAccountAddress = viewModel::updateAccountAddress,
    )
}

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    settingsUiState: SettingsUiState,
    onChangeIsTestnet: (isTestnet: Boolean) -> Unit,
    onChangeAccountAddress: (accountAddress: String) -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (settingsUiState) {
                    SettingsUiState.Loading -> {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    is SettingsUiState.Success -> {
                        SettingsPanel(
                            settings = settingsUiState.settings,
                            onChangeIsTestnet = onChangeIsTestnet,
                            onChangeAccountAddress = onChangeAccountAddress,
                        )
                    }
                }
                Divider(Modifier.padding(top = 16.dp))
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}

@Composable
private fun SettingsPanel(
    settings: EditableAppData,
    onChangeIsTestnet: (isTestnet: Boolean) -> Unit,
    onChangeAccountAddress: (accountAddress: String) -> Unit,
) {
    SettingsDialogSectionTitle(text = "Network")
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = "Testnet",
            selected = settings.isTestnet,
            onClick = { onChangeIsTestnet(true) },
        )
        SettingsDialogThemeChooserRow(
            text = "Mainnet",
            selected = !settings.isTestnet,
            onClick = { onChangeIsTestnet(false) },
        )
    }
    SettingsDialogSectionTitle(text = "Account")
    OutlinedTextField(
        value = settings.accountAddress,
        onValueChange = { accountAddress -> onChangeAccountAddress(accountAddress) },
        label = { Text("Address") }
    )
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

