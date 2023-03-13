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
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.FeaturedCourseDataClass
import com.robosoft.virtuallearnproject.dataclass.home.searchbycategory.SearchByCategoryAndKeywordsRequest
import com.robosoft.virtuallearnproject.dataclass.home.searchbycategory.SearchByCategoryKeywordResponseItem
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso

class FeaturedCourseRecyclerAdapter (val context: Context, private val data: List<SearchByCategoryKeywordResponseItem>) : RecyclerView.Adapter<FeaturedCourseRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.featured_courses_item_list, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = data[position]

        holder.title.text = course.courseTitle
        holder.chapters.text = course.courseContent?.totalChapters.toString() + " Chapters"
        val duration = DateUtils.formatElapsedTime(course.courseContent?.totalDuration?.toLong()!!)
        holder.duration.text = context.getString(R.string.duration,duration)
        if(course.courseImage!=null) {
            val courseImg = course.courseImage.replaceRange(4,4,"s")
            val imgUrl = Uri.parse(courseImg)
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
        val imageView: ImageView = itemView.findViewById(R.id.featuredcourse_iv)
        val title : TextView = itemView.findViewById(R.id.tv_course_title)
        val chapters : TextView = itemView.findViewById(R.id.tv_chaptercount)
        val duration : TextView = itemView.findViewById(R.id.tv_course_duration)

    }
}