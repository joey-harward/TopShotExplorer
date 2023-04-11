package com.joeyinthelab.topshot.di

import com.joeyinthelab.topshot.repository.OfflineAppDataRepository
import com.joeyinthelab.topshot.repository.AppDataRepository
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
}
