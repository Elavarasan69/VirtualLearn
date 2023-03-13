package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.databinding.FragmentRegisterPersonalDetailsBinding
import com.robosoft.virtuallearnproject.dataclass.PersonalDetailsRegisterRequestClass
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterPersonalDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRegisterPersonalDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterPersonalDetailsBinding.inflate(inflater, container, false)

        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val mobileNo = sharedPreferences?.getLong("phone_no", 0L)

        val apiService = RestApiService()

        binding.phoneNoTextview.text = mobileNo.toString()
        binding.sendPersonalData.setOnClickListener {

            val fullName = binding.fullNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val userName = binding.userNameEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()

            if(!validatePassword(password)){
                Toast.makeText(binding.root.context, "Follow password rules", Toast.LENGTH_SHORT).show()

            }
            else {
                if (password == confirmPassword) {
                    val registerData = PersonalDetailsRegisterRequestClass(
                        email = email,
                        fullName = fullName,
                        mobileNumber = mobileNo!!,
                        password = password,
                        userName = userName
                    )

                    apiService.register(registerData) {
                        if (it != null) {
                            if (it.accessToken != null) {
                                val editor = sharedPreferences?.edit()

                                editor?.putString("accessToken", it.accessToken)
                                editor?.apply()
                                Log.d("personal Details", it.accessToken.toString())
                                activity?.supportFragmentManager?.beginTransaction()?.replace(
                                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                                    RegistrationSuccessFragment()
                                )?.commit()
                            } else {
                                Toast.makeText(
                                    activity?.applicationContext,
                                    it.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "Passwords doesnot match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //      get the data entered. Create a data class. send the data to the api. Get the result.
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(
//                    com.robosoft.virtuallearnproject.R.id.fragment_container,
//                    RegistrationSuccessFragment()
//                )
//                ?.addToBackStack(null)
//                ?.commit()
        }



        return binding.root
    }

    private fun validatePassword(password: String): Boolean {

        val pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}