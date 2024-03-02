package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private var button : Button ?= null

    private var textView : TextView?= null
    private var textView1 : TextView?= null
    private var textView2 : TextView?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        button = findViewById(R.id.btnFinishResult)

        textView = findViewById(R.id.tvNameResult)
        textView1 = findViewById(R.id.tvNameResult1)
        textView2 = findViewById(R.id.tvNameResult2)


        val intent = intent

        val textName = intent.getStringExtra(applicationContext.getString(R.string.nameInt))
        val textId =  intent.getStringExtra(applicationContext.getString(R.string.idInt))
        val textResult =  intent.getStringExtra(applicationContext.getString(R.string.resultInt))
        val textTotal =  intent.getStringExtra(applicationContext.getString(R.string.totalInt))

        textView?.text = textName
        textView1?.text = textId
        textView2?.text = "$textResult/$textTotal"




        button?.setOnClickListener {
            val intent = Intent(this@ResultActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}