package com.silverorange.videoplayer.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockNetworkOkHttp3Module {
    @Provides
    @Singleton
    fun providesOkHttp3(): OkHttpClient = OkHttpClient()
}
