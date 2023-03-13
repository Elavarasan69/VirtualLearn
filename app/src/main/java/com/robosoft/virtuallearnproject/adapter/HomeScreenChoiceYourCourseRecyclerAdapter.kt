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
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso

class HomeScreenChoiceYourCourseRecyclerAdapter(val context : Context, private val data: ChoiceCourseResponse) :
    RecyclerView.Adapter<HomeScreenChoiceYourCourseRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.homescreen_choiceyourcourse_video_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = data[position]

        holder.title.text = course.courseTitle
        holder.chapters.text = course.courseContent?.totalChapters.toString() + " Chapters"
        holder.category.text = course.category
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
        val imageView: ImageView = itemView.findViewById(R.id.choiceyourcoursevideoimage_iv)
        val title: TextView = itemView.findViewById(R.id.course_title)
        val chapters: TextView = itemView.findViewById(R.id.course_chapter)
        val category : TextView = itemView.findViewById(R.id.tv_category)

    }
}