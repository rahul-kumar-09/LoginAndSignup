package com.programmingz.emailandpasswordrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var etMail: EditText
    private lateinit var etPassword: EditText
    private lateinit var signUp: Button
    private lateinit var login: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        etMail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        signUp = findViewById(R.id.signUp)
        login = findViewById(R.id.login)

        signUp.setOnClickListener {
            startActivity(Intent(this, SinUpActivity::class.java))
        }

        login.setOnClickListener {
            val email = etMail.text.toString().trim()
            val pass = etPassword.text.toString().trim()


                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this) { task->
                    if (task.isSuccessful){
                        //sign in is success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            updateUI()

                    } else {
                        Toast.makeText(this, "Email and Password not corrected", Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            updateUI()
        }
    }

}