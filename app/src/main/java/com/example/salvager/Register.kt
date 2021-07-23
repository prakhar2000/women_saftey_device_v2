package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.salvager.R.id
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //global variables
    private var firstName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var phoneNo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialise()
    }

    private fun initialise() {
        val btnCreateAccount = findViewById<View>(id.signup_button) as Button

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        val etFirstName = findViewById<View>(id.name) as EditText
        val etEmail = findViewById<View>(id.email) as EditText
        val etPassword = findViewById<View>(id.password) as EditText
        val etPhone = findViewById<View>(R.id.phone) as EditText
        firstName = etFirstName.text.toString().trim()
        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()
        phoneNo = etPhone.text.toString().trim()

        if(firstName!!.isEmpty()){
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
        }
        if(email!!.isEmpty()){
            Toast.makeText(this, "Please enter a email", Toast.LENGTH_SHORT).show()
        }
        if(password!!.isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
        }
        if(phoneNo!!.isEmpty()){
            Toast.makeText(this, "Please enter a phone no.", Toast.LENGTH_SHORT).show()
        }

        val ref=FirebaseDatabase.getInstance().getReference("Members")
        val userId = ref.push().key
        val member= userId?.let { Members(it, firstName!!, password!!, email!!, phoneNo!!) }

        if (userId != null) {
            ref.child(userId).setValue(member).addOnCanceledListener {
                Toast.makeText(this,"User registered successful",Toast.LENGTH_LONG).show()
                val intent=Intent(applicationContext,MainAcitivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun signup_page(view: View) {
        Toast.makeText(this, "Registartion Successful!",Toast.LENGTH_LONG).show()
        var intent= Intent(this, MainAcitivity::class.java)
        startActivity(intent)
    }

    fun login_page(view: View) {
        var intent= Intent(this, MainAcitivity::class.java)
        startActivity(intent)
    }
}