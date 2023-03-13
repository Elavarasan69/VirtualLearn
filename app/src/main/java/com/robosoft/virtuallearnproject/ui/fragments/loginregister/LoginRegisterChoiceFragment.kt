package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentLoginRegisterChoiceBinding


class LoginRegisterChoiceFragment : Fragment() {
    private lateinit var loginRegisterChoiceBinding: FragmentLoginRegisterChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginRegisterChoiceBinding = FragmentLoginRegisterChoiceBinding.inflate(inflater, container, false)

        loginRegisterChoiceBinding.loginBtn.setOnClickListener {
            val loginFragment: Fragment = LoginFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragment_container_login,
                loginFragment
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        loginRegisterChoiceBinding.registerBtn.setOnClickListener {
            val registerFragment = RegistrationFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragment_container_login,
                    registerFragment
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        loginRegisterChoiceBinding.termsservicesTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    WelcomeTremsServicesFragment()
                )
                ?.addToBackStack(null)?.commit()
        }

        loginRegisterChoiceBinding.privacypolicyTv.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    WelcomePrivacyPolicyFragment()
                )
                ?.addToBackStack(null)?.commit()
        }
        return loginRegisterChoiceBinding.root
    }

}


