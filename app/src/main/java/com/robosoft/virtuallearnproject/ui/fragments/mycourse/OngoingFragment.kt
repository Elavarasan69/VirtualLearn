package com.robosoft.virtuallearnproject.ui.fragments.mycourse

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.OnGoingAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentOngoingBinding
import com.robosoft.virtuallearnproject.dataclass.MyCourse.OngoingDataItem
import com.robosoft.virtuallearnproject.network.mycourse.MyCourseApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OngoingFragment : Fragment() {

    private lateinit var ongoingFragmentBinding: FragmentOngoingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        ongoingFragmentBinding = FragmentOngoingBinding.inflate(inflater,container,false)
        onGoingCourses()
        return ongoingFragmentBinding.root
    }

    override fun onResume() {
        onGoingCourses()
        super.onResume()
    }

    private fun onGoingCourses(){
        val myCourseApiOngoing = MyCourseApiService()
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        GlobalScope.launch {
            myCourseApiOngoing.onGoing(accessToken!!){
                if (it!=null){
                    Log.d("response",it.toString())
                    if(isAdded) {
                        ongoingFragmentBinding.ongoingRecyclerView.adapter =
                            OnGoingAdapter(activity?.applicationContext!!, it)
                        ongoingFragmentBinding.ongoingRecyclerView.layoutManager =
                            LinearLayoutManager(activity?.applicationContext)
                    }
                }
            }
        }
    }
}