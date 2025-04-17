package com.example.chordtrain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordtrain.AttemptDisplayData
import com.example.chordtrain.R
import com.example.chordtrain.StatisticsAdapter

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.attempts_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 🔹 Mock data
        val mockData = listOf(
            AttemptDisplayData(1, "C Major", "Easy", 3, 2, 3),
            AttemptDisplayData(2, "G Major", "Medium", 4, 3, 4),
            AttemptDisplayData(3, "A Minor", "Hard", 2, 1, 2)
        )

        recyclerView.adapter = StatisticsAdapter(mockData)

        return view
    }
}
