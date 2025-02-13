package com.example.fittrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Delete
    suspend fun deleteWorkout(workout: Workout)
}
