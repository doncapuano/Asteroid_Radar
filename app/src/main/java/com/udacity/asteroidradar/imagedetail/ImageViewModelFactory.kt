package com.udacity.asteroidradar.imagedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.domain.PictureOfDay

class ImageViewModelFactory(
    private val image: PictureOfDay,
    private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(image, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


