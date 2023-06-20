package com.example.task.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.DiscriptionScreen
import com.example.task.R
import com.example.task.model.AllCountryWise
import com.example.task.model.DisModel

class DisRecyclerViewAdapter(
    var context: Context,
    var aarrayList: DisModel
): RecyclerView.Adapter<DisRecyclerViewAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var date: TextView = itemView.findViewById(R.id.date)
        var caseTextDisNumber: TextView = itemView.findViewById(R.id.caseTextDisNumber)
        var recoveredTextDisNumber: TextView = itemView.findViewById(R.id.recoveredTextDisNumber)
        var deathsTextDisNumber: TextView = itemView.findViewById(R.id.deathsTextDisNumber)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        var view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.country_dis_card, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.date.text = aarrayList.timeline.cases.toList().get(position).first
        holder.caseTextDisNumber.text = aarrayList.timeline.cases.toList().get(position).second.toString()
        holder.deathsTextDisNumber.text =aarrayList.timeline.deaths.toList().get(position).second.toString()
        holder.recoveredTextDisNumber.text =
            aarrayList.timeline.recovered.toList().get(position).second.toString()

    }

    override fun getItemCount(): Int {
        return aarrayList.timeline.cases.toList().size
    }


}