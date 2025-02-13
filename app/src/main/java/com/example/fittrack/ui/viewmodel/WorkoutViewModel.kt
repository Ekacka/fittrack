package com.example.fittrack.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fittrack.data.Workout
import com.example.fittrack.repository.WorkoutRepository
import com.google.firebase.auth.FirebaseAuth

class WorkoutViewModel : ViewModel() {
    private val repository = WorkoutRepository()
    private val workouts = MutableLiveData<List<Workout>>()
    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid

    init {
        getWorkouts()
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

        val currentList = workouts.value?.toMutableList() ?: mutableListOf()
        val updatedList = currentList.filterNot { it.id == workoutId }
        workouts.value = updatedList
    }






}
