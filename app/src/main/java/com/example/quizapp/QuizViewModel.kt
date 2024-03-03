package com.example.quizapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuizRepository
    private var quizResponseList: List<Result>

    init {
        repository = QuizRepository(application)
        quizResponseList = repository.quizResponseListResult()
    }


    fun getAllQuizesResponse(): List<Result> {
        return quizResponseList
    }


    fun deleteQuizList() = repository.deleteList()
}
