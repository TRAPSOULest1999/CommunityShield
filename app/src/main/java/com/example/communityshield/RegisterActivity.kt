package com.example.communityshield

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.communityshield.databinding.ActivityMainBinding
import com.example.communityshield.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityRegisterBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //Progress Dialog
    private lateinit var progressDialog: ProgressDialog
    //Firbase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        //Configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Register"

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)*/

        //Configure progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Registering your account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //handle click event, begins registering user
        binding.butnRegister.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email = binding.editTextUserRegEmailAddress.text.toString().trim()
        password = binding.editTextUserRegPassword.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            //Invalid email format
            binding.editTextUserRegEmailAddress.error = "Invalid Email Format"
        }
        else if(TextUtils.isEmpty(password))
        {
            //no password entered
            binding.editTextUserRegPassword.error = "Please Enter Password"
        }
        else if(!password.matches(".*[A-Z].*".toRegex()))
        {
            binding.editTextUserRegPassword.error = "password must contain at least 1 UPPER-CASE letter"
        }
        else if(password.length < 8)
        {
            binding.editTextUserRegPassword.error = "password must  minimum of 8 characters"
        }
        else if(!password.matches(".*[a-z].*".toRegex()))
        {
            binding.editTextUserRegPassword.error = "password must contain at least 1 lower-case letter"
        }
        else if(!password.matches(".*[@#$%!&].*".toRegex()))
        {
            binding.editTextUserRegPassword.error = "password must contain at least 1 of these special characters (@#$%!&)"
        }
        else
        {
            //data is validated, register user
            firebaseSignUp()
        }

    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                //Registration Successful
                progressDialog.dismiss()

                //get user info
                val firebaseUser = firebaseAuth.currentUser
                uid = firebaseAuth.currentUser?.uid.toString()
                databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo")
                val email = firebaseUser!!.email
                firebaseUser.sendEmailVerification()
                val userName = ""
                val userSurname = ""
                val cell = ""
                val address = ""

                val userInfo = UserInfo(/*uid,*/userName,userSurname,cell,address)

                if (uid.isNotEmpty())
                { databaseReference.child(uid).setValue(userInfo)}

                Toast.makeText(this,"You have registered with email: $email", Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Verify Email and then log-in",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
            }
            .addOnFailureListener {e->
                //Registration failed
                progressDialog.dismiss()
                Toast.makeText(this,"Registration Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()// go back to previous activity when back button of ActionBar is pressed
        return super.onSupportNavigateUp()
    }
}