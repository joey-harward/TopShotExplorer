package com.joeyinthelab.topshot.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.joeyinthelab.topshot.SettingsUiState
import com.joeyinthelab.topshot.SettingsViewModel

@Composable
fun SettingsDialog(
	onDismiss: () -> Unit,
	viewModel: SettingsViewModel = hiltViewModel(),
) {
	val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
	val configuration = LocalConfiguration.current

	var usernameInitialized by remember { mutableStateOf(false) }
	var username by remember { mutableStateOf("") }
	if (username.isNotEmpty()) {
		usernameInitialized = true
	}

	AlertDialog(
		properties = DialogProperties(usePlatformDefaultWidth = false),
		modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
		onDismissRequest = {
			viewModel.updateUsername(username)
			onDismiss()
		},
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
						SettingsDialogSectionTitle(text = "Account Info")
						OutlinedTextField(
							value = if (usernameInitialized) { username } else {
								(settingsUiState as SettingsUiState.Success).settings.username
							},
							onValueChange = { s -> username = s },
							label = { Text("Username") }
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
					.clickable {
						viewModel.updateUsername(username)
						onDismiss()
					},
			)
		},
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

