package com.example.chordtrain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordtrain.db.AttemptWithChords

class StatisticsAdapter() :
    RecyclerView.Adapter<StatisticsAdapter.AttemptViewHolder>() {

    private var attempts = listOf<AttemptWithChords>()

    inner class AttemptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attempt: AttemptWithChords) {
            itemView.findViewById<TextView>(R.id.attempt_info_text).text = "Attempt #${attempt.attempt.attemptId} — ${attempt.attempt.key}, ${attempt.attempt.difficulty}, Length ${attempt.chords.size}"
            itemView.findViewById<TextView>(R.id.attempt_score_text).text = "Score: ${attempt.chords.size}/${attempt.chords.size}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttemptViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attempt, parent, false)
        return AttemptViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttemptViewHolder, position: Int) {
        holder.bind(attempts[position])

    }

    override fun getItemCount(): Int = attempts.size

    fun setAttempts(newAttempts: List<AttemptWithChords>) {
        attempts = newAttempts
        notifyDataSetChanged()
    }


}
