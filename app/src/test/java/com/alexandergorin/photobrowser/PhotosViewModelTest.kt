package com.alexandergorin.photobrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexandergorin.domain.PhotoDomainModel
import com.alexandergorin.domain.Repository
import com.alexandergorin.photobrowser.photos.PhotosViewModel
import com.alexandergorin.photobrowser.photos.PhotosViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val listOfPhotos = listOf(PhotoDomainModel(""))
    private val throwable = Throwable()
    private val repository: Repository = mockk()
    private val observerState: Observer<PhotosViewState> = mockk(relaxed = true)
    private val observerErrorEvent: Observer<String> = mockk(relaxed = true)
    private lateinit var viewModel: PhotosViewModel

    @Before
    fun before() {
        viewModel = PhotosViewModel(
            repository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        viewModel.photosViewState.observeForever(observerState)
        viewModel.errorEvent.observeForever(observerErrorEvent)
    }


    @Test
    fun `given repository emits list of photos when loadData called then emits loading and loaded states`() {
        every { repository.getRecentPhotos() } returns Single.just(listOfPhotos)

        viewModel.loadData()

        verifyOrder {
            observerState.onChanged(PhotosViewState.Loading)
            observerState.onChanged(PhotosViewState.Loaded(listOfPhotos))
        }
    }

    @Test
    fun `given repository emits error when loadData called then emits loading and error states`() {
        every { repository.getRecentPhotos() } returns Single.error(throwable)

        viewModel.loadData()

        verifyOrder {
            observerState.onChanged(PhotosViewState.Loading)
            observerState.onChanged(PhotosViewState.Error(throwable))
        }
    }

    @Test
    fun `given repository emits error when loadData called then emits error event`() {
        every { repository.getRecentPhotos() } returns Single.error(throwable)

        viewModel.loadData()

        verify {
            observerErrorEvent.onChanged(throwable.localizedMessage)
        }
    }

    @Test
    fun `given list of photos loaded when loadData called twice then does not reload photos from repo`() {
        every { repository.getRecentPhotos() } returns Single.just(listOfPhotos)

        viewModel.loadData()
        viewModel.loadData()

        verify(exactly = 1) {
            repository.getRecentPhotos()
        }
    }

    @Test
    fun `given list of photos loaded empty when loadData called twice then reloads photos from repo`() {
        every { repository.getRecentPhotos() } returns Single.just(emptyList())

        viewModel.loadData()
        viewModel.loadData()

        verify(exactly = 2) {
            repository.getRecentPhotos()
        }
    }
}
