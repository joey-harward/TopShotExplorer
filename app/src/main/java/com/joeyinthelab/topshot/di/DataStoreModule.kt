package com.joeyinthelab.topshot.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.joeyinthelab.topshot.UserPreferences
import com.joeyinthelab.topshot.common.Dispatcher
import com.joeyinthelab.topshot.common.TopShotDispatchers.IO
import com.joeyinthelab.topshot.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
	@Provides
	@Singleton
	fun providesUserPreferencesDataStore(
		@ApplicationContext context: Context,
		@Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
		userPreferencesSerializer: UserPreferencesSerializer,
	): DataStore<UserPreferences> =
		DataStoreFactory.create(
			serializer = userPreferencesSerializer,
			scope = CoroutineScope(ioDispatcher + SupervisorJob()),
		) {
			context.dataStoreFile("user_preferences.pb")
		}
}