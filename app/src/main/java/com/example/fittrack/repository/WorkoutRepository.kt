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
        val workoutId = workoutRef.push().key ?: return  // Generate unique ID

        val workoutWithId = workout.copy(id = workoutId) // Assign ID before storing
        workoutRef.child(workoutId).setValue(workoutWithId)
    }



    fun getUserWorkouts(liveData: MutableLiveData<List<Workout>>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workoutRef = database.getReference("users").child(userId).child("workouts")

        workoutRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    val workoutId = workoutSnapshot.key  // Get the unique Firebase ID
                    if (workout != null && workoutId != null) {
                        workouts.add(workout.copy(id = workoutId)) // Assign ID to object
                    }
                }
                liveData.value = workouts
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }


    fun deleteWorkout(workoutId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workoutRef = database.getReference("users").child(userId).child("workouts").child(workoutId)

        println("Attempting to delete workout: $workoutId") // 👈 Add this

        workoutRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Workout deleted successfully from Firebase: $workoutId")
            } else {
                println("Error deleting workout: ${task.exception?.message}")
            }
        }
    }


}
