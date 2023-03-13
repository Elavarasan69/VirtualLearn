package com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.robosoft.virtuallearnproject.NotificationFragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.BannerAdapter
import com.robosoft.virtuallearnproject.adapter.HomeScreenChoiceYourCourseRecyclerAdapter
import com.robosoft.virtuallearnproject.adapter.HomeScreenTopCourseOneRecyclerAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentHomeScreenMainBinding
import com.robosoft.virtuallearnproject.dataclass.home.FullNameResponse
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseResponse
import com.robosoft.virtuallearnproject.network.home.HomeApi
import com.robosoft.virtuallearnproject.network.home.HomeApiService
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import com.robosoft.virtuallearnproject.ui.activities.LoginRegisterContainerActivity
import com.robosoft.virtuallearnproject.ui.fragments.Search.HomeToSearchScreenFragment
import com.robosoft.virtuallearnproject.ui.fragments.categories.CategoriesFragment
import com.robosoft.virtuallearnproject.ui.fragments.categories.IndividualCategorieOnClickDetailScreenFragment
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.ChoiceYourCourseFragment
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.MyCourseFragment
import com.robosoft.virtuallearnproject.ui.fragments.profile.MyProfileFragment
import com.robosoft.virtuallearnproject.ui.fragments.settings.SettingsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response


class HomeScreenMainFragment : Fragment() {
    private lateinit var homeScreenmainBinding: FragmentHomeScreenMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeScreenmainBinding = FragmentHomeScreenMainBinding.inflate(inflater, container, false)

        val drawerLayout: DrawerLayout = homeScreenmainBinding.drawerLayout
        val navView: NavigationView = homeScreenmainBinding.navView

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        homeScreenmainBinding.toolbar.getChildAt(1).setOnClickListener {
            drawerLayout.open()
        }
        //   navView.findViewById<TextView>(R.id.user_name).text = "abc"

        homeScreenmainBinding.root.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.supportFragmentManager?.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val intent = Intent(activity, LoginRegisterContainerActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                activity?.startActivity(intent)
                activity?.finishAffinity()
                activity?.finish()
                true
            } else {
                false
            }
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> drawerLayout.close()
//                    activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
//                    ?.addToBackStack(null)
//                    ?.commit()
                R.id.nav_my_course -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, MyCourseFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_profile -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_notifications -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, NotificationFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_settings -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, SettingsFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_logout -> {

                    val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
                        .create()
                    val view = layoutInflater.inflate(R.layout.custom_alertbox_logout, null)
                    val logoutButton = view.findViewById<Button>(R.id.logout_tv)
                    val cancelButton = view.findViewById<Button>(R.id.cancel_tv)
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

            drawerLayout.close()

            true
        }

        homeScreenmainBinding.homesearchBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    HomeToSearchScreenFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        homeScreenmainBinding.categoriesSeeAllTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    CategoriesFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        homeScreenmainBinding.choiceyourcourseSeeAllTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    ChoiceYourCourseFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        homeScreenmainBinding.topcourrseoneSeeAllTv.setOnClickListener {
            startIndividualCategory("Music")
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(
//                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
//                    ChoiceYourCourseFragment()
//                )
//                ?.addToBackStack(null)
//                ?.commit()
        }

        homeScreenmainBinding.topcourrsetwoSeeAllTv.setOnClickListener {
            startIndividualCategory("Web Development")
        }
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(
//                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
//                    ChoiceYourCourseFragment()
//                )
//                ?.addToBackStack(null)
//                ?.commit()
//        }


        homeScreenmainBinding.tvNewest.setOnClickListener {
            homeScreenmainBinding.tvNewest.setTextColor(resources.getColor(R.color.onclick_all_new_popular))
            homeScreenmainBinding.textAll.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.tvPopular.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.tvNewest.setBackgroundResource(R.drawable.custom_all_popular_newest_background)
            homeScreenmainBinding.textAll.background = null
            homeScreenmainBinding.tvPopular.background = null

            if (isAdded == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    getchoiceCourse("Newest", "")
                }
            }
        }

        homeScreenmainBinding.tvPopular.setOnClickListener {
            homeScreenmainBinding.tvPopular.setTextColor(resources.getColor(R.color.onclick_all_new_popular))
            homeScreenmainBinding.textAll.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.tvNewest.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.tvPopular.setBackgroundResource(R.drawable.custom_all_popular_newest_background)
            homeScreenmainBinding.textAll.background = null
            homeScreenmainBinding.tvNewest.background = null
            if (isAdded == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    getchoiceCourse("Popular", "")
                }
            }
        }

