package com.example.fittrack.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fittrack.databinding.FragmentProfileBinding
import com.example.fittrack.network.fetchQuote
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        fetchAndDisplayQuote()
        return binding.root
    }

    private fun fetchAndDisplayQuote() {
        lifecycleScope.launch {
            val quote = fetchQuote()
            binding.quoteTextView.text = quote?.q ?: "Stay motivated!"
        }
    }
}
