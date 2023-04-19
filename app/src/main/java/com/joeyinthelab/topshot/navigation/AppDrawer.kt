package com.joeyinthelab.topshot.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joeyinthelab.topshot.R

@Composable
fun AppDrawer(
	route: String,
	modifier: Modifier = Modifier,
	navigateToMoments: () -> Unit = {},
	navigateToPlayers: () -> Unit = {},
	closeDrawer: () -> Unit = {}
) {
	ModalDrawerSheet(modifier = Modifier) {
		DrawerHeader(modifier)
		Spacer(modifier = Modifier.padding(5.dp))
		NavigationDrawerItem(
			label = {
				Text(
					text = "Your Moments",
					style = MaterialTheme.typography.labelLarge
				)
			},
			selected = route == AppDestinations.MOMENTS,
			onClick = {
				navigateToMoments()
				closeDrawer()
			},
			icon = { Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null) },
			shape = MaterialTheme.shapes.small
		)

		NavigationDrawerItem(
			label = {
				Text(
					text = "Players",
					style = MaterialTheme.typography.labelLarge
				)
			},
			selected = route == AppDestinations.PLAYERS,
			onClick = {
				navigateToPlayers()
				closeDrawer()
			},
			icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
			shape = MaterialTheme.shapes.small
		)
	}
}


@Composable
fun DrawerHeader(modifier: Modifier) {
	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.Start,
		modifier = modifier
			.background(MaterialTheme.colorScheme.secondary)
			.padding(15.dp)
			.fillMaxWidth()
	) {
		Image(
			painterResource(id = R.drawable.top_shot),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = modifier
				.size(70.dp)
				.clip(CircleShape)
		)
		Spacer(modifier = Modifier.padding(5.dp))
		Text(
			text = stringResource(id = R.string.app_name),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onPrimary,
		)
	}
}

@Preview
@Composable
fun DrawerHeaderPreview() {
	AppDrawer(modifier = Modifier, route = AppDestinations.MOMENTS)
}