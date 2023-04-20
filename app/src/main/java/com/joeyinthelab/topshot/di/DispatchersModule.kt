package com.joeyinthelab.topshot.di

import com.joeyinthelab.topshot.common.Dispatcher
import com.joeyinthelab.topshot.common.TopShotDispatchers.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
	@Provides
	@Dispatcher(IO)
	fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
