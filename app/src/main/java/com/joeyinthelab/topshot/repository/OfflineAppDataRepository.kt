package com.joeyinthelab.topshot.repository

import com.joeyinthelab.topshot.datastore.TopShotPreferencesDataStore
import com.joeyinthelab.topshot.model.AppData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineAppDataRepository @Inject constructor(
    private val preferencesDataSource: TopShotPreferencesDataStore,
) : AppDataRepository {
    override val appData: Flow<AppData> = preferencesDataSource.appData

    override suspend fun setUsername(username: String) {
        preferencesDataSource.setUsername(username)
    }
}
