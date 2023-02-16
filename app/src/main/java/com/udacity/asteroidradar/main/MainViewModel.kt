package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.PictureOfDayApi
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class AsteroidFilter { ALL, TODAY, WEEK }

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    private val _overflowFilter = MutableLiveData<AsteroidFilter>()

    //    _overflowFilter.switchMap
    val asteroidFeed = Transformations.switchMap(_overflowFilter) {
        when (it) {
            AsteroidFilter.ALL -> repository.asteroidFeed
            AsteroidFilter.TODAY -> repository.asteroidFeedToday
            AsteroidFilter.WEEK -> repository.asteroidFeedWeek
            else -> repository.asteroidFeed
        }
    }

    //  PictureOfDay LiveData Object
    private val _imageOfTheDay = MutableLiveData<PictureOfDay?>()
    val imageOfTheDay: LiveData<PictureOfDay?>
        get() = _imageOfTheDay

    //    Navigate to Asteroid Detail Screen LiveData Object
    private val _navigateToDetailScreen = MutableLiveData<Asteroid?>()
    val navigateToDetailScreen: LiveData<Asteroid?>
        get() = _navigateToDetailScreen

    //    Navigate to Image Detail Screen LiveData Object
    private val _navigateToImageScreen = MutableLiveData<PictureOfDay?>()
    val navigateToImageScreen: LiveData<PictureOfDay?>
        get() = _navigateToImageScreen


    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
        getAsteroidImageOfTheDay()
        getUpdateFilter(AsteroidFilter.TODAY)
    }

    
    private fun getUpdateFilter(filter: AsteroidFilter) {
        _overflowFilter.value = filter
    }

    fun updateFilter(filter: AsteroidFilter) {
        getUpdateFilter(filter)
    }

    //    PictureOfTheDay Coroutine API Function calls and loads PictureOfTheDay
    private fun getAsteroidImageOfTheDay() {
        viewModelScope.launch {
            try {
                val imageResult =
                    PictureOfDayApi.retrofitService.getImageOfTheDay(Constants.API_KEY)
                _imageOfTheDay.value = imageResult
            } catch (e: Exception) {
//                _status.value = AsteroidListApiStatus.ERROR
                _imageOfTheDay.value = null
            }
        }
    }

    //    When Asteroid is clicked, sets LiveData to Asteroid
    fun displayAsteroidDetailScreen(asteroid: Asteroid) {
        _navigateToDetailScreen.value = asteroid
    }

    //    When navigation is complete, sets LiveData to null
    fun displayAsteroidDetailScreenComplete() {
        _navigateToDetailScreen.value = null
    }

    fun displayImageDetailScreen(image: PictureOfDay) {
        _navigateToImageScreen.value = image
    }

    fun displayImageDetailScreenComplete() {
        _navigateToImageScreen.value = null
    }
}


