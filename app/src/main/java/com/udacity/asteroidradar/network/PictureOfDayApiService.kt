package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.domain.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface PictureOfDayApiService {
    @GET(Constants.IMAGE_OF_THE_DAY)
    suspend fun getImageOfTheDay(@Query("api_key") apiKey: String):
            PictureOfDay
}

object PictureOfDayApi {
    val retrofitService: PictureOfDayApiService by lazy {
        retrofit.create(PictureOfDayApiService::class.java)
    }
}

