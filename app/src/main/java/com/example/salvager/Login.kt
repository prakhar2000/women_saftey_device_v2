package com.example.salvager



import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//
//        // Configure sign-in to request the user's ID, email address, and basic
//// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }


        login_button.setOnClickListener {

            when{
                TextUtils.isEmpty(emailtext.text.toString().trim{ it<= ' '})->{
                    Toast.makeText(
                        this,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                TextUtils.isEmpty(passwordtext.text.toString().trim{ it<= ' '})->{
                    Toast.makeText(
                        this,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()


                }
                else ->{
                    val email : String=emailtext.text.toString().trim(){it <= ' '}
                    val password : String=passwordtext.text.toString().trim(){it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                //firebase registered user

//                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this,
                                    "You are logged in successfully",
                                    Toast.LENGTH_SHORT

                                ).show()

                                val intent = Intent(this, MapsActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                }
            }
        }
    }
}


//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.provider.ContactsContract.Profile
//import android.text.TextUtils
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import com.example.salvager.R.layout
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class Login : AppCompatActivity() {
//
//    //global variables
//    private var email: String? = null
//    private var password: String? = null
//
//    //UI elements
//    private var etEmail: EditText? = null
//    private var etPassword: EditText? = null
//    private var btnLogin: Button? = null
//    private var btnCreateAccount: TextView? = null
//
//    //Firebase references
//    private var mAuth: FirebaseAuth? = null
//    private lateinit var database:FirebaseDatabase
//    private lateinit var reference: DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(layout.activity_login)
//
//        initialise()
//    }
//
//    private fun initialise() {
//        etEmail = findViewById<View>(R.id.email) as EditText
//        etPassword = findViewById<View>(R.id.password) as EditText
//        btnLogin = findViewById<View>(R.id.login_button) as Button
//        btnCreateAccount = findViewById<View>(R.id.signup) as TextView
//
//        mAuth = FirebaseAuth.getInstance()
//        btnCreateAccount!!
//            .setOnClickListener { startActivity(Intent(this, Register::class.java)) }
//        btnLogin!!.setOnClickListener { loginUser() }
//    }
//
//    private fun loginUser() {
//        email = etEmail?.text.toString()
//        password = etPassword?.text.toString()
//
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
//                        val intent= Intent(this, Profile::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(this,"Enter correct username or password",Toast.LENGTH_LONG).show()
//                    }
//                }
//        } else {
//            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun profileLogin(view: View) {
//        val intent= Intent(this, Profile::class.java)
//        startActivity(intent)
//    }
//
//    fun signUp(view: View) {
//        val intent=Intent(this, Register::class.java)
//        startActivity(intent)
//    }
//
//}