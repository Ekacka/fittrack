package com.example.fittrack.repository

import androidx.lifecycle.MutableLiveData
import com.example.fittrack.data.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WorkoutRepository {
    private val database = FirebaseDatabase.getInstance()

    fun addWorkout(workout: Workout) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workoutRef = database.getReference("users").child(userId).child("workouts")
        val workoutId = workoutRef.push().key ?: return
        workoutRef.child(workoutId).setValue(workout)
    }

    fun getUserWorkouts(liveData: MutableLiveData<List<Workout>>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workoutRef = database.getReference("users").child(userId).child("workouts")

        workoutRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    workout?.let { workouts.add(it) }
                }
                liveData.value = workouts
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun deleteWorkout(workoutId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workoutRef = database.getReference("users").child(userId).child("workouts").child(workoutId)
        workoutRef.removeValue()
    }
}
