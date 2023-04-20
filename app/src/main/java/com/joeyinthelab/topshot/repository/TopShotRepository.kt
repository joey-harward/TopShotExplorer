package com.joeyinthelab.topshot.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.joeyinthelab.topshot.UserProfileQuery
import com.joeyinthelab.topshot.UserMomentsQuery
import com.joeyinthelab.topshot.model.Moment
import javax.inject.Inject

class TopShotRepository @Inject constructor(
	private val apolloClient: ApolloClient,
) {
	suspend fun getUserMoments(username: String): List<Moment> {
		val userProfileResponse: ApolloResponse<UserProfileQuery.Data> =
			apolloClient.query(UserProfileQuery(username)).execute()
		val flowAddress = userProfileResponse.data?.getUserProfileByUsername?.publicInfo?.flowAddress.orEmpty()

		val userMomentsResponse: ApolloResponse<UserMomentsQuery.Data> =
			apolloClient.query(UserMomentsQuery(flowAddress)).execute()
		val userMoments: List<Moment> =
			userMomentsResponse.data?.searchMintedMoments?.data?.searchSummary?.data?.data?.map { momentQueryData ->
				Moment(
					flowId = momentQueryData?.onMintedMoment?.flowId.orEmpty(),
					tier = momentQueryData?.onMintedMoment?.tier?.name.orEmpty(),
					setFlowName = momentQueryData?.onMintedMoment?.set?.flowName.orEmpty(),
					flowSerialNumber = momentQueryData?.onMintedMoment?.flowSerialNumber.orEmpty(),
					assetPathPrefix = momentQueryData?.onMintedMoment?.assetPathPrefix.orEmpty(),
					playHeadline = momentQueryData?.onMintedMoment?.play?.headline.orEmpty(),
					playShortDescription = momentQueryData?.onMintedMoment?.play?.shortDescription.orEmpty(),
				)
			} ?: emptyList()
		return userMoments
	}
}
