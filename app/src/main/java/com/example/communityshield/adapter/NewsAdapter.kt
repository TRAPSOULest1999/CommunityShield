package com.example.communityshield.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.communityshield.R
import com.example.communityshield.databinding.NewsListItemBinding
import com.example.communityshield.model.NewsData
import com.example.communityshield.view.NewActivity

class NewsAdapter(
    var c:Context, var newsList:ArrayList<NewsData>
):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()
{

    inner class NewsViewHolder(var v:NewsListItemBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<NewsListItemBinding>(inflter, R.layout.news_list_item, parent, false)
        return NewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newList = newsList[position]
        holder.v.isNews = newsList[position]
            holder.v.root.setOnClickListener {
                val img = newList.img
                val heading = newList.heading
                val info = newList.info

                /** Set Data **/
                val mIntent = Intent(c, NewActivity::class.java)
                mIntent.putExtra("img",img)
                mIntent.putExtra("heading",heading)
                mIntent.putExtra("info",info)
                c.startActivity(mIntent)
            }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}