package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.salvager.R.layout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainAcitivity : AppCompatActivity() {

    //global variables
    private var email: String? = null
    private var password: String? = null

    //UI elements
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: TextView? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null
    private lateinit var database:FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        initialise()
    }

    private fun initialise() {
        etEmail = findViewById<View>(R.id.email) as EditText
        etPassword = findViewById<View>(R.id.password) as EditText
        btnLogin = findViewById<View>(R.id.login_button) as Button
        btnCreateAccount = findViewById<View>(R.id.signup) as TextView

        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!
            .setOnClickListener { startActivity(Intent(this, Register::class.java)) }
        btnLogin!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                        val intent= Intent(this, Profile::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,"Enter correct username or passowrd",Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    fun profileLogin(view: View) {
        val intent= Intent(this, Profile::class.java)
        startActivity(intent)
    }

    fun signUp(view: View) {
        val intent=Intent(this, Register::class.java)
        startActivity(intent)
    }

}