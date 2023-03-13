package com.robosoft.virtuallearnproject.ui.fragments.mycourse

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.CompletedAdapter
import com.robosoft.virtuallearnproject.adapter.OnGoingAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentCompletedBinding
import com.robosoft.virtuallearnproject.network.mycourse.MyCourseApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompletedFragment : Fragment() {

    private lateinit var completedFragmentBinding: FragmentCompletedBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        completedFragmentBinding = FragmentCompletedBinding.inflate(inflater,container,false)
        completedCourse()
        return completedFragmentBinding.root
    }
    override fun onResume() {
        completedCourse()
        super.onResume()
    }
    private fun completedCourse(){
        val myCourseApiCompleted = MyCourseApiService()
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        GlobalScope.launch {
            myCourseApiCompleted.completed(accessToken!!){
                if (it!=null){
                    Log.d("response",it.toString())
                    completedFragmentBinding.completedRecyclerView.adapter = CompletedAdapter(it)
                    completedFragmentBinding.completedRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
                }
                else {
                    completedFragmentBinding.completedRecyclerView.visibility = View.INVISIBLE
                    completedFragmentBinding.noCourseLayout.visibility = View.VISIBLE
                }
            }
        }
    }
}