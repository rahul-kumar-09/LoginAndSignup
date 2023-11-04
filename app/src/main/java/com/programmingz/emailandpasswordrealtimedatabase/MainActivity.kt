package com.programmingz.emailandpasswordrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogOut: Button
    private lateinit var dName: TextView
    private lateinit var dEmail: TextView
    private lateinit var dAddress: TextView

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference

        btnLogOut = findViewById(R.id.btnLogOut)
        dName = findViewById(R.id.dName)
        dEmail = findViewById(R.id.dEmail)
        dAddress = findViewById(R.id.dAddress)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {
            val name = it.child("name").value.toString()
            val email = it.child("email").value.toString()
            val address = it.child("address").value.toString()

            dName.text = name
            dEmail.text = email
            dAddress.text = address

        }.addOnFailureListener {
            Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
        }

        btnLogOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}