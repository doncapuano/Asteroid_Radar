package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.domain.Asteroid

@JsonClass(generateAdapter = true)
data class NearEarthObjects(val networkAsteroids: ArrayList<Asteroid>)

fun NearEarthObjects.asDatabaseModel(): Array<AsteroidEntity> {
    return networkAsteroids.map {
        AsteroidEntity(
            asteroidId = it.id,
            codename = it.codename,
            closeApproachDate = it.formattedDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
        )
    }.toTypedArray()
}

