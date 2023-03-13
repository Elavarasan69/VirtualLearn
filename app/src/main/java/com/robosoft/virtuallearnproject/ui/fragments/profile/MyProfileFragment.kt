package com.robosoft.virtuallearnproject.ui.fragments.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.robosoft.virtuallearnproject.ChangeCurrentPasswordFragment
import com.robosoft.virtuallearnproject.NotificationFragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.databinding.FragmentMyProfileBinding
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.FullNameResponse
import com.robosoft.virtuallearnproject.network.home.HomeApi
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject

import com.robosoft.virtuallearnproject.network.myprofile.ViewProfileApiService
import com.robosoft.virtuallearnproject.ui.activities.LoginRegisterContainerActivity
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.LoginRegisterChoiceFragment
import com.robosoft.virtuallearnproject.ui.fragments.mycourse.MyCourseFragment
import com.robosoft.virtuallearnproject.ui.fragments.settings.SettingsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home_screen_main.*
import kotlinx.android.synthetic.main.fragment_my_profile.view.*
import kotlinx.android.synthetic.main.viewpager_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : Fragment() {
    private lateinit var binding : FragmentMyProfileBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        val drawerLayout: DrawerLayout = binding.profileDrawerLayout
        val navView: NavigationView = binding.profileNavView

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.myProfileHamburgerIB.setOnClickListener {
            drawerLayout.open()
            if (isAdded == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    getUserName()
                }
            }
        }

        binding.root.setOnKeyListener { view, keyCode, keyEvent ->
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


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                R.id.nav_my_course -> {
                    activity?.supportFragmentManager?.popBackStack()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.homescreenfragment_container, MyCourseFragment())
                        ?.addToBackStack(null)
                        ?.commit()
                }
                R.id.nav_profile -> drawerLayout.close()
//                    activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
//                    ?.addToBackStack(null)
//                    ?.commit()
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
            true
        }

        binding.changePasswordCV?.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, ChangeCurrentPasswordFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        val fullName = binding.fullNameTv?.text.toString()
        val userName = binding.usernameTv?.text.toString()
        val email = binding.emailTv?.text.toString()
        val mobileNumber = binding.mblnoTv?.text.toString().toLong()
        val occupation = binding.occupationTv?.text.toString()
        val dateOfBirth = binding.dobTv?.text.toString()


        Log.d("entered","enter")

        val apiService = ViewProfileApiService()
        val access_token = SharedPreferenceManager(requireContext()).getAccessToken()!!

        Log.d("token",access_token)
        GlobalScope.launch {
            apiService.viewProfile(access_token){

            Log.d("response",it.toString())
                if (it!=null){
                    if(binding.profileImageIv!=null) {
                        val instructorImg = binding.profileImageIv
                        val imageUrlModification = it?.profileImage?.replaceRange(4,4,"s")
                        val imgUrl = Uri.parse(imageUrlModification)
                        Picasso.get().load(imgUrl).into(instructorImg)
                    }
                    var url = ""
                    if(binding.profilephotobackgroundIv!=null) {
                        val instructorImg = binding.profilephotobackgroundIv
                        val imageUrlModification = it?.profileImage?.replaceRange(4,4,"s")
                        val imgUrl = Uri.parse(imageUrlModification)
                        Picasso.get().load(imgUrl).into(instructorImg)
                        if (imageUrlModification != null) {
                            url = imageUrlModification
                        }
                    }

                    //Passing data

                    val name = it.name
                    val course = it.courses.toString()
                    val chapters = it.chapters.toString()
                    val tests = it.test.toString()
                    val fullname = it.name
                    val usrename = it.username
                    val email = it.email
                    val mblno = it.mobileNumber.toString()
                  val occupation = it.occupation.toString()
                   val dob = it.dateOfBirth.toString().substringBefore("T")
                    val twitter = it.twitterLink.toString()
                    val facebook = it.facebook.toString()
                    val gender = it.gender.toString()
                    val profilePhoto = it.profileImage.toString()
                    val navUserDesignation  = view?.findViewById<TextView>(R.id.nav_designation)


                    Log.d("dob",dob)
                    binding.profileNameTv.text = it.name
                    binding.profiledesignationTv?.text = it.occupation.toString()
                    binding.courses.text = it?.courses.toString()
                    //binding.courses.text = it?.courses.toString()
                    binding.chapters.text = it?.chapters.toString()
                    binding.testsTv.text = it?.test.toString()
                    binding.fullNameTv.text = it?.name
                    binding.usernameTv.text = it?.username
                    binding.emailTv.text = it?.email
                    binding.mblnoTv.text = it?.mobileNumber.toString()
                   binding.occupationTv.text = it?.occupation.toString()
                   binding.dobTv.text = dob
                    navUserDesignation?.text = it?.occupation.toString()



                    //binding.twitter.text = it?.twitter.toString()
                    //binding.facebook.text = it?.facebook.toString()

                    val bundle = Bundle()
                    bundle.putString("name",name)
                    bundle.putString("course",course)
                    bundle.putString("chapters",chapters)
                    bundle.putString("fullname",fullname)
                    bundle.putString("usrename",usrename)
                    bundle.putString("email",email)
                    bundle.putString("mblno",mblno)
                    bundle.putString("occupation",occupation)
                    bundle.putString("dob",dob)
                    bundle.putString("twitter",twitter)
                    bundle.putString("facebook",facebook)
                    bundle.putString("gender",gender)
                    bundle.putString("profilephoto", profilePhoto)


                    val editProfileFragment = EditProfileFragment()
                    editProfileFragment.arguments = bundle

                    binding.editProfileIV?.setOnClickListener {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(
                                R.id.homescreenfragment_container,
                                editProfileFragment
                            )
                            ?.addToBackStack(null)
                            ?.commit()
                    }

//                    val sharedPreferences = context?.getSharedPreferences("sharedPreference" , Context.MODE_PRIVATE)
//                    val editor = sharedPreferences?.edit()
//
//                    editor?.putString("profileImageIv",it.profileImage)
//                    editor?.putString("profileNameTv",it.name)
//                    editor?.putString("courses", it.courses.toString())
//                   // editor?.putString("courses", it.courses.toString())
//                    editor?.putString("chapters", it.chapters.toString())
//                    editor?.putString("testsTv", it.test.toString())
//                    editor?.putString("fullNameTv",it.name)
//                    editor?.putString("usernameTv",it.username)
//                    editor?.putString("emailTv",it.email)
//                    editor?.putString("mblnoTv", it.mobileNumber.toString())

//                    Log.d("profile response",it.toString())
                }else{
                    Toast.makeText(activity?.applicationContext,"something went wrong",Toast.LENGTH_SHORT).show()
                }
        }

//        val access_token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzdjNGI1MDBmZTFmMmU4N2RkYzc3MjAiLCJpYXQiOjE2NjkwOTAxMjgsImV4cCI6MTY2OTE3NjUyOH0.G4WFrUwHnJ7LMtZU3KegJhfi52MOt4HvnceNOh_3lfo"
       // val data = ViewProfileRequestData(dateOfBirth,email,fullName,mobileNumber,occupation,userName)
//        Log.d("data",data.toString())
//        apiService.editProfile(access_token,data){
//            if (it!=null){
//                Log.d("message",it.message)
//            }else{
//                Log.d("message", "something went wrong")
//            }
//        }


        }
        return binding.root
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (this.toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
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