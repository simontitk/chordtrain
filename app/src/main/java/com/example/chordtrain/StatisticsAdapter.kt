package com.example.chordtrain

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordtrain.db.AttemptWithChords

class StatisticsAdapter() :
    RecyclerView.Adapter<StatisticsAdapter.AttemptViewHolder>() {

    private var attempts = listOf<AttemptWithChords>()

    inner class AttemptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attempt: AttemptWithChords) {
            val score = attempt.chords.count {it.hit}
            itemView.findViewById<TextView>(R.id.attempt_key).text = attempt.attempt.key
            itemView.findViewById<TextView>(R.id.attempt_difficulty).text = attempt.attempt.difficulty
            itemView.findViewById<TextView>(R.id.attempt_score).text = "$score / ${attempt.chords.size}"

            val attemptChordsContainer = itemView.findViewById<LinearLayout>(R.id.attempt_chords_container)
            attempt.chords.forEach { chordAttempt ->
                val chordView = TextView(itemView.context).apply {
                    text = chordAttempt.chord
                    width = 200
                    gravity = Gravity.CENTER
                    setTextColor(Color.WHITE)
                    setPadding(12, 12, 12, 12)
                    setBackgroundColor(if (chordAttempt.hit) Color.parseColor("#247A35") else Color.RED)
                }
                val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                params.setMargins(10, 14, 10, 0)  // left, top, right, bottom
                chordView.layoutParams = params
                attemptChordsContainer.addView(chordView)
            }
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
