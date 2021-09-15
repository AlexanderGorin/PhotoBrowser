package com.alexandergorin.data.repo

import com.alexandergorin.data.api.FlickrApi
import com.alexandergorin.data.mappers.PhotosResponseMapper
import com.alexandergorin.domain.PhotoDomainModel
import com.alexandergorin.domain.Repository
import io.reactivex.Single
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val flickrApi: FlickrApi,
    private val photosResponseMapper: PhotosResponseMapper
) : Repository {

    override fun getRecentPhotos(): Single<List<PhotoDomainModel>> {
        return flickrApi.getRecentPhotos()
            .map { photosResponseMapper.invoke(it) }
    }
}