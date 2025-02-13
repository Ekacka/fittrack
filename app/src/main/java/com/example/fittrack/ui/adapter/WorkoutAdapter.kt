package com.example.fittrack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fittrack.R
import com.example.fittrack.data.Workout
import com.example.fittrack.databinding.ItemWorkoutBinding

class WorkoutAdapter(private var workouts: List<Workout>) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workouts[position])
    }

    override fun getItemCount(): Int = workouts.size

    fun updateWorkouts(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    fun getWorkoutAt(position: Int): Workout = workouts[position]

    inner class WorkoutViewHolder(private val binding: ItemWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutTitle.text = workout.title
            binding.workoutDescription.text = workout.description

            if (!workout.imageUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(workout.imageUrl)
                    .into(binding.workoutImage)
            } else {
                binding.workoutImage.setImageResource(R.drawable.placeholder)
            }
        }
    }
}
