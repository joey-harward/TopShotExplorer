package com.joeyinthelab.topshot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import com.joeyinthelab.topshot.R
import com.joeyinthelab.topshot.UserPreferences
import com.joeyinthelab.topshot.model.AppData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopShotPreferencesDataStore @Inject constructor(
	@ApplicationContext private val context: Context,
	private val userPreferences: DataStore<UserPreferences>,
) {
	val appData = userPreferences.data
		.map {
			AppData(
				username = it.username ?: context.getString(R.string.default_username)
			)
		}

	suspend fun setUsername(username: String) {
		userPreferences.updateData {
			it.toBuilder().setUsername(username).build()
		}
	}
}
