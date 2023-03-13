package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.databinding.FragmentRegistrationSuccessBinding
import com.robosoft.virtuallearnproject.ui.activities.HomeScreenContainerActivity

class RegistrationSuccessFragment : Fragment() {

    lateinit var binding : FragmentRegistrationSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationSuccessBinding.inflate(inflater, container, false)

        binding.txtLetsGetStarted.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            val intent = Intent(activity, HomeScreenContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity?.startActivity(intent)
        }


        return binding.root
         }


}