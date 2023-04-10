package com.joeyinthelab.topshot.repository

import com.joeyinthelab.topshot.model.AppData
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    /**
     * Stream of [AppData]
     */
    val appData: Flow<AppData>

    /**
     * Sets whether using Testnet or Mainnet
     */
    suspend fun setIsTestnet(isTestnet: Boolean)

    /**
     * Sets the active user's account address
     */
    suspend fun setAccountAddress(address: String)
}
