package com.joeyinthelab.topshot.repository

import android.content.Context
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.joeyinthelab.topshot.R
import com.joeyinthelab.topshot.UserMomentsQuery
import com.joeyinthelab.topshot.MintedMomentsQuery
import com.nftco.flow.sdk.FlowAccessApi
import com.nftco.flow.sdk.FlowScript
import com.nftco.flow.sdk.cadence.Field
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class TopShotCollectionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apolloClient: ApolloClient,
    @Named("Testnet") private val flowTestnetApi: FlowAccessApi,
    @Named("Mainnet") private val flowMainnetApi: FlowAccessApi
) {
    suspend fun getUserMoments(account: String): List<String> {
//        val userProfileResponse: ApolloResponse<UserProfileQuery.Data> =
//            apolloClient.query(UserProfileQuery("joeystyles")).execute()
//        val flowAddress = userProfileResponse.data?.getUserProfileByUsername?.publicInfo?.flowAddress

        val userMoments: ApolloResponse<UserMomentsQuery.Data> =
            apolloClient.query(UserMomentsQuery(account)).execute()
        val userMomentIds: List<String> = userMoments.data?.searchMintedMoments?.data?.searchSummary?.data?.data?.map { momentQueryData ->
            val parts = momentQueryData?.sortID.toString().split("_")
            parts[parts.size-1]
        } ?: emptyList()

        val mintedMoments: ApolloResponse<MintedMomentsQuery.Data> =
            apolloClient.query(MintedMomentsQuery(userMomentIds)).execute()
        val list: List<String> = mintedMoments.data?.getMintedMoments?.data?.map { mintedMomentData ->
            mintedMomentData?.flowId.toString()
        } ?: emptyList()

        return list
    }

    fun getCollection(isTestnet: Boolean, account: String): List<String> {
        val accessAPI = if (isTestnet) flowTestnetApi else flowMainnetApi
        Log.d("FLOW_API", accessAPI.getNetworkParameters().name)

        val topShotAddress =
            if (isTestnet)
                context.getString(R.string.topshot_testnet)
            else
                context.getString(R.string.topshot_mainnet)

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

        return collectionIds
    }
}
