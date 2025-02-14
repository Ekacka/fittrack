package com.example.fittrack.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrack.databinding.FragmentHomeBinding
import com.example.fittrack.ui.adapter.WorkoutAdapter
import com.example.fittrack.ui.viewmodel.WorkoutViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var workoutAdapter: WorkoutAdapter
    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        workoutAdapter = WorkoutAdapter(emptyList())
        binding.recyclerView.adapter = workoutAdapter

        observeWorkouts()
        setupSwipeToDelete()

        return binding.root
    }

    private fun observeWorkouts() {
        viewModel.getWorkouts().observe(viewLifecycleOwner, Observer { workouts ->
            workoutAdapter.updateWorkouts(workouts)
        })
    }



    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val workout = workoutAdapter.getWorkoutAt(position)

                println("Workout to delete: ${workout.id}")

                if (workout.id != null) {
                    viewModel.deleteWorkout(workout.id!!)
                    workoutAdapter.notifyItemRemoved(position)
                } else {
                    println("Workout ID is null! Cannot delete.")
                    workoutAdapter.notifyItemChanged(position)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}
