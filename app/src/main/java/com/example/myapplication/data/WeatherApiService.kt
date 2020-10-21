package com.example.myapplication.data

import com.example.myapplication.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "b827aa99d6e14e08848135524202110"
const val BASE_URL = "https://api.weatherapi.com/v1/"

interface WeatherApiService {

    @GET("current.json")
    fun getCurretWeather(
            @Query("q") location: String,
            @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>


    companion object{
        operator fun invoke(): WeatherApiService{
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().
                    addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherApiService::class.java)
        }
    }
}