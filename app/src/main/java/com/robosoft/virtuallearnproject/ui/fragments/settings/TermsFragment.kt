package com.robosoft.virtuallearnproject.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentTermsBinding


class TermsFragment : Fragment() {

    private lateinit var termsFragmentBinding: FragmentTermsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        termsFragmentBinding = FragmentTermsBinding.inflate(inflater, container, false)

        val backBtn = termsFragmentBinding.backBtn
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, SettingsFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        return termsFragmentBinding.root
    }

}