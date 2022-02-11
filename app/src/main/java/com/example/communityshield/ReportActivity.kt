package com.example.communityshield

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_report.radioGroupR

class ReportActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var curLocation: String

    private val callback= object:LocationCallback(){
        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }
        //Gets GPS co-ordinates and sends SMS notification
        override fun onLocationResult(result: LocationResult?) {
            val lastLocation= result?.lastLocation
            var sms = SmsManager.getDefault()
            val checkedReportRadioBtn = radioGroupR.checkedRadioButtonId
            val urgency = findViewById<RadioButton>(checkedReportRadioBtn)
            Log.d("TAG", "onLocationResult: ${lastLocation?.longitude.toString()}")
            findViewById<TextView>(R.id.location).text="Longitude : "+lastLocation?.longitude.toString()+"\n"+"Latitude : "+lastLocation?.latitude.toString()
            curLocation = "Longitude : "+lastLocation?.longitude.toString() + "\n" + "Latitude : "+lastLocation?.latitude.toString()
            sms.sendTextMessage("+27679170271","Crime Alert","CRIME ALERT!!! \nUrgency: ${urgency.text} at\n"+curLocation,null,null)
            Toast.makeText(this@ReportActivity,"HELP IS ON THE WAY!",Toast.LENGTH_LONG).show()
            super.onLocationResult(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        //Check if user has granted permission for use of location and SMS services
        //If not then permission request will be sent
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.SEND_SMS),
                200
            )
        }
        else{

        }

        //get reference to button
        val button = findViewById<Button>(R.id.btnReport)
        button.setOnClickListener{
            onGPS()
            fetchLocation()
        }

        // get reference to ImageView
        val repMenu = findViewById<ImageView>(R.id.imageRepMenu)

        // set on-click listener
        repMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    //Check location is enabled
    //Send user to location settings if not enabled
    //Else fetch user location
    fun onGPS() {

        Log.d("TAG", "onGPS: ${isLocationEnabled()}")

        if (!isLocationEnabled()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            Toast.makeText(this,"Enable Location Services",Toast.LENGTH_LONG).show()
        } else {
            fetchLocation()
        }


    }

    //Get location
    private fun fetchLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //check iof location permissions are granted
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                //check if SMS permission is granted
                ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                //Request location and SMS permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.SEND_SMS),
                    200
                )
                return
            }else{
                //Get location
                requestLocation()
            }


        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        Log.d("TAG", "requestLocation: ")
        val requestLocation= LocationRequest()
        requestLocation.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        requestLocation.interval = 0
        requestLocation.fastestInterval = 0
        requestLocation.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(
            requestLocation,callback, Looper.myLooper()
        )

    }

    //check if device location is enabled
    fun isLocationEnabled(): Boolean {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}
