package com.joeyinthelab.topshot.repository

import android.content.Context
import com.joeyinthelab.topshot.R
import com.joeyinthelab.topshot.model.AppData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class OfflineAppDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AppDataRepository {
    private val _appData = MutableStateFlow(
        AppData(
            isTestnet = false,
            accountAddress = context.getString(R.string.mainnet_account_graphql)
        )
    )

    override val appData: StateFlow<AppData> = _appData

    override suspend fun setIsTestnet(isTestnet: Boolean) {
        _appData.value = AppData(
            isTestnet, _appData.value.accountAddress
        )
    }

    override suspend fun setAccountAddress(address: String) {
        _appData.value = AppData(
            _appData.value.isTestnet, address
        )
    }
}
