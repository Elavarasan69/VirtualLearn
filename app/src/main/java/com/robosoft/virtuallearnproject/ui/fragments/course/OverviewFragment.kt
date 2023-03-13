package com.robosoft.virtuallearnproject.ui.fragments.course

import android.net.Uri
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
import com.robosoft.virtuallearnproject.databinding.FragmentOverviewBinding
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.network.course.CourseApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_my_profile.view.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OverviewFragment : Fragment() {

    lateinit var binding : FragmentOverviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater,container,false)

        val bundle = arguments
        val courseId = bundle?.getString("courseId")
        val courseTitle = bundle?.getString("courseTitle")

//        binding.instructorBackground.setShowingLine(2)
//        binding.instructorBackground.addShowMoreText("SHOW MORE")
//        binding.instructorBackground.addShowLessText("SHOW LESS")


//        binding.courseDescription.setShowingLine(6)

        val apiService = CourseApiService()
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()!!
        val data= CourseRequest(courseId!!, "overview")
        Log.d("course ID",courseId.toString())
        GlobalScope.launch {
            apiService.getCourseOverview(accessToken, data) {

                val overview = it?.courseOverview?.overViewId

                if (it != null) {
                    binding.courseSubDescription.text = overview?.description
                    binding.previewTitle.text = overview?.previewThisCourse?.description
                    binding.previewTime.text = getString(R.string.duration, DateUtils.formatElapsedTime(overview?.previewThisCourse?.videoDuration?.toLong()!!))

                    binding.courseDescription.setContent(overview?.description)
                    binding.courseDescription.setTextMaxLength(200)
                    binding.courseDescription.setSeeMoreTextColor(R.color.orange)
                    binding.courseDescription.setSeeMoreText("SHOW MORE","SHOW LESS")

                    binding.instructorName.text = overview?.instructor?.name
                    if(binding.instructorImage!=null) {
                        val instructorImg = binding.instructorImage
                        val imageUrlModification = overview?.instructor?.instructorImage?.replaceRange(4,4,"s")
                        val imgUrl = Uri.parse(imageUrlModification)
                        Picasso.get().load(imgUrl).into(instructorImg)
                    }
                    binding.courseincludeOneTv.text = overview?.courseIncludes?.get(0)
                    binding.courseincludeTwoTv.text = overview?.courseIncludes?.get(1)
                    binding.courseincludeThreeTv.text = overview?.courseIncludes?.get(2)
                    binding.courseincludeFourTv.text = overview?.courseIncludes?.get(3)
                    binding.whatyouwilllearnOneTv.text = overview?.whatYouWillLearn?.get(0)
                    binding.whatyouwilllearnTwoTv.text = overview?.whatYouWillLearn?.get(1)
                    if(overview?.whatYouWillLearn?.size!! >= 3) {
                        binding.whatyouwilllearnThreeTv.text = overview?.whatYouWillLearn?.get(2)
                    }
                    if(overview?.whatYouWillLearn?.size!! >= 4) {
                        binding.whatyouwilllearnFourTv.text = overview?.whatYouWillLearn?.get(3)
                    }

                    binding.requirmentOneTv.text = overview?.requirements?.get(0)
                    binding.requirmentTwoTv.text = overview?.requirements?.get(1)
                    binding.requirmentThreeTv.text = overview?.requirements?.get(2)
                    binding.instructorDesignation.text = overview?.instructor?.designation
                    binding.instructorDescription.setContent(overview?.instructor?.description)
                    binding.instructorDescription.setTextMaxLength(200)
                    binding.instructorDescription.setSeeMoreTextColor(R.color.orange)
                    binding.instructorDescription.setSeeMoreText("SHOW MORE", "SHOW LESS")
                }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}