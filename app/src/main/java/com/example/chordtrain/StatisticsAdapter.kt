package com.example.chordtrain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter(private val attempts: List<AttemptDisplayData>) :
    RecyclerView.Adapter<StatisticsAdapter.AttemptViewHolder>() {

    inner class AttemptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val attemptInfo: TextView = itemView.findViewById(R.id.attempt_info_text)
        val attemptScore: TextView = itemView.findViewById(R.id.attempt_score_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttemptViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attempt, parent, false)
        return AttemptViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttemptViewHolder, position: Int) {
        val attempt = attempts[position]
        holder.attemptInfo.text = "Attempt #${attempt.attemptId} — ${attempt.key}, ${attempt.difficulty}, Length ${attempt.length}"
        holder.attemptScore.text = "Score: ${attempt.correctCount}/${attempt.totalChords}"
    }

    override fun getItemCount(): Int = attempts.size
}
