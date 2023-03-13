package com.robosoft.virtuallearnproject.ui.fragments.testresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.ResultAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentResultBinding
import com.robosoft.virtuallearnproject.dataclass.test.TestBodyData
import com.robosoft.virtuallearnproject.dataclass.test.TestResultBody
import com.robosoft.virtuallearnproject.network.test.TestApiService

class ResultFragment : Fragment() {

    private lateinit var resultFragmentBinding: FragmentResultBinding
    private val testApi = TestApiService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        resultFragmentBinding = FragmentResultBinding.inflate(inflater,container,false)

        val bundle = arguments
        val testID = bundle?.getString("testID")
        val chapterNo = bundle?.getString("chapterNo")
        val chapterTitle = bundle?.getString("chapterTitle")
        val courseTitle = bundle?.getString("courseTitle")

        resultFragmentBinding.chapterTitle.text = "Chapter $chapterNo: $chapterTitle "
        resultFragmentBinding.courseTitle.text = courseTitle

        resultFragmentBinding.closeIcon.setOnClickListener {
            activity?.finish()
        }

        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()!!
        val data = TestResultBody(testID.toString())
        Log.d("testID",testID.toString())
        testApi.testResult(accessToken,data){
            if (it != null) {
                Log.d("test result response", it.toString())
//                displaying part
                resultFragmentBinding.scoreTv.text = it.correctlyAnsweredInHundred.toString()
                resultFragmentBinding.passingGradeTv.text = it.passingGrade
                resultFragmentBinding.correctTv.text = it.correctlyAnswered
                resultFragmentBinding.wrongTv.text = it.wronglyAnswered
                resultFragmentBinding.questionRecyclerView.adapter = ResultAdapter(it)
                resultFragmentBinding.questionRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
            }
            else{
                Log.d("test result response", "Invalid")
            }
        }
        return resultFragmentBinding.root
    }
    override fun onResume() {
        super.onResume()
        this.resultFragmentBinding.root
    }
}