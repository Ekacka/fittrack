package com.example.fittrack.data

data class Workout(
    val id: String? = null,
    val title: String = "",        // Use 'title' instead of 'name'
    val description: String = "",
    val duration: Int = 0,
    val date: String = "",
    val imageUrl: String? = null
)
