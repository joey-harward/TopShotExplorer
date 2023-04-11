package com.joeyinthelab.topshot.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import com.joeyinthelab.topshot.BuildConfig
import com.joeyinthelab.topshot.R
import com.nftco.flow.sdk.FlowAccessApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun imageLoader(
        okHttpCallFactory: Call.Factory,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .callFactory(okHttpCallFactory)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .respectCacheHeaders(false)
        .apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
        }
        .build()

    @Provides
    @Singleton
    @Named("Testnet")
    fun flowTestnetApi(
        @ApplicationContext application: Context,
    ): FlowAccessApi = com.nftco.flow.sdk.Flow.newAccessApi(
        application.getString(R.string.testnet_api), 9000
    )

    @Provides
    @Singleton
    @Named("Mainnet")
    fun flowMainnetApi(
        @ApplicationContext application: Context,
    ): FlowAccessApi = com.nftco.flow.sdk.Flow.newAccessApi(
        application.getString(R.string.mainnet_api), 9000
    )
}