        homeScreenmainBinding.textAll.setOnClickListener {
            homeScreenmainBinding.textAll.setTextColor(resources.getColor(R.color.onclick_all_new_popular))
            homeScreenmainBinding.tvPopular.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.tvNewest.setTextColor(resources.getColor(R.color.all_new_popular))
            homeScreenmainBinding.textAll.setBackgroundResource(R.drawable.custom_all_popular_newest_background)
            homeScreenmainBinding.tvPopular.background = null
            homeScreenmainBinding.tvNewest.background = null
            if (isAdded == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    getchoiceCourse("All", "")
                }
            }
        }

        homeScreenmainBinding.designLinearlayout.setOnClickListener {
            startIndividualCategory("Design")
        }
        homeScreenmainBinding.businessLinearlayout.setOnClickListener {
            startIndividualCategory("Business")
        }
        homeScreenmainBinding.healthFitnessLinearlayout.setOnClickListener {
            startIndividualCategory("Health & Fitness")
        }
        homeScreenmainBinding.musicLinearlayout.setOnClickListener {
            startIndividualCategory("Music")
        }
        homeScreenmainBinding.marketingLinearlayout.setOnClickListener {
            startIndividualCategory("Marketing")
        }
        homeScreenmainBinding.itSoftwareLinearlayout.setOnClickListener {
            startIndividualCategory("IT & Software")
        }
        homeScreenmainBinding.teachingLinearlayout.setOnClickListener {
            startIndividualCategory("Teaching")
        }
        homeScreenmainBinding.webdevelopmentLinearlayout.setOnClickListener {
            startIndividualCategory("Web Development")
        }

        if (isAdded == true) {
            CoroutineScope(Dispatchers.IO).launch {
                getHomeScreenData()
            }
        }

        return homeScreenmainBinding.root
    }

    private fun startIndividualCategory(category: String) {
        val bundle = Bundle()
        bundle.putString("category", category)

        val individualCategory = IndividualCategorieOnClickDetailScreenFragment()
        individualCategory.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.replace(
            com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
            individualCategory
        )
            ?.addToBackStack(null)
            ?.commit()
    }

    private suspend fun getHomeScreenData() {
        CoroutineScope(Dispatchers.IO).launch {
            async { getUserName() }
            var choiceTask = async { getchoiceCourse("All", "") }
            var topcourseTask1 = async { getTopCourseInMusic("") }
            var topcourseTask2 = async { getTopCourseInWebdev("") }
            var suggestionTaks = async { getSuggestedCourse() }
            async { getBanners() }

        }
    }

    private suspend fun getBanners() {
        val apiService = HomeApiService()
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        apiService.getBanners(accessToken!!) {
            if (it != null) {
                if (isAdded) {
                    homeScreenmainBinding.suggestionsRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    homeScreenmainBinding.suggestionsRecyclerview.adapter =
                        BannerAdapter(activity?.applicationContext!!, it)
                }
            }
        }
    }

    private suspend fun getUserName() {
        val apiService = ServiceBuilderObject.buildService(HomeApi::class.java)
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        apiService.getName(accessToken!!).enqueue(object : Callback<FullNameResponse> {
            override fun onResponse(
                call: retrofit2.Call<FullNameResponse>,
                response: Response<FullNameResponse>
            ) {
                if (response.body() != null) {
                    homeScreenmainBinding.usernameTv.text = response.body()!!.fullName
                    val navUserName = view?.findViewById<TextView>(R.id.nav_user_name)
                    if (navUserName != null) {
                        navUserName.text = response.body()!!.fullName
                    }
                    // val navProfilePhoto = view?.findViewById<ImageView>(R.id.profilephotoofnavigation_iv)
//                    if(navProfilePhoto !=null) {
//                        val instructorImg = navProfilePhoto
//                      //  val imageUrlModification = it?.profileImage?.replaceRange(4,4,"s")
//                        val imgUrl = Uri.parse(imageUrlModification)
//                        Picasso.get().load(imgUrl).into(instructorImg)
//                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<FullNameResponse>, t: Throwable) {

            }

        })
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (this.toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

    private suspend fun getSuggestedCourse() {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val suggestRequest = ChoiceCourseRequest("All", "")
        val homeService = HomeApiService()
        homeService.choiceCourse(accessToken!!, suggestRequest) {
            if (it != null) {
                Log.d("msg", it.toString())

                if (isAdded) {
                    homeScreenmainBinding.suggestionsRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    homeScreenmainBinding.suggestionsRecyclerview.adapter =
//                        CourseAdapter(activity?.applicationContext!!, it)
                }

            } else {
                Log.d("msg", "something went wrong1")
            }
        }

    }

    suspend fun getTopCourseInMusic(choice: String?): TopCourseResponse? {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val topRequestData = TopCourseRequest(choice, "Music")
        val homeService = HomeApiService()
        var response: TopCourseResponse? = null
        homeService.topCourseInCategory(accessToken!!, topRequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                response = it
                if (isAdded) {
                    homeScreenmainBinding.topcourseoneRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    homeScreenmainBinding.topcourseoneRecyclerview.adapter =
                        HomeScreenTopCourseOneRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }

            } else {
                Log.d("msg", "something went wrong1")
            }
        }
        return response
    }

    suspend fun getTopCourseInWebdev(choice: String?): TopCourseResponse? {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val topRequestData = TopCourseRequest(choice, "Web Development")
        val homeService = HomeApiService()
        var response: TopCourseResponse? = null
        homeService.topCourseInCategory(accessToken!!, topRequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                response = it
                if (isAdded) {
                    homeScreenmainBinding.topcoursetwoRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    homeScreenmainBinding.topcoursetwoRecyclerview.adapter =
                        HomeScreenTopCourseOneRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }
            } else {
                Log.d("msg", "something went wrong1")
            }
        }
        return response
    }

    suspend fun getchoiceCourse(choice: String, view: String?): String? {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val choicerequestData = ChoiceCourseRequest(choice, view)
        val homeService = HomeApiService()
        var response: ChoiceCourseResponse? = null
        homeService.choiceCourse(accessToken!!, choicerequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                response = it
                if (isAdded) {
                    homeScreenmainBinding.allcourseRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    homeScreenmainBinding.allcourseRecyclerview.adapter =
                        HomeScreenChoiceYourCourseRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }
            } else {
                Log.d("msg", "something went wrong")
            }
        }
        return "hi"
    }

    fun logout() {
        val intent = Intent(activity, LoginRegisterContainerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity?.startActivity(intent)
        activity?.finishAffinity()
        activity?.finish()
    }
}