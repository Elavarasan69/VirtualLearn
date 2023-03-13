package com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.HomeScreenChoiceYourCourseRecyclerAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentHomeScreenMainBinding
import com.robosoft.virtuallearnproject.databinding.FragmentProfileBinding
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.network.home.HomeApiService

class ProfileFragment : Fragment() {
    private lateinit var profileFragmentBinding : FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        profileFragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)



        return profileFragmentBinding.root

    }

    /*suspend fun getchoiceCourse(choice: String, view: String?): String? {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val choicerequestData = ChoiceCourseRequest(choice, view)
        val homeService = HomeApiService()
        var response: ChoiceCourseResponse? = null
        homeService.choiceCourse(accessToken!!, choicerequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                response = it
                profileFragmentBinding.personal_details_ll.=
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                profileFragmentBinding.allcourseRecyclerview.adapter = HomeScreenChoiceYourCourseRecyclerAdapter(it)
            } else {
                Log.d("msg", "something went wrong")
            }
        }
        return "hi"
    }
*/

}

