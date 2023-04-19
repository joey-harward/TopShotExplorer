package com.joeyinthelab.topshot.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.joeyinthelab.topshot.UserMomentsQuery
import com.joeyinthelab.topshot.MintedMomentsQuery
import com.joeyinthelab.topshot.UserProfileQuery
import com.joeyinthelab.topshot.MomentNFTsQuery
import com.joeyinthelab.topshot.model.MomentNFT
import javax.inject.Inject

class TopShotRepository @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend fun getUserMoments(username: String): List<String> {
        val userProfileResponse: ApolloResponse<UserProfileQuery.Data> =
            apolloClient.query(UserProfileQuery(username)).execute()
        val flowAddress = userProfileResponse.data?.getUserProfileByUsername?.publicInfo?.flowAddress.orEmpty()

        val userMoments: ApolloResponse<UserMomentsQuery.Data> =
            apolloClient.query(UserMomentsQuery(flowAddress)).execute()
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

    suspend fun getUserMoments2(username: String): List<MomentNFT> {
        val userProfileResponse: ApolloResponse<UserProfileQuery.Data> =
            apolloClient.query(UserProfileQuery(username)).execute()
        val flowAddress = userProfileResponse.data?.getUserProfileByUsername?.publicInfo?.flowAddress.orEmpty()

        val userMoments: ApolloResponse<MomentNFTsQuery.Data> =
            apolloClient.query(MomentNFTsQuery(flowAddress)).execute()
        val userMomentData: List<MomentNFT> = userMoments.data?.searchMintedMoments?.data?.searchSummary?.data?.data?.map { momentQueryData ->
            MomentNFT(
                flowId = momentQueryData?.onMintedMoment?.flowId.orEmpty(),
                tier = momentQueryData?.onMintedMoment?.tier?.name.orEmpty(),
                setFlowName = momentQueryData?.onMintedMoment?.set?.flowName.orEmpty(),
                flowSerialNumber = momentQueryData?.onMintedMoment?.flowSerialNumber.orEmpty(),
                assetPathPrefix = momentQueryData?.onMintedMoment?.assetPathPrefix.orEmpty(),
            )
        } ?: emptyList()
        return userMomentData
    }
}
