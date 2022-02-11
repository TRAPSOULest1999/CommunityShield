package com.example.communityshield

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityshield.adapter.NewsAdapter
import com.example.communityshield.model.NewsData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var mDataBase:DatabaseReference
    private lateinit var newsList:ArrayList<NewsData>
    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsList = ArrayList()
        mAdapter = NewsAdapter(this, newsList)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerView.adapter = mAdapter
        getNewsData()
    }

    private fun getNewsData() {
        mDataBase = FirebaseDatabase.getInstance().getReference("News")
        mDataBase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Check that news data exists
                if (snapshot.exists()){
                    //For each news data it adds for display
                    for ( newsSnapshot in snapshot.children){
                        val news = newsSnapshot.getValue(NewsData::class.java)
                        newsList.add(news!!)
                    }
                    newsRecyclerView.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NewsActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}