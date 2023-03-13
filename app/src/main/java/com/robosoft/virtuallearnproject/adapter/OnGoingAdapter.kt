package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.MyCourse.OngoingResponseData
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso


class OnGoingAdapter(val context: Context, val data: OngoingResponseData) :
    RecyclerView.Adapter<OnGoingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ongoing_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ongoingCourse = data[position]

        val videoUrl = ongoingCourse.url?.replaceRange(4, 4, "s")
        holder.courseTitle.text = ongoingCourse.title
        holder.chapterCompleted.text = ongoingCourse.chaptersCompleted.toString()
        holder.totalChapters.text = ongoingCourse.totalChapters.toString()
        holder.backGroundImage.let {
            if (videoUrl != null) {
                val uri = Uri.parse(videoUrl)
                Picasso.get().load(uri).into(it)
            }
        }
        holder.continueCourse.setOnClickListener {
            val intent = Intent(context, IndividualCourseActivity::class.java)
            intent.putExtra("courseId", ongoingCourse.courseId)
            intent.putExtra("courseName", ongoingCourse.title)
            intent.putExtra("courseChaptersCount", ongoingCourse.totalChapters)
            intent.putExtra("courseCategory", ongoingCourse.category)
            intent.putExtra("lessonCount", ongoingCourse.totalLessons)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val courseTitle: TextView = itemView.findViewById(R.id.course_name_tv)
        val chapterCompleted: TextView = itemView.findViewById(R.id.chaptersCompleted_tv)
        val totalChapters: TextView = itemView.findViewById(R.id.totalChapters_tv)
        val backGroundImage: ImageView = itemView.findViewById(R.id.background_img)
        val continueCourse: Button = itemView.findViewById(R.id.continue_btn)
    }
}