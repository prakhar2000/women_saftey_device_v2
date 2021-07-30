package com.example.salvager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_emergency_contacts.*
import kotlinx.android.synthetic.main.activity_emergency_contacts.sign_out
import kotlinx.android.synthetic.main.activity_maps.*


class emergency_contacts : AppCompatActivity() {


    lateinit var btn_submit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

        loaddata()
        btn_submit = findViewById<Button>(R.id.add_contacts)
        btn_submit.setOnClickListener(View.OnClickListener {

            var number1 = etv1.text.toString()
            var number2 =etv2.text.toString()
            var number3 = etv3.text.toString()


            val mPrefs = getSharedPreferences("numbers", 0)
            val editor = mPrefs.edit()
            editor.putString("number1",number1 )
            editor.putString("number2",number2 )
            editor.putString("number3",number3 )
            editor.commit()
            Toast.makeText(this , "Data Saved", Toast.LENGTH_SHORT).show()
        })


        val profilemem= findViewById<TextView>(R.id.profile)
        profilemem.setOnClickListener{onclickpro()}

        val homepage=findViewById<TextView>(R.id.home)
        homepage.setOnClickListener{ onclickhome() }


        sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }




    }

private fun loaddata(){
    val sharedPreference = getSharedPreferences("numbers",0)
    val number1 = sharedPreference.getString("number1","0")
    val number2 = sharedPreference.getString("number2","0")
    val number3 = sharedPreference.getString("number3","0")

    etv1.hint = number1
    etv2.hint =number2
    etv3.hint = number3


}

    private fun onclickpro() {
        startActivity(
            Intent(
                this,
                com.example.salvager.profileActivity::class.java
            )
        )
    }

    private fun onclickhome() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

}