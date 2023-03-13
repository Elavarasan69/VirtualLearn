package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.banners.BannerResponse
import com.squareup.picasso.Picasso

class CourseAdapter(val context: Context, private val data: BannerResponse) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseImageView: ImageView = itemView.findViewById(R.id.courseIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_advertisement, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = data[position]
//        if(item._id == null){
//            val imgUri = Uri.parse(item.BannerImage)
//            Picasso.get().load(imgUri).into(holder.courseImageView)
//        }
//        else {
//            val imgUri = Uri.parse(item.courseImage)
//            Picasso.get().load(imgUri).into(holder.courseImageView)
//        }
        if (course.courseImage != null) {
            val imgUri = Uri.parse(course.courseImage)
            Picasso.get().load(imgUri).into(holder.courseImageView)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}