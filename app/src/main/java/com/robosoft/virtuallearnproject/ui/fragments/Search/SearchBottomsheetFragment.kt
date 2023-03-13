package com.robosoft.virtuallearnproject.ui.fragments.Search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentSearchBottomsheetBinding
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment


class SearchBottomsheetFragment : Fragment() {
    private lateinit var searchbottomsheetbinding : FragmentSearchBottomsheetBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchbottomsheetbinding = FragmentSearchBottomsheetBinding.inflate(inflater, container, false)


//        searchbottomsheetbinding.designLayout?.setOnClickListener{
//            searchbottomsheetbinding.designLayout!!.setBackgroundColor(R.color.yellow)
//        }

        return searchbottomsheetbinding.root
    }

}