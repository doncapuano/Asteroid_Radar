package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid

@Entity(tableName = "saved_asteroids_table")
data class AsteroidEntity(
    @PrimaryKey(autoGenerate = true)
    var asteroidId: Long = 0L,
    @ColumnInfo(name = "codename")
    var codename: String,
    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter")
    var estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity: Double,
    @ColumnInfo(name = "distance")
    var distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous")
    var isPotentiallyHazardous: Boolean,
)

//    Map database entities to domain entities
fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.asteroidId,
            codename = it.codename,
            formattedDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}