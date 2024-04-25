package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFormBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormAct : AppCompatActivity() {

    lateinit var binding: ActivityFormBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi FirebaseApp
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance() ?: throw NullPointerException("FirebaseAuth instance is null.")

        binding.t4.setOnClickListener{
            val intent = Intent(this@FormAct, LoginAct::class.java)
            startActivity(intent)
        }

        binding.btnRegis.setOnClickListener {
            val username = binding.fusername.text.toString().trim()
            val email = binding.femail.text.toString().trim()
            val telp = binding.ftelp.text.toString().trim()
            val password = binding.fpassword.text.toString().trim()
            val retakepass = binding.fretake.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || telp.isEmpty() || password.isEmpty() || retakepass.isEmpty()) {
                // Tampilkan pesan kesalahan, misalnya menggunakan Toast
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.femail.error = " Email Tidak valid"
                binding.femail.requestFocus()
                return@setOnClickListener
            }

            //validasi panjang password
            if (password.length < 6) {
                binding.fpassword.error = " Password Harus 6 Karakter"
                binding.fpassword.requestFocus()
                return@setOnClickListener
            }

            //RETAKE PASSWORD
            // Lakukan validasi khusus, misalnya, pastikan password dan retakePassword sama
            if (password != retakepass) {
                // Tampilkan pesan kesalahan, misalnya menggunakan Toast
                Toast.makeText(this, "Password dan Retype Password harus sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isInternetAvailable()) {
                Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerFirebase(email, password)
        }
    }

    private fun registerFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "REGISTER BERHASIL", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginAct::class.java)
                    startActivity(intent)
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        // Handle user already exists
                    } else if (exception is FirebaseAuthWeakPasswordException) {
                        Toast.makeText(this, "Password terlalu lemah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun isInternetAvailable(): Boolean {
        return true
    }

}
