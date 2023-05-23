package com.joeyinthelab.topshot.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.joeyinthelab.topshot.UserProfileQuery
import com.joeyinthelab.topshot.UserMomentsQuery
import com.joeyinthelab.topshot.SearchPlayersQuery
import com.joeyinthelab.topshot.PlayerMomentsQuery
import com.joeyinthelab.topshot.model.Moment
import com.joeyinthelab.topshot.model.Player
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

	suspend fun searchPlayers(name: String): List<Player> {
		val searchPlayersResponse: ApolloResponse<SearchPlayersQuery.Data> =
			apolloClient.query(SearchPlayersQuery(name)).execute()

		val players: List<Player> =
			searchPlayersResponse.data?.getSearchSuggestions?.playerSuggestions?.map { playerData ->
				Player(
					id = playerData?.id.orEmpty(),
					name = playerData?.name.orEmpty(),
				)
			} ?: emptyList()

		return players
	}

	suspend fun getPlayerMoments(players: List<String>): List<Moment> {
		val playerMomentsResponse: ApolloResponse<PlayerMomentsQuery.Data> =
			apolloClient.query(PlayerMomentsQuery(players)).execute()
		val playerMoments: List<Moment> =
			playerMomentsResponse.data?.searchMintedMoments?.data?.searchSummary?.data?.data?.map { momentQueryData ->
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
		return playerMoments
	}
}
