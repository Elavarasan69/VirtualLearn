package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robosoft.virtuallearnproject.databinding.FragmentPasswordChangeSuccessBinding

class PasswordChangeSuccessFragment : Fragment() {
   lateinit var binding : FragmentPasswordChangeSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordChangeSuccessBinding.inflate(inflater,container,false)

        binding.txtLetsGetStarted.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(com.robosoft.virtuallearnproject.R.id.fragment_container_login, LoginFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        return binding.root
        }

}