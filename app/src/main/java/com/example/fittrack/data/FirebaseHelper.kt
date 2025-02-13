package com.example.fittrack.data

import com.example.fittrack.data.Workout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://fittrack-53376-default-rtdb.firebaseio.com/")
        .getReference("workouts")

    fun addWorkout(workout: Workout, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val id = database.push().key ?: return
        val newWorkout = workout.copy(id = id)
        database.child(id).setValue(newWorkout)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getWorkouts(onDataReceived: (List<Workout>) -> Unit) {
        database.get().addOnSuccessListener { snapshot ->
            val workouts = snapshot.children.mapNotNull {
                val id = it.key ?: return@mapNotNull null
                it.getValue(Workout::class.java)?.copy(id = id)
            }
            onDataReceived(workouts)
        }
    }



    fun deleteWorkout(id: String, onComplete: () -> Unit) {
        database.child(id).removeValue().addOnCompleteListener { onComplete() }
    }
}
