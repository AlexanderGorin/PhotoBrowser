package com.alexandergorin.photobrowser.di

import com.alexandergorin.data.api.FlickrApi
import com.alexandergorin.data.createService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppServiceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        Gson()
            .newBuilder()
            .create()

    @Provides
    @Singleton
    fun provideFlickrApi(gson: Gson): FlickrApi = createService(gson)

}
