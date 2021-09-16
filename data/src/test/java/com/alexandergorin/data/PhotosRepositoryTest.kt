package com.alexandergorin.data

import com.alexandergorin.data.api.FlickrApi
import com.alexandergorin.data.mappers.PhotosResponseMapper
import com.alexandergorin.data.models.PhotosResponse
import com.alexandergorin.data.repo.PhotosRepository
import com.alexandergorin.domain.PhotoDomainModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class PhotosRepositoryTest {

    private val throwable = Throwable()
    private val photosResponse = mockk<PhotosResponse>()
    private val listOfPhotos = listOf(PhotoDomainModel(""))
    private val flickrApi = mockk<FlickrApi> {
        every { getRecentPhotos(any(), any(), any(), any(), any()) } returns Single.just(photosResponse)
    }
    private val photosResponseMapper = mockk<PhotosResponseMapper> {
        every { this@mockk.invoke(photosResponse) } returns listOfPhotos
    }

    private val repository = PhotosRepository(flickrApi, photosResponseMapper)

    @Test
    fun `given api emits success value when getRecentPhotos invoked then invokes mapper and emits correct value`() {
        repository.getRecentPhotos()
            .test()
            .assertValue(listOfPhotos)

        verify { photosResponseMapper.invoke(photosResponse) }
    }

    @Test
    fun `given api emits error when getRecentPhotos invoked then does not invoke mapper and emits error`() {
        every { flickrApi.getRecentPhotos(any(), any(), any(), any(), any()) } returns Single.error(throwable)

        repository.getRecentPhotos()
            .test()
            .assertError(throwable)

        verify(exactly = 0) { photosResponseMapper.invoke(any()) }
    }
}