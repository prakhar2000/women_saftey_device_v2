package com.example.salvager

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class test : AppCompatActivity() {

    lateinit var context: Context
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our
    // Database Reference for Firebase.
    var databaseReference: DatabaseReference? = null

    // variable for Text view.
    private var retriveTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase!!.getReference("Data")

        // initializing our object class variable.
        //retriveTV = findViewById(R.id.idTVRetriveData)

        // calling method
        // for getting data.
        context=this
        getdata()

    }
    private fun getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                val value = snapshot.getValue(Int::class.java)

                if (value != null) {
                    if(value>400){
            //                    val value2=heart rate ki

                    }
                }
                // after getting the value we are setting
                // our value to our text view in below line.
//                if(value == "blue"){
//                    Toast.makeText(context , "reached",Toast.LENGTH_LONG).show()
////                    val intent = Intent(context, GoodTouch::class.java)
////                    startActivity(intent)
//                }
//                else if(value == "black"){
//                    Toast.makeText(context , "reached",Toast.LENGTH_LONG).show()
////                    val intent = Intent(context, BadTouch::class.java)
////                    startActivity(intent)
//                }

                    Toast.makeText(context , value.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(this@test, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}