package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.databinding.ActivityFormBinding
import com.example.myapplication.databinding.ActivityWellcomePageBinding

class wellcomePage : AppCompatActivity() {

    lateinit var binding : ActivityWellcomePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWellcomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textReg.setOnClickListener{
            val intent = Intent(this, FormAct::class.java)
            startActivity(intent)
        }


    }



}