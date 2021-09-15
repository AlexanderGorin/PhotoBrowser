package com.alexandergorin.photobrowser.photos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexandergorin.photobrowser.R

class PhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, PhotosFragment.newInstance())
                .commit()
        }
    }
}