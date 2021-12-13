package com.example.salvager

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.salvager.databinding.ActivityMaps2Binding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class MapsActivity2 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMaps2Binding

    lateinit var context: Context
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our
    // Database Reference for Firebase.
    var databaseReference: DatabaseReference? = null

    // variable for Text view.
    private var retriveTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        // Read from the database
        // Read from the database








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
                Toast.makeText(this@MapsActivity2, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}