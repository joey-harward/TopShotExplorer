package com.joeyinthelab.topshot.repository

import android.content.Context
import com.joeyinthelab.topshot.R
import com.joeyinthelab.topshot.model.AppData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineAppDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AppDataRepository {
    // simulating app data in prefs
    private val isTestnet = false

    private val accountAddress =
        if (isTestnet)
            context.getString(R.string.testnet_account)
        else
            context.getString(R.string.mainnet_account)

    override val appData: Flow<AppData> = flow {
        // simulate delay getting app data from prefs
        //delay(2000)

        emit(AppData(
            isTestnet = isTestnet,
            accountAddress = accountAddress
        ))
    }

    override suspend fun setIsTestnet(isTestnet: Boolean) {
        // TODO: save isTestnet variable to prefs
    }

    override suspend fun setAccountAddress(address: String) {
        // TODO: save account address to prefs
    }
}
