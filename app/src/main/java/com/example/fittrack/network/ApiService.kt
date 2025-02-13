package com.example.fittrack.network

import com.example.fittrack.data.Workout
import retrofit2.http.GET

interface QuoteApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuoteResponse>
}
