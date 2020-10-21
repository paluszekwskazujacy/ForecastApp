package com.example.myapplication.data.network.response

import com.example.myapplication.data.db.entity.CurrentWeatherEntry
import com.example.myapplication.data.db.entity.Location
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(

        @SerializedName("current")
        val currentWeatherEntry: CurrentWeatherEntry,
        val location: Location
)