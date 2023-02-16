package com.udacity.asteroidradar

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.AsteroidEntity
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest {

    private lateinit var asteroidDao: AsteroidDatabaseDao
    private lateinit var db: AsteroidDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        asteroidDao = db.asteroidDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    @Test
//    fun insertAndGetAsteroid() = runBlocking {
//        val asteroidEntity = AsteroidEntity()
//        asteroidDao.insert(asteroidEntity)
//        val tonight = asteroidDao.getAllAsteroids()
//        assertEquals(tonight?.value, asteroidEntity)
//    }
}