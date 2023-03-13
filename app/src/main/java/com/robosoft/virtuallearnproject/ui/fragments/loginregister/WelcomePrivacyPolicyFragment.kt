package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.databinding.FragmentWelcomePrivacyPolicyBinding


class WelcomePrivacyPolicyFragment : Fragment() {
    private lateinit var privacybinding: FragmentWelcomePrivacyPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        privacybinding = FragmentWelcomePrivacyPolicyBinding.inflate(inflater, container, false)
        privacybinding.welcomepolicybackBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    LoginRegisterChoiceFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        return privacybinding.root
    }

}