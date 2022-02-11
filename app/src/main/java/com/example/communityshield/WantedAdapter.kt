package com.example.communityshield

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class WantedAdapter(private val wantedList: ArrayList<Wanted>) :
    RecyclerView.Adapter<WantedAdapter.WantedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wanted_list_item,parent,false)
        return WantedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WantedViewHolder, position: Int) {
        val currentItem = wantedList[position]
        holder.suspectImage.setImageResource(currentItem.suspectImage)
        holder.crimeInfo.text = currentItem.crimeInfo
        holder.briefReport.text = currentItem.briefReport

        val isVisible = currentItem.visibility
        holder.constraintLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.crimeInfo.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return wantedList.size
    }

    class WantedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val suspectImage : ShapeableImageView = itemView.findViewById(R.id.suspect_Image)
        val crimeInfo : TextView = itemView.findViewById(R.id.crime_info)
        val briefReport : TextView = itemView.findViewById(R.id.briefReport)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.expandedLayout)

    }

}