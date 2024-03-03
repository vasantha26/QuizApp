package com.example.quizapp

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class QuizRepository(application: Application) {

    private var questionDao: QuestionDao
    private val database: AppDatabase
    private var quizResponseList: List<Result>


    init {
        database = AppDatabase.getInstance(application)
        questionDao = database.questionDao()
        quizResponseList = questionDao.getAllQuestions()

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insert(questionEntity: List<Result>) {
        GlobalScope.launch(Dispatchers.IO) {
            questionEntity.let { questionDao.insertQuestions(it) }
        }
    }


    fun quizResponseListResult(): List<Result> {
        return quizResponseList
    }


    fun deleteList() = questionDao.deleteAll()

}
