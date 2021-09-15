package com.alexandergorin.photobrowser.photos

import com.alexandergorin.domain.PhotoDomainModel

sealed class PhotosViewState {
    object Loading : PhotosViewState()
    data class Loaded(val photos: List<PhotoDomainModel>) : PhotosViewState()
    data class Error(val throwable: Throwable) : PhotosViewState()
}
