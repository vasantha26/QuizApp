package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel
    private var repository: QuizRepository? = null
    private var button: Button? = null
    private var editText: EditText? = null
    private var editText1: EditText? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = QuizRepository(application)
        button = findViewById(R.id.bv_start_quiz1)

        editText = findViewById(R.id.tv_name1)
        editText1 = findViewById(R.id.tv_registration_number)

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]


        val userName = editText?.text
        val idName = editText1?.text


        button?.setOnClickListener {

            makeRequest()

            if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(idName)) {
                return@setOnClickListener
            } else if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(idName)) {

                val intent = Intent(this@MainActivity, PlayActivity::class.java);
                intent.putExtra(applicationContext.getString(R.string.nameInt), userName)
                intent.putExtra(applicationContext.getString(R.string.idInt), idName)
                startActivity(intent)
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun makeRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(QuizApiService::class.java)
        GlobalScope.launch {
            api.getQuestions(15, 9, applicationContext.getString(R.string.easy), applicationContext.getString(R.string.b)).enqueue(object :
                Callback<QuizModel> {
                override fun onResponse(call: Call<QuizModel>, response: Response<QuizModel>) {
                    Log.d("TAG", "  response >>>  " + Gson().toJson(response))
                    if (response.isSuccessful) {
                        val questions = response.body()?.results?.map { question ->
                            Result(
                                category = question.category,
                                type = question.type,
                                difficulty = question.difficulty,
                                question = question.question,
                                correctAnswer = question.correctAnswer,
                            )
                        }

                        if (questions != null) {
                            repository?.insert(questions)
                        }

                    }
                }

                override fun onFailure(call: Call<QuizModel>, t: Throwable) {
                    Log.d("TAG", "  response >>>1  " + t.message)
                }
            })
        }
    }
}