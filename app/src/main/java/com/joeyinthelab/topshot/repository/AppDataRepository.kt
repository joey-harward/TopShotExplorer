package com.joeyinthelab.topshot.repository

import com.joeyinthelab.topshot.model.AppData
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    /**
     * Stream of [AppData]
     */
    val appData: Flow<AppData>

    /**
     * Sets the active username
     */
    suspend fun setUsername(username: String)
}
