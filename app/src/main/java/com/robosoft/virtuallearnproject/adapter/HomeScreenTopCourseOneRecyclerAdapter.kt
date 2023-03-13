package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseResponse
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso

class HomeScreenTopCourseOneRecyclerAdapter(
    val context: Context,
    private val data: TopCourseResponse
) : RecyclerView.Adapter<HomeScreenTopCourseOneRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.homescreen_topcourse_one, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val course = data[position]
        holder.courseTitle.text = course.courseTitle
        holder.courseChapters.text = course.courseContent?.totalChapters.toString() + " Chapters"
        holder.courseDuration.text = DateUtils.formatElapsedTime(course.courseContent?.totalDuration?.toLong()!!)
        if (course.courseImage != null) {
//            Glide.with(context).load(imgUrl).into(holder.imageView)
//            Picasso.with(context).load(imgUrl).into(holder.imageView)
            val courseImage = course.courseImage.replaceRange(4,4,"s")
            val imgUrl = Uri.parse(courseImage)
            Picasso.get().load(imgUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(
                context.applicationContext,
                IndividualCourseActivity::class.java
            )
            intent.putExtra("courseId", course._id)
            intent.putExtra("courseName", course.courseTitle)
            intent.putExtra("courseCategory", course.category)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("courseChaptersCount", course.courseContent?.totalChapters)
            intent.putExtra("lessonCount", course.courseContent?.totalLessons)
            context.startActivity(
                intent
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.coursetogetstart_iv)
        val courseTitle: TextView = itemView.findViewById(R.id.course_title)
        val courseChapters: TextView = itemView.findViewById(R.id.course_chapter)
        val courseDuration: TextView = itemView.findViewById(R.id.course_duration)
    }
}