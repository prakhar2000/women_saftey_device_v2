package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.salvager.R.id
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

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
        else if(email!!.isEmpty()){
            Toast.makeText(this, "Please enter a email", Toast.LENGTH_SHORT).show()
        }
        else if(password!!.isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
        }
        else if(phoneNo!!.isEmpty()){
            Toast.makeText(this, "Please enter a phone no.", Toast.LENGTH_SHORT).show()
        }
        else {
            mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val ref=FirebaseDatabase.getInstance().getReference("Members")
                        val userId = ref.push().key
                        val member= userId?.let { Members(it, firstName!!, email!!, phoneNo!!) }

                        if (userId != null) {
                            ref.child(userId).setValue(member).addOnCanceledListener {
                                Toast.makeText(this,"User registered successful",Toast.LENGTH_LONG).show()
                                val intent=Intent(applicationContext,MainAcitivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updateUser() {

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
