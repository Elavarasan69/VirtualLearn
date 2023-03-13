package com.robosoft.virtuallearnproject.ui.fragments.moduletest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentTestFailedBinding

class TestFailedFragment : Fragment() {


    private lateinit var testFailedFragment: FragmentTestFailedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        testFailedFragment = FragmentTestFailedBinding.inflate(inflater,container,false)

        testFailedFragment.closeBtn.setOnClickListener {
            activity?.finish()
        }

        testFailedFragment.retryTv.setOnClickListener {
            activity?.finish()
        }

        return testFailedFragment.root
    }
}