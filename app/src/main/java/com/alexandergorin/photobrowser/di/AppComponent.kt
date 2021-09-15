package com.alexandergorin.photobrowser.di

import android.content.Context
import com.alexandergorin.photobrowser.photos.PhotosFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppServiceModule::class, AppModule::class, AppBindsModule::class])
interface AppComponent {

    fun inject(photosFragment: PhotosFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
