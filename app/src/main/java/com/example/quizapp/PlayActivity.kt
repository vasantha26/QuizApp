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

class PlayActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel
    var result: List<Result>? = null

    private var button: Button? = null

    private var raddio: RadioGroup? = null

    private var textView: RadioButton? = null
    private var textView1: RadioButton? = null
    private var textView4: TextView? = null
    private var textView3: TextView? = null
    private var textView5: TextView? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer_items)
        button = findViewById(R.id.bv_start_quiz)


        val intent = intent

        val textName = intent.getStringExtra(applicationContext.getString(R.string.nameInt))
        val textId = intent.getStringExtra(applicationContext.getString(R.string.idInt))

        textView = findViewById(R.id.tv_re)
        textView1 = findViewById(R.id.tv_reg)
        textView3 = findViewById(R.id.tv_name3)
        textView4 = findViewById(R.id.tv_name2)
        textView5 = findViewById(R.id.quiz_timer)
        raddio = findViewById(R.id.radio)



        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]


        viewModel.getAllQuizes().observe(this) { quit ->

            if (quit.isNotEmpty() && quit != null) {

                result = quit
                quit.forEach {
                    textView4?.text = it.question
                    textView?.text = it.correctAnswer
                    textView1?.text = it.incorrectAnswers.toString()
                }
            }
        }

        var i = 1
        var radioGroup = 0
        var totalQues = 0

        button?.setOnClickListener {


            val selection = raddio?.checkedRadioButtonId
            if (selection != -1) {
                val radioButton = selection?.let { it1 -> findViewById<View>(it1) } as RadioButton



                if (result?.size!! > 0 && result != null) {

                    result?.forEach { it1 ->
                        result.let {

                            if (i < it?.size!!) {


                                totalQues = it.size
                                if (radioButton.text.toString() == it1.correctAnswer) {
                                    radioGroup++
                                    textView3?.text =
                                        applicationContext.getString(R.string.correct_ans) + radioGroup

                                }
                                textView4?.text =
                                    applicationContext.getString(R.string.Ques) + { i + 1 } + it1.question
                                textView?.text = it1.correctAnswer
                                textView1?.text = it1.incorrectAnswers.toString()

                                if (i == it.size.minus(1)) {
                                    button?.text = applicationContext.getString(R.string.finesh)
                                }

                                raddio?.clearCheck()
                                i++

                            } else {

                                if (radioButton.text.toString() == it[i - 1].correctAnswer) {
                                    radioGroup++
                                    textView3?.text =
                                        applicationContext.getString(R.string.correct_ans) + radioGroup
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
                                    radioGroup
                                )
                                startActivity(intent)
                                finish()
                            }
                        }
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