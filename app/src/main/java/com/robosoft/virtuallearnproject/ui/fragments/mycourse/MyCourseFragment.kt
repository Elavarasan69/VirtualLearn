package com.robosoft.virtuallearnproject.ui.fragments.mycourse

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.robosoft.virtuallearnproject.NotificationFragment
import com.robosoft.virtuallearnproject.ui.fragments.profile.MyProfileFragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.MyCourseViewPagerAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentMyCourseBinding
import com.robosoft.virtuallearnproject.dataclass.home.FullNameResponse
import com.robosoft.virtuallearnproject.network.home.HomeApi
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import com.robosoft.virtuallearnproject.ui.activities.LoginRegisterContainerActivity
import com.robosoft.virtuallearnproject.ui.fragments.Search.HomeToSearchScreenFragment

import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.NotificationsFragment
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.PasswordChangeSuccessFragment
import com.robosoft.virtuallearnproject.ui.fragments.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_home_screen_main.*
import kotlinx.android.synthetic.main.fragment_my_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class MyCourseFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myCourseFragmentBinding: FragmentMyCourseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        myCourseFragmentBinding = FragmentMyCourseBinding.inflate(inflater, container, false)

        //        myCourseFragmentBinding.menuToolbarInclude.searchIv.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()
//
//                ?.replace(
//                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
//                    HomeToSearchScreenFragment()
//                )
//                ?.addToBackStack(null)
//                ?.commit()
//        }
        //ViewPager

        myCourseFragmentBinding.toolbar.searchIv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    HomeToSearchScreenFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }


        myCourseFragmentBinding.root.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.supportFragmentManager?.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                true
            } else {
                false
            }
        }


        tabLayout = myCourseFragmentBinding.courseTabLayout
        viewPager2 = myCourseFragmentBinding.courseViewPager
        viewPager2.isUserInputEnabled = false
        viewPager2.adapter = MyCourseViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> "Ongoing"
                1 -> "Completed"
                else -> throw Resources.NotFoundException("Position Not Found")
            }
        }.attach()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val drawerLayout: DrawerLayout = myCourseFragmentBinding.drawerLayout
        val navView: NavigationView = myCourseFragmentBinding.navView

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        myCourseFragmentBinding.toolbar.hambergerIv.setOnClickListener{
            drawerLayout.open()
            if (isAdded == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    getUserName()
                }
            }
        }


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_my_course -> drawerLayout.close()
//                    activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.homescreenfragment_container, MyCourseFragment())
//                    ?.addToBackStack(null)
//                    ?.commit()
                R.id.nav_profile -> {
                    activity?.supportFragmentManager?.popBackStack()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
                        ?.addToBackStack(null)
                        ?.commit()
                }
                R.id.nav_notifications -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, NotificationFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_settings -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, SettingsFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_logout -> {
                    val builder = AlertDialog.Builder(requireActivity(),R.style.CustomAlertDialog)
                        .create()
                    val view = layoutInflater.inflate(R.layout.custom_alertbox_logout,null)
                    val  logoutButton = view.findViewById<Button>(R.id.logout_tv)
                    val  cancelButton = view.findViewById<Button>(R.id.cancel_tv)
                    builder.setView(view)

                    cancelButton.setOnClickListener {
                        builder.dismiss()
                    }
                    logoutButton.setOnClickListener {
                        logout()
                    }
                    builder.setCanceledOnTouchOutside(false)
                    builder.show()
                }
            }
            true
        }
        return myCourseFragmentBinding.root
    }

    fun logout(){
        val intent = Intent(activity, LoginRegisterContainerActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK )
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity?.startActivity(intent)
        activity?.finishAffinity()
        activity?.finish()
    }

    suspend private fun getUserName() {
        val apiService = ServiceBuilderObject.buildService(HomeApi::class.java)
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        apiService.getName(accessToken!!).enqueue(object : Callback<FullNameResponse> {
            override fun onResponse(
                call: retrofit2.Call<FullNameResponse>,
                response: Response<FullNameResponse>
            ) {
                if(response.body()!=null){
                    val navUserName  = view?.findViewById<TextView>(R.id.nav_user_name)
                    if (navUserName != null) {
                        navUserName.text = response.body()!!.fullName
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<FullNameResponse>, t: Throwable) {

            }

        })
    }
}