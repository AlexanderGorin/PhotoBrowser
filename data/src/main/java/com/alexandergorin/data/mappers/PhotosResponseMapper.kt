package com.alexandergorin.data.mappers

import com.alexandergorin.data.models.Photo
import com.alexandergorin.data.models.PhotosResponse
import com.alexandergorin.domain.PhotoDomainModel
import javax.inject.Inject

class PhotosResponseMapper @Inject constructor() : (PhotosResponse) -> List<PhotoDomainModel> {

    override fun invoke(photosResponse: PhotosResponse): List<PhotoDomainModel> {
        return photosResponse.photos.photos.map { PhotoDomainModel(it.getUrl()) }.filter { it.url != "" }
    }

    private fun Photo.getUrl(): String {
        return urlO ?: urlL ?: urlC ?: urlZ ?: urlN ?: urlM ?: urlQ ?: urlS ?: urlT ?: urlSq ?: ""
    }
}