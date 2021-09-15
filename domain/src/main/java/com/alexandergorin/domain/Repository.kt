package com.alexandergorin.domain

import io.reactivex.Single

interface Repository {
    fun getRecentPhotos(): Single<List<PhotoDomainModel>>
}