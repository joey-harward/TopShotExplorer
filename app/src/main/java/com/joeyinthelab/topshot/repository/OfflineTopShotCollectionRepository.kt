package com.joeyinthelab.topshot.repository

import android.content.Context
import com.joeyinthelab.topshot.R
import com.nftco.flow.sdk.FlowScript
import com.nftco.flow.sdk.cadence.Field
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineTopShotCollectionRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : TopShotCollectionRepository {
    private val isTestnet = false

    private val accessApi =
        if (isTestnet)
            context.getString(R.string.testnet_api)
        else
            context.getString(R.string.mainnet_api)

    private val topShotAddress =
        if (isTestnet)
            context.getString(R.string.topshot_testnet)
        else
            context.getString(R.string.topshot_mainnet)

    private val account =
        if (isTestnet)
            context.getString(R.string.testnet_account)
        else
            context.getString(R.string.mainnet_account)

    private val accessAPI = com.nftco.flow.sdk.Flow.newAccessApi(accessApi, 9000)

    override val collection: Flow<List<String>> = flow {
        val script = """
			import TopShot from $topShotAddress
			pub fun main(): [UInt64] {
				let acct = getAccount($account)
				let collectionRef = acct.getCapability(/public/MomentCollection)
										.borrow<&{TopShot.MomentCollectionPublic}>()!
				return collectionRef.getIDs()
			}
		"""

        val flowScript = FlowScript(script)
        val response = accessAPI.executeScriptAtLatestBlock(flowScript)
        val json = response.jsonCadence

        // need better type checking here
        val collectionFields = json.value as Array<*>
        val collectionIds = collectionFields.map {
            (it as Field<*>).value as String
        }

        emit(
            collectionIds
        )
    }
}
