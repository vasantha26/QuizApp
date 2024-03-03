package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel
    private var questionsList: List<Result>? = null

    private var nextButton: Button? = null

    private var raddio: RadioGroup? = null

    private var trueButton: RadioButton? = null
    private var falseButton: RadioButton? = null
    private var questionsText: TextView? = null
    private var resultText: TextView? = null
    private var textView5: TextView? = null


    companion object {
        var i = 1
        var result = 0
        var totalQues = 0

    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer_items)
        nextButton = findViewById(R.id.bv_start_quiz)


        val intent = intent

        val textName = intent.getStringExtra(applicationContext.getString(R.string.nameInt))
        val textId = intent.getStringExtra(applicationContext.getString(R.string.idInt))


        trueButton = findViewById(R.id.tv_re)
        falseButton = findViewById(R.id.tv_reg)
        resultText = findViewById(R.id.tv_name3)
        questionsText = findViewById(R.id.tv_name2)
        textView5 = findViewById(R.id.quiz_timer)
        raddio = findViewById(R.id.radio)



        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]







        questionsList = viewModel.getAllQuizesResponse()
        GlobalScope.launch {
            if (questionsList != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    questionsText?.text =
                        applicationContext.getString(R.string.Ques) + 1 + questionsList!![0].question


                    if (questionsList!![0].correctAnswer != null && questionsList!![0].incorrectAnswers.toString() != null){
                        trueButton?.text = questionsList!![0].correctAnswer
                        falseButton?.text = questionsList!![0].incorrectAnswers.toString()

                    }else{
                        trueButton?.text  =  applicationContext.getString(R.string.t)
                        falseButton?.text = applicationContext.getString(R.string.f)


                    }


                }
            }
        }





        nextButton?.setOnClickListener {


            val selection = raddio?.checkedRadioButtonId
            if (selection != -1) {
                val radioButton = selection?.let { it1 -> findViewById<View>(it1) } as RadioButton

                questionsList.let {

                    if (i < it?.size!!) {
                        totalQues = it.size

                        if (radioButton.text.toString() == it[i - 1].correctAnswer) {
                            result++
                            resultText?.text =
                                applicationContext.getString(R.string.correct_ans) + result

                        }

                        questionsText?.text = applicationContext.getString(R.string.Ques) + { i + 1 } + questionsList!![0].question




                        if (questionsList!![0].correctAnswer != null && questionsList!![0].incorrectAnswers.toString() != null){
                            trueButton?.text = questionsList!![0].correctAnswer
                            falseButton?.text = questionsList!![0].incorrectAnswers.toString()
                        }else {
                            trueButton?.text = applicationContext.getString(R.string.t)
                            falseButton?.text = applicationContext.getString(R.string.f)

                        }


                        if (i == it.size.minus(1)) {
                            nextButton?.text = applicationContext.getString(R.string.finesh)
                        }

                        raddio?.clearCheck()
                        i++

                    } else {

                        if (radioButton.text.toString() == it[i - 1].correctAnswer) {
                            result++
                            resultText?.text =
                                applicationContext.getString(R.string.correct_ans) + result
                        }

                        val intent =
                            Intent(this@PlayActivity, ResultActivity::class.java)
                        intent.putExtra(
                            applicationContext.getString(R.string.nameInt),
                            textName
                        )
                        intent.putExtra(
                            applicationContext.getString(R.string.idInt),
                            textId
                        )
                        intent.putExtra(
                            applicationContext.getString(R.string.totalInt),
                            totalQues
                        )
                        intent.putExtra(
                            applicationContext.getString(R.string.resultInt),
                            result
                        )
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    applicationContext.getString(R.string.SelectAnswerInt),
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }
}