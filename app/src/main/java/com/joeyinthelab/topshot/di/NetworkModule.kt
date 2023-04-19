package com.joeyinthelab.topshot.di

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.joeyinthelab.topshot.BuildConfig
import com.joeyinthelab.topshot.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
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
        @ApplicationContext application: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoader = ImageLoader.Builder(application)
        .okHttpClient(okHttpClient)
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
    fun provideApollo(
        @ApplicationContext application: Context,
        okHttpClient: OkHttpClient
    ) : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(application.getString(R.string.topshot_graphql))
            .okHttpClient(okHttpClient)
            .build()
    }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "joey.harward@gmail.com Android")
        return chain.proceed(request.build())
    }
}
