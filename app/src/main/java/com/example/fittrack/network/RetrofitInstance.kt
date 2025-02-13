package com.example.fittrack.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.fittrack.network.ApiService

object RetrofitInstance {
    private const val BASE_URL = "https://zenquotes.io/api/"

    val api: QuoteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApiService::class.java)
    }

}

interface ApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuoteResponse>
}

data class QuoteResponse(
    val q: String, // Quote text
    val a: String  // Author
)

suspend fun fetchQuote(): QuoteResponse? {
    return withContext(Dispatchers.IO) {
        try {
            RetrofitInstance.api.getRandomQuote().firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}
