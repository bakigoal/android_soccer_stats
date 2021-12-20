package com.bakigoal.soccerstats.network

import com.bakigoal.soccerstats.BuildConfig
import com.bakigoal.soccerstats.network.service.SoccerStatsService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Main entry point for network access. Call like `Network.soccerStatsService.getPlaylist()`
 */
object Network {

    private const val BASE_URL = "https://v3.football.api-sports.io/"

    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor {
            val original = it.request()

            val request = original.newBuilder()
                .header("x-rapidapi-key", BuildConfig.API_SPORTS_KEY)
                .header("x-rapidapi-host", BuildConfig.API_SPORTS_HOST)
                .build()

            it.proceed(request)
        }
        .build()

    /**
     * Configure retrofit to parse JSON and use coroutines
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient)
        .build()

    /**
     * Services
     */
    val soccerStatsService: SoccerStatsService = retrofit.create(SoccerStatsService::class.java)
}
