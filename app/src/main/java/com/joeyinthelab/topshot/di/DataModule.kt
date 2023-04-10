package com.joeyinthelab.topshot.di

import com.joeyinthelab.topshot.repository.OfflineAppDataRepository
import com.joeyinthelab.topshot.repository.AppDataRepository
import com.joeyinthelab.topshot.repository.OfflineTopShotCollectionRepository
import com.joeyinthelab.topshot.repository.TopShotCollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAppDataRepository(
        offlineAppDataRepository: OfflineAppDataRepository,
    ): AppDataRepository

    @Binds
    fun bindsTopShotCollectionRepository(
        offlineTopShotCollectionRepository: OfflineTopShotCollectionRepository,
    ): TopShotCollectionRepository
}
