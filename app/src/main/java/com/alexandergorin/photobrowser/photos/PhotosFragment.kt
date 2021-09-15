package com.alexandergorin.photobrowser.photos

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alexandergorin.photobrowser.Application
import com.alexandergorin.photobrowser.databinding.PhotosFragmentBinding
import com.alexandergorin.photobrowser.utils.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class PhotosFragment : ViewBindingFragment<PhotosFragmentBinding>(PhotosFragmentBinding::inflate) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PhotosViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.offscreenPageLimit = 3

        with(viewModel) {
            photosViewState.observe(viewLifecycleOwner) { viewState ->
                when (viewState) {
                    is PhotosViewState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.viewPager.isVisible = false
                    }
                    is PhotosViewState.Loaded -> {
                        binding.progressBar.isVisible = false
                        binding.viewPager.isVisible = true
                        binding.viewPager.adapter = PhotosRecyclerViewAdapter(viewState.photos)
                    }
                    PhotosViewState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.viewPager.isVisible = false
                    }
                }
            }
            errorEvent.observe(viewLifecycleOwner) { errorMessage ->
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        fun newInstance() = PhotosFragment()
    }
}