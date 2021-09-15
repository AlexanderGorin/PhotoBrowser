package com.alexandergorin.photobrowser.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class AppModule {

    @Provides
    @Named("IOScheduler")
    fun providesIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named("UIScheduler")
    fun providesUIScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
