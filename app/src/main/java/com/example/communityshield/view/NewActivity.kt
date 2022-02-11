package com.example.communityshield.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communityshield.R
import com.example.communityshield.uitel.getProgressDrawable
import com.example.communityshield.uitel.loadImage
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        val newsIntent = intent
        val newsHeading = newsIntent.getStringExtra("heading")
        val newsInfo = newsIntent.getStringExtra("info")
        val newsImg = newsIntent.getStringExtra("img")

        heading.text = newsHeading
        info.text = newsInfo
        img.loadImage(newsImg, getProgressDrawable(this))

    }
}