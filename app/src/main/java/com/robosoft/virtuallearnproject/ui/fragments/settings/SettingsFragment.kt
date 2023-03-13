package com.robosoft.virtuallearnproject.ui.fragments.settings

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentSettingsBinding
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment


class SettingsFragment : Fragment() {

    private lateinit var settingFragmentBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        settingFragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        var flag = 0

        val backBtn = settingFragmentBinding.backBtn
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//            beginTransaction()
//                ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
//                ?.addToBackStack(null)
//                ?.commit()
        }
        settingFragmentBinding.root.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.supportFragmentManager?.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                true
            } else {
                false
            }
        }

        settingFragmentBinding.expandable.parentLayout.setOnClickListener {
            if (flag == 0) {
                flag = 1
                settingFragmentBinding.expandable.expand()
            } else {
                flag = 0
                settingFragmentBinding.expandable.collapse()
            }
        }

        settingFragmentBinding.privacyTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, PolicyFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        settingFragmentBinding.termsTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.homescreenfragment_container, TermsFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        return settingFragmentBinding.root
    }

}