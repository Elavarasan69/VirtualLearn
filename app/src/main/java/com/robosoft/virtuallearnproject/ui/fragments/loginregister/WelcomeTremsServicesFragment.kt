package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.databinding.FragmentWelcomeTremsServicesBinding


class WelcomeTremsServicesFragment : Fragment() {
    private lateinit var termsbinding: FragmentWelcomeTremsServicesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        termsbinding = FragmentWelcomeTremsServicesBinding.inflate(inflater, container, false)
        termsbinding.welcometermsbackBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    LoginRegisterChoiceFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        return termsbinding.root

    }
}