package com.example.communityshield

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.communityshield.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //Progress Dialog
    private lateinit var progressDialog:ProgressDialog
    //Firbase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    companion object{const val isDeleted = false}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        //Configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"*/


        //Configure progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        //handle click event, begins ForgotPasswordActivity
        binding.etLoginForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        //handle click event, begins registerActivity
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //handle click event, begins login
        binding.btnLogin.setOnClickListener{
            //before logging in, validate user data
            validateData()
        }

    }

    private fun validateData() {
        //get data
        email = binding.editTextUserEmailAddress.text.toString().trim()
        password = binding.editTextUserPassword.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            //Invalid email format
            binding.editTextUserEmailAddress.error = "Invalid Email Format"
        }
        else if(TextUtils.isEmpty(password))
        {
            //no password entered
            binding.editTextUserPassword.error = "Please Enter Password"
        }
        else
        {
            //data is validated, begin login
            fireBaseLogin()
        }
    }

    private fun fireBaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login successful
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                if(firebaseUser.isEmailVerified)
                {
                    //open ReportActivity
                    Toast.makeText(this,"Logged In as $email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ReportActivity::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(this@MainActivity,"Please check email for verification link",Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in, then goes to ReportActivity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null)
        {
           val  email = firebaseUser!!.email
            if(!isDeleted) {
                if(firebaseUser.isEmailVerified) {
                    //user is already logged in
                    startActivity(Intent(this, ReportActivity::class.java))
                }
            }
        }
    }


}