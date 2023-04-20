package com.joeyinthelab.topshot.model

/**
 * Class summarizing relevant moment response data
 */
data class Moment(
	val flowId: String,
	val tier: String,
	val setFlowName: String,
	val flowSerialNumber: String,
	val assetPathPrefix: String,
	val playHeadline: String,
	val playShortDescription: String,
)