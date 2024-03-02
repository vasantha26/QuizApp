package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface QuestionDao {

    @Query("SELECT * FROM question_table")
    fun getAllQuestions(): LiveData<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions: List<Result>)
}