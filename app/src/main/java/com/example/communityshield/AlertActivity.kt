package com.example.communityshield

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.example.communityshield.databinding.ActivityAlertBinding
import com.example.communityshield.databinding.ActivityEditProfileBinding
import com.example.communityshield.model.NewsData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_report.*
import kotlin.properties.Delegates

class AlertActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityAlertBinding

    //Firbase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseAnonymousReference: DatabaseReference
    private lateinit var databaseNonAnoymousReference: DatabaseReference
    private lateinit var databaseUidReference: DatabaseReference
    private lateinit var uid: String
    private var counter by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAlertBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //get user firebase uid
        uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Alerts")
        databaseAnonymousReference = databaseReference.child("Anonymous")
        databaseNonAnoymousReference = databaseReference.child("Non-Anonymous")
        databaseUidReference = databaseNonAnoymousReference.child(uid)


        // set on-click listener
        binding.btnAlertBackMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        //set on-click listener
        binding.btnAlert.setOnClickListener {
            //Declare and initialize values
            val checkedAlertRadioBtn = radioGroupA.checkedRadioButtonId
            val alertType = findViewById<RadioButton>(checkedAlertRadioBtn)
            val location = binding.editAlertLocationTextMultiLine.text.toString()
            val details = binding.editAlertTextMultiLine.text.toString()

            //add values to databaseAnonymousReference
            if (alertType.text == "ANONYMOUS") {
                //Check that user has entered into both fields
                if (location.isNotEmpty() && details.isNotEmpty()) {
                    //Add data of type alertInfo to the database
                    val alert = AlertInfo(location, details)
                    databaseAnonymousReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val count = snapshot.childrenCount + 1
                            databaseAnonymousReference.child(count.toString()).setValue(alert)
                                .addOnSuccessListener {
                                    binding.editAlertLocationTextMultiLine.text.clear()
                                    binding.editAlertTextMultiLine.text.clear()
                                    //Confirm alert has been sent
                                    Toast.makeText(
                                        this@AlertActivity,
                                        "Successfully sent alert",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }.addOnFailureListener {
                                Toast.makeText(
                                    //Confirm that alert has not been sent yet
                                    this@AlertActivity,
                                    "Alert sending - Failed",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@AlertActivity, error.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    })

                }
                else
                {
                    Toast.makeText(this@AlertActivity,"Both Location and Details fields MUST be completed",Toast.LENGTH_LONG).show()
                }

            }
            else
            {
                //Check that user has entered into both fields
                if (location.isNotEmpty() && details.isNotEmpty()) {
                    //Add data of type alertInfo to the database
                    val alert = AlertInfo(location, details)
                    databaseUidReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val count = snapshot.childrenCount + 1
                            databaseUidReference.child(count.toString()).setValue(alert)
                                .addOnSuccessListener {
                                    binding.editAlertLocationTextMultiLine.text.clear()
                                    binding.editAlertTextMultiLine.text.clear()
                                    //Confirm alert has been sent
                                    Toast.makeText(
                                        this@AlertActivity,
                                        "Successfully sent alert",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //Confirm alert has not been sent
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this@AlertActivity,
                                        "Alert sending - Failed",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@AlertActivity, error.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                }
                else
                {
                    Toast.makeText(this@AlertActivity,"Both Location and Details fields MUST be completed",Toast.LENGTH_LONG).show()
                }

            }



        }
    }
}