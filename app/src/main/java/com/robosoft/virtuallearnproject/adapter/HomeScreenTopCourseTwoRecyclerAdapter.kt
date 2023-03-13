package com.robosoft.virtuallearnproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.HomeScreenTopCourseTwoDataClass

class HomeScreenTopCourseTwoRecyclerAdapter (private val mList: List<HomeScreenTopCourseTwoDataClass>) : RecyclerView.Adapter<HomeScreenTopCourseTwoRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.homescreen_topcourse_two, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val TopCourseTwoDataClass = mList[position]
        holder.imageView.setImageResource(TopCourseTwoDataClass.image)
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