package com.robosoft.virtuallearnproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.CourseToGetStartDataClass

class CourseToGetStartRecyclerAdapter(private val mList: List<CourseToGetStartDataClass>) : RecyclerView.Adapter<CourseToGetStartRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.courses_to_get_start_item_list, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CourseToGetStartDataClass = mList[position]
        holder.imageView.setImageResource(CourseToGetStartDataClass.image)
        // holder.textView.text = ChoiceYourCourseDataClass.text

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.coursetogetstart_iv)
        //val textView: TextView = itemView.findViewById(R.id.coursetitle_tv)
    }
}