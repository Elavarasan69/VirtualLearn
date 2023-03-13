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
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterResponse
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponseItem
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso

class ApplyFilterAdapter (val context: Context, private val data: ApplyFilterResponse) : RecyclerView.Adapter<ApplyFilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_suggestion_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val course = data?.get(position)
        holder.courseTitle.text = course?.title
        holder.chapterCount.text = course?.totalChapters.toString() + " Chapters"
        holder.category.text = course?.category.toString()
        if(course?.image!=null){
            val url = course?.image.replaceRange(4,4,"s")
            val imgUrl = Uri.parse(url)
            Picasso.get().load(imgUrl).into(holder.imageView)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(
                context.applicationContext,
                IndividualCourseActivity::class.java
            )
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("courseId", course?.courseId)
            intent.putExtra("courseName", course?.title)
            intent.putExtra("courseCategory", course?.category)
            intent.putExtra("courseChaptersCount", course?.totalChapters)
            intent.putExtra("lessonCount", course?.totalLessons)
            context.startActivity(
                intent
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.course_iv)
        val courseTitle: TextView = itemView.findViewById(R.id.coursetitle_tv)
        val chapterCount: TextView = itemView.findViewById(R.id.numberofchapters_tv)
        val category: TextView = itemView.findViewById(R.id.category)
    }
}