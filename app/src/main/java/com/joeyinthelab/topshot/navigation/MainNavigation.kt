package com.joeyinthelab.topshot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joeyinthelab.topshot.ui.screen.Home

@Composable
fun MainNavigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Routes.Home.route) {
		composable(Routes.Home.route) {
			Home(navController = navController)
		}
	}
}