package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.banners.BannerResponse
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso

class BannerAdapter(val context: Context, private val data: BannerResponse) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val advertisementImage: ImageView = itemView.findViewById(R.id.courseIV)
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseImage: ImageView = itemView.findViewById(R.id.course_image)
        val courseTitle = itemView.findViewById<TextView>(R.id.course_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_advertisement, parent, false)
            return AdvertisementViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_course, parent, false)
            return CourseViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if(item._id == null){
            holder as AdvertisementViewHolder
            val url = item.BannerImage?.replaceRange(4,4,"s")
            val imgUri = Uri.parse(url)
            Picasso.get().load(imgUri).into(holder.advertisementImage)
        }
        else {
            holder as CourseViewHolder
            holder.courseTitle.text = item.courseTitle
            val url = item.courseImage?.replaceRange(4,4,"s")
            val imgUri = Uri.parse(url)
            Picasso.get().load(imgUri).into(holder.courseImage)

            holder.itemView.setOnClickListener {
                val intent = Intent(
                    context,
                    IndividualCourseActivity::class.java
                )
                intent.putExtra("courseId", item._id)
                intent.putExtra("courseName", item.courseTitle)
                intent.putExtra("courseCategory", item.category)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("courseChaptersCount", item.courseContent?.totalChapters)
                intent.putExtra("lessonCount", item.courseContent?.totalLessons)
                context.startActivity(
                    intent
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        if(item._id == null) {
            return 0
        } else {
            return 1
        }
    }
}