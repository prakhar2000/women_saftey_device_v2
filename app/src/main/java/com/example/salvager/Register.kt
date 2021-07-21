package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
        var btnCreateAccount = findViewById<View>(id.signup_button) as Button

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        var etFirstName = findViewById<View>(id.name) as EditText
        var etEmail = findViewById<View>(id.email) as EditText
        var etPassword = findViewById<View>(id.password) as EditText
        var etPhone = findViewById<View>(R.id.phone) as EditText
        firstName = etFirstName.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        phoneNo = etPhone.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(phoneNo)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val userId = mAuth!!.currentUser!!.uid

                        //update user profile information

                        val ref=FirebaseDatabase.getInstance().getReference("users")
                        val userID=ref.push().key
                        val user= userID?.let { Users(it, firstName!!, email!!, phoneNo!!) }

                        ref.child(userId).setValue(user).addOnCanceledListener {
                            Toast.makeText(this, "Registering User",Toast.LENGTH_LONG).show()
                            var intent=Intent(this,MainAcitivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
        val intent = Intent(this, MainAcitivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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