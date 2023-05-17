package com.silverorange.videoplayer.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockNetworkHelperModule {
    @Provides
    @Singleton
    fun providesMockNetworkHelper(@ApplicationContext context: Context): MockNetworkHelper = MockNetworkHelper(context)
}
