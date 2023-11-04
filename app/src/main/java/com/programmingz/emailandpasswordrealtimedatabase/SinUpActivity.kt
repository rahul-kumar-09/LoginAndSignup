package com.programmingz.emailandpasswordrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SinUpActivity : AppCompatActivity() {
    private lateinit var tvEmail: EditText
    private lateinit var tvPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLoginButton: Button
    private lateinit var tvName: EditText
    private lateinit var tvAddress: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_up)

        tvEmail = findViewById(R.id.dEmail)
        tvPassword = findViewById(R.id.tvPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvAddress = findViewById(R.id.dAddress)
        tvName = findViewById(R.id.dName)
        btnLoginButton = findViewById(R.id.btnLoginButton)

        auth = Firebase.auth
        database = Firebase.database.reference

        btnRegister.setOnClickListener {
            val sEmail = tvEmail.text.toString().trim()
            val sPassword = tvPassword.text.toString().trim()

            if (sEmail.isNotEmpty() && sPassword.isNotEmpty()){
                auth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(this) { task->
                    if (task.isSuccessful){
                        //sign in success
                        saveData()
                        updateUI()

                    } else {
                        Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this, "Empty blank not allows", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun saveData() {
        val sEmail = tvEmail.text.toString().trim()
        val sAddress = tvAddress.text.toString().trim()
        val sName = tvName.text.toString().trim()
        val sPass = tvPassword.text.toString().trim()

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        val user = userModel(sName,sEmail,sAddress, sPass)
        database.child("User").child(userID).setValue(user)

    }

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            updateUI()
        }
    }

}