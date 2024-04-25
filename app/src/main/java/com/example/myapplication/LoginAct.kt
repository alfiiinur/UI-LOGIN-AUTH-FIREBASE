package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.myapplication.content.Home
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginAct : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()



        binding.btnRegis.setOnClickListener{
            val email = binding.flemail.text.toString().trim()
            val password = binding.flpass.text.toString().trim()

            if ( email.isEmpty() || password.isEmpty() ) {
                // Tampilkan pesan kesalahan, misalnya menggunakan Toast
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.flemail.error = " Email Tidak valid"
                binding.flemail.requestFocus()
                return@setOnClickListener
            }

            //validasi panjang password
            if (password.length < 6) {
                binding.flpass.error = " Password Harus 6 Karakter"
                binding.flpass.requestFocus()
                return@setOnClickListener
            }


            LoginFirebase(email, password)
        }

    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    val exception = task.exception
                    Toast.makeText(this, "${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }


    }

    fun openRegistrationActivity(view: View) {
        // Lakukan tindakan yang diinginkan saat teks "Register" diklik
        val intent = Intent(this, FormAct::class.java)
        startActivity(intent)
    }
}