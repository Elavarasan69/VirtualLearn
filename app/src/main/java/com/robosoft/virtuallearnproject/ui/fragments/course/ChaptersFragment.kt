package com.robosoft.virtuallearnproject.ui.fragments.course

import android.content.Context
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.ChapterExpandListAdapter2
import com.robosoft.virtuallearnproject.databinding.FragmentChaptersBinding
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.network.course.CourseApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChaptersFragment : Fragment() {

    lateinit var binding : FragmentChaptersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChaptersBinding.inflate(inflater,container,false)

        //call the api and get response here
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        val courseId = bundle?.getString("courseId")
        val courseTitle = bundle?.getString("courseTitle")
        updateUi(courseId!!,courseTitle!!)
        Log.d("onresumeFragment", "OnResume Fragement")
        binding.root.requestLayout()
    }

    fun getAccessToken() : String?{
        val sharedPreferences = context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences?.getString("accessToken", "")
        return accessToken
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if(isVisibleToUser){
//            requireActivity()?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
//        }
//    }

    fun updateUi(courseId : String,courseTitle: String){
        val apiService = CourseApiService()
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()

        val sendingData = CourseRequest(courseId!!, "chapters")
        GlobalScope.launch {
            apiService.getCourseDetails(accessToken!!,sendingData){
                if(it!=null){
                    Log.d("response", it.toString())
                    binding.chapterExpandableList.setAdapter(ChapterExpandListAdapter2(courseTitle,it,activity?.applicationContext!! ))
                    ViewCompat.setNestedScrollingEnabled(binding.chapterExpandableList, true)
                    val course = it.listOfChapters
                    binding.courseDescShort.text = getString(R.string.chapter_details, course?.courseContent?.totalChapters, course?.courseContent?.totalLessons, DateUtils.formatElapsedTime(course?.courseContent?.totalDuration?.toLong()!!))
                }
                else{
                    Log.d("response", "something went wrong")
                }
            }
        }
    }



}