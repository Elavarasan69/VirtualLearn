package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.squareup.picasso.Picasso

class CompletedAdapter (val data: com.robosoft.virtuallearnproject.dataclass.MyCourse.CompletedData):
    RecyclerView.Adapter<CompletedAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.completed_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val completedCourse = data[position]

        val videoUrl = completedCourse.image
        val uri = Uri.parse(videoUrl)
        holder.courseTitle.text = completedCourse.title
        holder.approvalRate.text = completedCourse.approvalRate
        holder.backGroundImage.let {
            if(videoUrl!=null){
                val uri = Uri.parse(videoUrl)
                Picasso.get().load(uri).into(it)
            }
        }
        holder.viewCertificate.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val courseTitle: TextView = itemView.findViewById(R.id.course_name_tv)
        val approvalRate: TextView = itemView.findViewById(R.id.percentage_tv)
        val backGroundImage: ImageView = itemView.findViewById(R.id.background_img)
        val viewCertificate: Button = itemView.findViewById(R.id.view_certificate_btn)
    }
}