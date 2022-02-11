package com.example.communityshield

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ExitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        val backToLogin = findViewById<LinearLayout>(R.id.exit)

        // set on-click listener
        backToLogin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            //sign-out
            Firebase.auth.signOut()
            //Open login activity
            startActivity(intent)
            finish()
        }


    }
}