package com.example.fittrack.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fittrack.data.Workout
import com.example.fittrack.repository.WorkoutRepository

class WorkoutViewModel : ViewModel() {
    private val repository = WorkoutRepository()
    private val workouts = MutableLiveData<List<Workout>>()

    init {
        getWorkouts()  // Fetch workouts when ViewModel initializes
    }

    fun getWorkouts(): LiveData<List<Workout>> {
        repository.getUserWorkouts(workouts)
        return workouts
    }

    fun addWorkout(workout: Workout) {
        repository.addWorkout(workout)
    }

    fun deleteWorkout(workoutId: String) {
        repository.deleteWorkout(workoutId)
    }
}
