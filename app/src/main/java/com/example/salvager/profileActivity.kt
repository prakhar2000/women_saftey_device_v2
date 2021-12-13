package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val addEmergencyContacts= findViewById<TextView>(R.id.add_emergency_contacts)
        addEmergencyContacts.setOnClickListener{onclickec()}

        val homepage=findViewById<TextView>(R.id.home)
        homepage.setOnClickListener{ onclickhome() }


        sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }


    private fun onclickec() {
        startActivity(Intent(this, emergency_contacts::class.java))
    }

    private fun onclickhome() {
        startActivity(Intent(this, MapsActivity::class.java))
    }



}
