package com.robosoft.virtuallearnproject.ui.fragments.moduletest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.databinding.FragmentTestCompletedBinding
import com.robosoft.virtuallearnproject.ui.fragments.testresult.ResultFragment

class TestCompletedFragment : Fragment() {

    private lateinit var testCompletedBinding: FragmentTestCompletedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val bundle = arguments
        val testID = bundle?.getString("testID")
        val chapterNo = bundle?.getString("chapterNo")
        val chapterTitle = bundle?.getString("chapterTitle")
        val courseTitle = bundle?.getString("courseTitle")
        testCompletedBinding= FragmentTestCompletedBinding.inflate(inflater, container, false)
        testCompletedBinding.messageTv.text =
            "You have completed Chapter $chapterNo - $chapterTitle $courseTitle"
        testCompletedBinding.closeBtn.setOnClickListener {
            activity?.finish()
        }
        testCompletedBinding.resultTv.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("testID", testID)
            bundle.putString("chapterNo", chapterNo)
            bundle.putString("chapterTitle", chapterTitle)
            bundle.putString("courseTitle",courseTitle)
            val result = ResultFragment()
            result.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                com.robosoft.virtuallearnproject.R.id.fragment_container_video,
                result
            )?.addToBackStack(null)
                ?.commit()
        }
        return testCompletedBinding. root
    }

    override fun onResume() {
        super.onResume()
        this.testCompletedBinding.root
    }
}