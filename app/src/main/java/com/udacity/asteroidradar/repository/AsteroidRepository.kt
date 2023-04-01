package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.NearEarthObjects
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException
import java.time.LocalDate

/**
 * Repository for fetching asteroid information from the network and storing it
 */
class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroidFeed: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDatabaseDao.getAllAsteroids()
    ) {
        it.asDomainModel()
    }

    val asteroidFeedToday: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDatabaseDao.getTodayAsteroids(LocalDate.now().toString())
    ) {
        it.asDomainModel()
    }

    val asteroidFeedWeek: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDatabaseDao.getWeeklyAsteroids(LocalDate.now().toString())
    ) {
        it.asDomainModel()
    }


    /**
     * Refresh the asteroids stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val feedResult = AsteroidApi.retrofitService.getFeed(
                    LocalDate.now().toString(),
                    Constants.API_KEY
                )
                    .await()
                val parsedFeedResult = parseAsteroidsJsonResult(JSONObject(feedResult))
                database.asteroidDatabaseDao.insert(*NearEarthObjects(parsedFeedResult).asDatabaseModel())
            } catch (ex: UnknownHostException) {
                Log.e("AsteroidRepository", "no network error")
            }
        }
    }

    suspend fun refreshWeeklyAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val today = LocalDate.now()
                val tomorrow = today.plusDays(1)
                val feedResult = AsteroidApi.retrofitService.getFeed(
                    tomorrow.toString(),
                    Constants.API_KEY
                )
                    .await()
                val parsedFeedResult = parseAsteroidsJsonResult(JSONObject(feedResult))
                database.asteroidDatabaseDao.insert(*NearEarthObjects(parsedFeedResult).asDatabaseModel())
            } catch (ex: UnknownHostException) {
                Log.e("AsteroidRepository", "no network error")
            }
        }
    }
}