package com.joeyinthelab.topshot

import com.nftco.flow.sdk.Flow
import com.nftco.flow.sdk.FlowAddress
import com.nftco.flow.sdk.FlowScript
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	private val port = 9000

	private val testnetAPI = "access.devnet.nodes.onflow.org"
	private val testnetAccount = "0x0f09ae6125797e4a"

	private val mainnetAPI = "access.mainnet.nodes.onflow.org"
	private val mainnetAccount = "0xcbbe7e57a0bb249f"

	private val topShotTestnet = "0x877931736ee77cff"
	private val topShotMainnet = "0x0b2a3299cc857e29"

	@Test
	fun testTestnet() {
		val accessAPI = Flow.newAccessApi(testnetAPI, port)
		val flowChainId = accessAPI.getNetworkParameters()
		assertEquals(flowChainId.name, "TESTNET")
	}

	@Test
	fun testMainnet() {
		val accessAPI = Flow.newAccessApi(mainnetAPI, port)
		val flowChainId = accessAPI.getNetworkParameters()
		assertEquals(flowChainId.name, "MAINNET")
	}

	@Test
	fun testTestnetAccount() {
		val accessAPI = Flow.newAccessApi(testnetAPI, port)
		val flowAddress = FlowAddress(testnetAccount)
		val account = accessAPI.getAccountAtLatestBlock(flowAddress)
		assertEquals(account?.address, flowAddress)
	}

	@Test
	fun testMainnetAccount() {
		val accessAPI = Flow.newAccessApi(mainnetAPI, port)
		val flowAddress = FlowAddress(mainnetAccount)
		val account = accessAPI.getAccountAtLatestBlock(flowAddress)
		assertEquals(account?.address, flowAddress)
	}

	@Test
	fun testTestnetScript() {
		val script = """
			import TopShot from $topShotTestnet
			pub fun main(): [UInt64] {
				let acct = getAccount($testnetAccount)
				let collectionRef = acct.getCapability(/public/MomentCollection)
										.borrow<&{TopShot.MomentCollectionPublic}>()!
				return collectionRef.getIDs()
			}
		"""
		val accessAPI = Flow.newAccessApi(testnetAPI, port)
		val flowScript = FlowScript(script)
		val response = accessAPI.executeScriptAtLatestBlock(flowScript)
		val json = response.jsonCadence
		assert(json.value is Array<*>)
	}

	@Test
	fun testMainnetScript() {
		val script = """
			import TopShot from $topShotMainnet
			pub fun main(): [UInt64] {
				let acct = getAccount($mainnetAccount)
				let collectionRef = acct.getCapability(/public/MomentCollection)
										.borrow<&{TopShot.MomentCollectionPublic}>()!
				return collectionRef.getIDs()
			}
		"""
		val accessAPI = Flow.newAccessApi(mainnetAPI, port)
		val flowScript = FlowScript(script)
		val response = accessAPI.executeScriptAtLatestBlock(flowScript)
		val json = response.jsonCadence
		assert(json.value is Array<*>)
	}
}