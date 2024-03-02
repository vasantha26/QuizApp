package com.example.quizapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "question_table")
data class Result (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var type: String? = null,
    var difficulty: String? = null,
    var category: String? = null,
    var question: String? = null,
    var correctAnswer: String? = null,
//    var incorrectAnswers: ArrayList<String>? = null
)
