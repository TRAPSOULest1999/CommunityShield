package com.example.communityshield

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // get references to the various CardViews
        val cardReport = findViewById<CardView>(R.id.cardReport)
        val cardProfile = findViewById<CardView>(R.id.cardProfile)
        val cardNews = findViewById<CardView>(R.id.cardNews)
        val cardWanted = findViewById<CardView>(R.id.cardWanted)
        val cardAlert = findViewById<CardView>(R.id.cardAlert)
        val cardLogOut = findViewById<CardView>(R.id.cardLogOut)

        // set on-click listener
        cardReport.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }

        cardProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        cardNews.setOnClickListener{
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        cardWanted.setOnClickListener{
            val intent = Intent(this, WantedActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        cardAlert.setOnClickListener{
            val intent = Intent(this, AlertActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        cardLogOut.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            Firebase.auth.signOut()
            startActivity(intent)
            finish()
        }

    }
}