package com.example.salvager

import android.Manifest
import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.salvager.databinding.ActivityMapsBinding
import com.example.salvager.emergency_contacts
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_emergency_contacts.*

import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    //----------------------------------------------------

     var flag:Boolean=false
    lateinit var num1:String

    lateinit var num2:String

    lateinit var num3:String
    lateinit var longi: String
    lateinit var lati: String

    private val PERMISSION_REQUEST = 10
    private var permission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.SEND_SMS,android.Manifest.permission.INTERNET
    )
    lateinit var context: Context
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our
    // Database Reference for Firebase.
    var databaseReference: DatabaseReference? = null


    //----------------------------------------------------

    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
    }

    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)

                        longi=location.longitude.toString()
                        lati=location.latitude.toString()
                        if(flag==true) {

                            val mPrefs: SharedPreferences = getSharedPreferences("numbers", 0)
                            num1 = mPrefs.getString("number1","").toString()
                            num2 = mPrefs.getString("number2","").toString()
                            num3 = mPrefs.getString("number3","").toString()
//
//        sendlocation(num1,longi,lati)
//
//        sendlocation(num2,longi,lati)
//
//        sendlocation(num3,longi,lati)

                            Toast.makeText(context , num1.toString(),Toast.LENGTH_LONG).show()

                            Toast.makeText(context , num3.toString(),Toast.LENGTH_LONG).show()

                            Toast.makeText(context , num2.toString(),Toast.LENGTH_LONG).show()
                        sendlocation(num1,longi,lati)

                        sendlocation(num2,longi,lati)

                        sendlocation(num3,longi,lati)
flag=false
                        }

                        Toast.makeText(context , latLng.toString(),Toast.LENGTH_LONG).show()
                        val markerOptions = MarkerOptions().position(latLng)
                        map.addMarker(markerOptions)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }

                }
            }
        }
    }

    private fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    //------------------------------------------------------main functn-------------------
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkPermission(this, permission)) {
            Toast.makeText(this, "Permission already given", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissions(permission, PERMISSION_REQUEST)
        }
//        setContentView(R.layout.activity_maps)
//
//
//        val addEmergencyContacts = findViewById<TextView>(com.example.salvager.R.id.aec)
//        addEmergencyContacts.setOnClickListener{onclickec()}
        //---------------------------------------------------------------
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
        //---------------------------------------------------------------


        //----------------------------------------------------
//
//         sign_out.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



    }




    //----------------------------------------------------


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allsuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allsuccess = false
                    var requestAgain =
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        ))
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Go to settinga and enable the permissions",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
            if (allsuccess) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST) {
//            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return
//                }
//                map.isMyLocationEnabled = true
//            }
//            else {
//                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
//                finish()
//            }
//        }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    fun checkPermission(context: Context, permissionArray: Array<String>): Boolean {
        var allsuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED) {
                allsuccess = false
            }

        }


        return allsuccess
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
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


                        flag=true

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
                Toast.makeText(this@MapsActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun sendlocation(phonenumber: String, longi:String,lati:String){
        var smsManager = SmsManager.getDefault()
        var smsBody = StringBuffer()
        smsBody.append("http://maps.google.com/maps?q=")
        smsBody.append(lati)
        smsBody.append(",")
        smsBody.append(longi)
        smsManager.sendTextMessage(phonenumber,null,smsBody.toString(),null,null)


    }
    private fun onclickpro() {
        startActivity(
            Intent(
                this,
                profileActivity::class.java
            )
        )
    }



    fun onclickec(view: android.view.View) {
        startActivity(Intent(this,com.example.salvager.emergency_contacts::class.java))
    }

    private fun onclickhome() {
        startActivity(Intent(this, MapsActivity::class.java))
    }
}