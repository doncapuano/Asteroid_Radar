package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {

    //    Update and Insert w OnConflictStrategy = upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroidEntity: AsteroidEntity)

    @Query("SELECT * FROM saved_asteroids_table ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM saved_asteroids_table WHERE close_approach_date = :key")
    fun getTodayAsteroids(key: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM saved_asteroids_table WHERE close_approach_date >= :key ORDER BY close_approach_date ASC")
    fun getWeeklyAsteroids(key: String): LiveData<List<AsteroidEntity>>

//    To implement save feature in future
//    @Query("SELECT * FROM saved_asteroids_table WHERE asteroidId = :key")
//    fun getSavedAsteroids(key: Long): LiveData<List<AsteroidEntity?>>

//    To implement delete/clear feature in future
//    @Query("DELETE FROM saved_asteroids_table WHERE close_approach_date < :key")
//    fun clearPreviousAsteroids(key: String)
}