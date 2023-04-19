package com.joeyinthelab.topshot.navigation

import androidx.navigation.NavHostController
import com.joeyinthelab.topshot.navigation.AppDestinations.MOMENTS
import com.joeyinthelab.topshot.navigation.AppDestinations.PLAYERS

object AppDestinations {
	const val MOMENTS = "Your Moments"
	const val PLAYERS = "Players"
}

class AppNavigationActions(private val navController: NavHostController) {
	fun navigateToMoments() {
		navController.navigate(MOMENTS) {
			popUpTo(MOMENTS)
		}
	}

	fun navigateToPlayers() {
		navController.navigate(PLAYERS) {
			popUpTo(PLAYERS)
		}
//		navController.navigate(PLAYERS) {
//			launchSingleTop = true
//			restoreState = true
//		}
	}
}