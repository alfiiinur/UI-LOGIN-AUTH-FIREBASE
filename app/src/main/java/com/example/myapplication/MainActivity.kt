package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.hide()

        android.os.Handler().postDelayed({
            val intent = Intent(this@MainActivity, wellcomePage::class.java)
            startActivity(intent)
            finish()

        }, 3000)

    }
}