package com.joeyinthelab.topshot.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joeyinthelab.topshot.navigation.AppDestinations
import com.joeyinthelab.topshot.navigation.AppDrawer
import com.joeyinthelab.topshot.navigation.AppNavigationActions
import com.joeyinthelab.topshot.ui.dialog.SettingsDialog
import com.joeyinthelab.topshot.ui.screen.MomentsScreen
import com.joeyinthelab.topshot.ui.screen.PlayersScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
	val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = currentNavBackStackEntry?.destination?.route ?: AppDestinations.MOMENTS
	val navigationActions = remember(navController) {
		AppNavigationActions(navController)
	}

	val showSettingsDialog = remember { mutableStateOf(false) }
	if (showSettingsDialog.value) {
		SettingsDialog(
			onDismiss = { showSettingsDialog.value = false },
		)
	}

	ModalNavigationDrawer(drawerContent = {
		AppDrawer(
			route = currentRoute,
			navigateToMoments = { navigationActions.navigateToMoments() },
			navigateToPlayers = { navigationActions.navigateToPlayers() },
			closeDrawer = { coroutineScope.launch { drawerState.close() } },
			modifier = Modifier
		)
	}, drawerState = drawerState) {
		Scaffold(
			topBar = {
				TopAppBar(
					title = { Text(text = currentRoute) },
					modifier = Modifier.fillMaxWidth(),
					navigationIcon = {
						IconButton(
							onClick = {
								coroutineScope.launch { drawerState.open() }
							},
							content = {
								Icon(
									imageVector = Icons.Default.Menu, contentDescription = null
								)
							}
						)
					},
					actions = {
						IconButton(onClick = { showSettingsDialog.value = true }) {
							Icon(
								imageVector = Icons.Rounded.Settings,
								contentDescription = "Settings",
							)
						}
					},
					colors = TopAppBarDefaults.smallTopAppBarColors(
						containerColor = MaterialTheme.colorScheme.primaryContainer
					)
				)
			},
			modifier = Modifier
		) {
			NavHost(
				navController = navController,
				startDestination = AppDestinations.MOMENTS,
				modifier = modifier.padding(it)) {
				composable(AppDestinations.MOMENTS) {
					MomentsScreen()
				}
				composable(AppDestinations.PLAYERS) {
					PlayersScreen()
				}
			}
		}
	}
}