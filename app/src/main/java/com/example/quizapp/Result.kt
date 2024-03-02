package com.example.quizapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "question_table")
data class Result (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var type: String? = null,
    var difficulty: String? = null,
    var category: String? = null,
    var question: String? = null,
    var correctAnswer: String? = null,
    @ColumnInfo(name = "incorrectAnswers")
    @field:TypeConverters(Converters::class)
    var incorrectAnswers: List<String>? = null
)
