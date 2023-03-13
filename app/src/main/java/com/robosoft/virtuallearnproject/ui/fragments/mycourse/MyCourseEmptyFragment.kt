package com.robosoft.virtuallearnproject.ui.fragments.mycourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.robosoft.virtuallearnproject.NotificationFragment
import com.robosoft.virtuallearnproject.ui.fragments.profile.MyProfileFragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentMyCourseWelcomeBinding
import com.robosoft.virtuallearnproject.ui.fragments.categories.IndividualCategorieOnClickDetailScreenFragment
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.NotificationsFragment
import com.robosoft.virtuallearnproject.ui.fragments.settings.SettingsFragment
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment

class MyCourseEmptyFragment : Fragment() {

    private lateinit var myCourseEmptyFragmentBinding: FragmentMyCourseWelcomeBinding
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myCourseEmptyFragmentBinding = FragmentMyCourseWelcomeBinding.inflate(inflater,container,false)


        val drawerLayout: DrawerLayout = myCourseEmptyFragmentBinding.drawerLayout
        val navView: NavigationView = myCourseEmptyFragmentBinding.navView

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_my_course ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, MyCourseEmptyFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_profile ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_notifications ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, NotificationFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_settings ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, SettingsFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_logout ->activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
                    ?.addToBackStack(null)
                    ?.commit()
            }
            true
        }

        myCourseEmptyFragmentBinding.category1.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category2.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category3.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category4.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category5.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category6.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category7.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category8.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category9.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category10.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        myCourseEmptyFragmentBinding.category11.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, IndividualCategorieOnClickDetailScreenFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        return myCourseEmptyFragmentBinding.root
    }
}
