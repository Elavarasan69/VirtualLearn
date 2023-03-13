package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.WelcomeDataClass

class WelcomeViewPagerAdapter(val data : List<WelcomeDataClass>, val context: Context) : RecyclerView.Adapter<WelcomeViewPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.img_holder)
        val title = itemView.findViewById<TextView>(R.id.title_holder)
        val description = itemView.findViewById<TextView>(R.id.description_holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource( data[position].img_id)
        holder.title.text = data[position].title
        holder.description.text = data[position].description
    }

    override fun getItemCount(): Int {
        return data.size
    }
}