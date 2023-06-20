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
import com.example.task.R
import com.example.task.DiscriptionScreen
import com.example.task.model.AllCountryWise
import com.example.task.model.Matchsummary

class RecyclerViewAdapter(
    var context: Context,
    var aarrayList: AllCountryWise
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var name: TextView = itemView.findViewById(R.id.countryName)
        var countryImg: ImageView = itemView.findViewById(R.id.countryImg)
        var caseNumberTile: TextView = itemView.findViewById(R.id.caseNumberTile)
        var deathNumberTile: TextView = itemView.findViewById(R.id.deathNumberTile)
        var recoveredNumberTile: TextView = itemView.findViewById(R.id.recoveredNumberTile)
        var activeText: TextView = itemView.findViewById(R.id.activeText)
        var cardTile: CardView = itemView.findViewById(R.id.cardTile)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        var view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.country_tile, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return aarrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.name.text = aarrayList.get(position).country
        holder.caseNumberTile.text = "CASES: " + aarrayList.get(position).cases.toString()
        holder.deathNumberTile.text =
            "DEATHS: " + aarrayList.get(position).deaths.toString() + "[+${aarrayList.get(position).todayDeaths.toString()}]"
        holder.recoveredNumberTile.text =
            "RECOVERED: " + aarrayList.get(position).recovered.toString() + "[+${
                aarrayList.get(position).todayRecovered.toString()
            }]"
        holder.activeText.text = "ACTIVE: " + aarrayList.get(position).active.toString()


        if (aarrayList.get(position).countryInfo.flag == null) {
            holder.countryImg.setImageResource(R.drawable.image)
        } else {
            Glide.with(holder.itemView).load(aarrayList.get(position).countryInfo.flag)
                .placeholder(R.drawable.image_load).centerCrop()
                .into(holder.countryImg)
        }



        holder.cardTile.setOnClickListener {
            val intent = Intent(context, DiscriptionScreen::class.java)
//
            intent.putExtra("info", aarrayList.get(position).countryInfo.iso2)
            intent.putExtra("name", aarrayList.get(position).country)
            context.startActivity(intent)
            Log.d("Click", "You Click $position")
               }
    }
}