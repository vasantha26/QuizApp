package com.example.quizapp

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class QuizRepository(application: Application) {

    private var questionDao: QuestionDao
    private var quizResponse: LiveData<List<Result>>
    private val database: AppDatabase

    init {
        database = AppDatabase.getInstance(application)
        questionDao = database.questionDao()
        quizResponse = questionDao.getAllQuestions()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insert(questionEntity: List<Result>) {
        GlobalScope.launch(Dispatchers.IO) {
            questionEntity.let { questionDao.insertQuestions(it) }
        }
    }


    fun getAllCats(): LiveData<List<Result>> {
        return quizResponse
    }


}
