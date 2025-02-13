package com.example.fittrack.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fittrack.R
import com.example.fittrack.data.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddWorkoutFragment : Fragment() {
    private lateinit var etWorkoutName: EditText
    private lateinit var etDuration: EditText
    private lateinit var etCalories: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var btnSaveWorkout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_workout, container, false)

        etWorkoutName = view.findViewById(R.id.etWorkoutName)
        etDuration = view.findViewById(R.id.etDuration)
        etCalories = view.findViewById(R.id.etCalories)
        etImageUrl = view.findViewById(R.id.etImageUrl)
        btnSaveWorkout = view.findViewById(R.id.btnSaveWorkout)

        btnSaveWorkout.setOnClickListener {
            saveWorkoutToFirebase()
        }
        return view
    }

    private fun saveWorkoutToFirebase() {
        val title = etWorkoutName.text.toString().trim()
        val duration = etDuration.text.toString().trim().toIntOrNull() ?: 0
        val calories = etCalories.text.toString().trim().toIntOrNull() ?: 0
        val imageUrl = etImageUrl.text.toString().trim()

        if (title.isEmpty() || duration == 0 || calories == 0) {
            Toast.makeText(requireContext(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val workout = Workout(
            id = null,
            title = title,
            description = "Calories Burned: $calories",
            duration = duration,
            date = System.currentTimeMillis().toString(),
            imageUrl = imageUrl
        )

        val databaseRef = FirebaseDatabase.getInstance().getReference("users")
            .child(userId)
            .child("workouts")

        databaseRef.push().setValue(workout)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Workout Saved!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun clearFields() {
        etWorkoutName.text.clear()
        etDuration.text.clear()
        etCalories.text.clear()
        etImageUrl.text.clear()
    }
}
