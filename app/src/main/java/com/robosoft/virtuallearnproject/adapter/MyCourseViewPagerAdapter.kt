package com.robosoft.virtuallearnproject.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.CompletedFragment
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.MyCourseFragment
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.OngoingFragment

class MyCourseViewPagerAdapter (myCourseFragment: MyCourseFragment) : FragmentStateAdapter(myCourseFragment){
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> OngoingFragment()
            1 -> CompletedFragment()
            else -> throw Resources.NotFoundException("Position Not Found")
        }
    }
}