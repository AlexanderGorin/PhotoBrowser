package com.alexandergorin.photobrowser.photos

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandergorin.data.repo.PhotosRepository
import com.alexandergorin.photobrowser.utils.SingleLiveEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository,
    @Named("IOScheduler") private val ioScheduler: Scheduler,
    @Named("UIScheduler") private val uiScheduler: Scheduler,
) : ViewModel(), DefaultLifecycleObserver {

    private val bag = CompositeDisposable()

    val errorEvent: LiveData<String>
        get() = mutableErrorEvent
    private val mutableErrorEvent = SingleLiveEvent<String>()

    val photosViewState: LiveData<PhotosViewState>
        get() = mutablePhotosViewState
    private val mutablePhotosViewState = MutableLiveData<PhotosViewState>()

    override fun onStart(owner: LifecycleOwner) {
        loadData()
    }

    private fun loadData() {
        bag += repository.getRecentPhotos()
            .toObservable()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .map<PhotosViewState> { PhotosViewState.Loaded(it) }
            .onErrorReturn { PhotosViewState.Error(it) }
            .startWith(PhotosViewState.Loading)
            .subscribeBy { viewState ->
                mutablePhotosViewState.value = viewState
                if (viewState is PhotosViewState.Error) {
                    mutableErrorEvent.value = viewState.throwable.localizedMessage
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}
