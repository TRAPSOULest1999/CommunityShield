package com.example.communityshield

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnForgotPassword.setOnClickListener {
            val email:String = etFPEmailAddress.text.toString().trim { it <= ' ' }
            //Check user entered something into the editText
            if(email.isEmpty())
            {
                Toast.makeText(this@ForgotPasswordActivity, "Enter email address", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //Get user firebase instance
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        //Ensure getting instance is successful
                        if (task.isSuccessful)
                        {
                            Toast.makeText(this@ForgotPasswordActivity, "Password link successfully sent, check email", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else
                        {
                            //Send user error message
                            Toast.makeText(this@ForgotPasswordActivity, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}