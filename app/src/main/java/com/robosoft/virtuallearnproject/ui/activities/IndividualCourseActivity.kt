package com.robosoft.virtuallearnproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.CourseOverviewAdapter
import com.robosoft.virtuallearnproject.databinding.ActivityIndividualCourseBinding
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.dataclass.course.EnrollRequest
import com.robosoft.virtuallearnproject.network.course.CourseApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class IndividualCourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIndividualCourseBinding
    private lateinit var courseId: String
    private lateinit var courseTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIndividualCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseApiService = CourseApiService()
        val accessToken = SharedPreferenceManager(applicationContext).getAccessToken()

        val intent = intent
        val courseCategory = intent.getStringExtra("courseCategory")
        val courseChapters = intent.getIntExtra("courseChaptersCount", 0)
        val lessonCount = intent.getIntExtra("lessonCount", 0)
        courseId = intent.getStringExtra("courseId")!!
        courseTitle = intent.getStringExtra("courseName") !!

        binding.courseHeaderLayout.courseTitle.text = courseTitle
        binding.courseHeaderLayout.tvCategory.text = courseCategory
        val lessonChapterCount = courseChapters.toString() + " Chapters | " + lessonCount.toString() + " Lessons"
        binding.courseHeaderLayout.chapterLessonCount.text = lessonChapterCount

        val viewPagerAdapter = CourseOverviewAdapter(supportFragmentManager, lifecycle, courseId, courseTitle)
        binding.courseViewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.courseTabLayout, binding.courseViewpager) { tab, postion ->
            if (postion == 0) {
                tab.text = "Overview"
            } else {
                tab.text = "Chapters"
            }
        }.attach()

        binding.courseHeaderLayout.closeBtn.setOnClickListener {
            onBackPressed()
        }

        CoroutineScope(Dispatchers.IO).async {
            val tempRequest = CourseRequest(courseId, "overview")
            courseApiService.getCourseOverview(accessToken!!, tempRequest) {
                if(it!=null){
                    if(it.isEnrolled!=null){
                        binding.joincourseBtn.visibility = View.INVISIBLE

                        binding.courseHeaderLayout.courseTitle.text = courseTitle
                    }
                }
            }
        }


        val dataRequest = EnrollRequest(courseId)
        binding.joincourseBtn.setOnClickListener {
            courseApiService.enrollCourse(accessToken!!, dataRequest) {
                if (it != null) {
                    if(it.message == "Successfully Enrolled!"){
                        binding.joincourseBtn.visibility = View.INVISIBLE
                        Toast.makeText(this, "Successfully enrolled", Toast.LENGTH_SHORT).show()
                        recreate()
                    }
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResuming activity")

    }

}