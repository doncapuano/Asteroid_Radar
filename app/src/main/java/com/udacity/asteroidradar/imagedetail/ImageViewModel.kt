package com.udacity.asteroidradar.imagedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.domain.PictureOfDay

class ImageViewModel(image: PictureOfDay, application: Application) :
    AndroidViewModel(application) {

    private val _selectedImageOfDay = MutableLiveData<PictureOfDay>()
    val selectedImageOfDay: LiveData<PictureOfDay>
        get() = _selectedImageOfDay

    init {
        _selectedImageOfDay.value = image
    }
}
