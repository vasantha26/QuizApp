package com.example.quizapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuizRepository
    private var questionEntity: LiveData<List<Result>>

    init {
        repository = QuizRepository(application)
        questionEntity = repository.getAllCats()
    }


    fun getAllQuizes(): LiveData<List<Result>> {
        return questionEntity
    }
}
