package com.joeyinthelab.topshot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.joeyinthelab.topshot.MainActivityUiState.Loading
import com.joeyinthelab.topshot.MainActivityUiState.Success
import com.joeyinthelab.topshot.ui.TopShotApp
import com.joeyinthelab.topshot.ui.theme.TopShotSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	private val viewModel: MainActivityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		val splashScreen = installSplashScreen()
		super.onCreate(savedInstanceState)

		var uiState: MainActivityUiState by mutableStateOf(Loading)

		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState
					.onEach {
						uiState = it
					}
					.collect()
			}
		}

		splashScreen.setKeepOnScreenCondition {
			when (uiState) {
				Loading -> true
				is Success -> false
			}
		}

		WindowCompat.setDecorFitsSystemWindows(window, false)

		setContent {
			val systemUiController = rememberSystemUiController()
			val useDarkIcons = !isSystemInDarkTheme()

			DisposableEffect(systemUiController, useDarkIcons) {
				systemUiController.setSystemBarsColor(
					color = Color.Transparent,
					darkIcons = useDarkIcons
				)
				onDispose {}
			}

			TopShotSampleTheme {
				Surface(
					modifier = Modifier.fillMaxSize()
				) {
					TopShotApp()
				}
			}
		}
	}
}
