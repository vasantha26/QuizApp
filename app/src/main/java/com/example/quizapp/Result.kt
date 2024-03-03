package com.example.quizapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "question_table")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "type")
    var type: String?,
    @ColumnInfo(name = "difficulty")
    var difficulty: String?,
    @ColumnInfo(name = "category")
    var category: String?,
    @ColumnInfo(name = "question")
    var question: String?,
    @ColumnInfo(name = "correct_answer")
    var correctAnswer: String?,
    @ColumnInfo(name = "incorrect_answers")
    @field:TypeConverters(Converters::class)
    val incorrectAnswers: List<String>?
)
