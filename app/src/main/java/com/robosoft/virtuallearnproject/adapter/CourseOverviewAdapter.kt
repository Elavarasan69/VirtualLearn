package com.robosoft.virtuallearnproject.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.robosoft.virtuallearnproject.ui.fragments.course.ChaptersFragment
import com.robosoft.virtuallearnproject.ui.fragments.course.OverviewFragment

class CourseOverviewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,val courseId : String,val courseTitle : String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("courseId", courseId)
        bundle.putString("courseTitle",courseTitle)
        return if (position == 0) {
            val overview = OverviewFragment()
            overview.arguments = bundle
            return overview
        } else {
           val chapters =  ChaptersFragment()
            chapters.arguments = bundle
            return chapters
        }
    }



    
}