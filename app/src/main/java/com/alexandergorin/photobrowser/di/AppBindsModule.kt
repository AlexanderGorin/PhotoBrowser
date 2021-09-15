package com.alexandergorin.photobrowser.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandergorin.data.repo.PhotosRepository
import com.alexandergorin.domain.Repository
import com.alexandergorin.photobrowser.photos.PhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class AppBindsModule {

    @Singleton
    @Binds
    abstract fun bindPhotosRepository(repository: PhotosRepository): Repository

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindViewModel(viewModel: PhotosViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelFactory): ViewModelProvider.Factory
}
