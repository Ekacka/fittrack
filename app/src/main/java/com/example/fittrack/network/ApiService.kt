package com.example.fittrack.network

import com.example.fittrack.data.Workout
import retrofit2.http.GET

interface   ApiService {
    @GET("workouts")
    suspend fun getWorkouts(): List<Workout>
}
