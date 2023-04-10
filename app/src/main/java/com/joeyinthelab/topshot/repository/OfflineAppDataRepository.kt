package com.joeyinthelab.topshot.repository

import com.joeyinthelab.topshot.model.AppData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineAppDataRepository @Inject constructor() : AppDataRepository {
    override val appData: Flow<AppData> = flow {
        // simulate getting app data from prefs
        //delay(2000)

        emit(AppData(
            isTestnet = false,
            accountAddress = "0xcbbe7e57a0bb249f"
        ))
    }

    override suspend fun setIsTestnet(isTestnet: Boolean) {
        //setIsTestnet(isTestnet)
    }

    override suspend fun setAccountAddress(address: String) {
        //setAccountAddress(address)
    }
}
