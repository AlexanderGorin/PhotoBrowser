package com.alexandergorin.photobrowser.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandergorin.domain.PhotoDomainModel
import com.alexandergorin.domain.Repository
import com.alexandergorin.photobrowser.utils.SingleLiveEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class PhotosViewModel @Inject constructor(
    private val repository: Repository,
    @Named("IOScheduler") private val ioScheduler: Scheduler,
    @Named("UIScheduler") private val uiScheduler: Scheduler,
) : ViewModel() {

    private val bag = CompositeDisposable()

    val errorEvent: LiveData<String>
        get() = mutableErrorEvent
    private val mutableErrorEvent = SingleLiveEvent<String>()

    val photosViewState: LiveData<PhotosViewState>
        get() = mutablePhotosViewState
    private val mutablePhotosViewState = MutableLiveData<PhotosViewState>()

    private var photos: List<PhotoDomainModel> = emptyList()

    fun loadData() {
        // need to load photos only if photos were not loaded previously or empty list was loaded
        if (photos.isEmpty()) {
            loadPhotos()
        }
    }

    private fun loadPhotos() {
        repository.getRecentPhotos()
            .toObservable()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .map<PhotosViewState> { PhotosViewState.Loaded(it) }
            .onErrorReturn { PhotosViewState.Error(it) }
            .startWith(PhotosViewState.Loading)
            .subscribeBy { viewState ->
                mutablePhotosViewState.value = viewState
                when (viewState) {
                    is PhotosViewState.Error -> mutableErrorEvent.value = viewState.throwable.localizedMessage
                    is PhotosViewState.Loaded -> photos = viewState.photos
                    else -> Unit
                }
            }
            .addTo(bag)
    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}
