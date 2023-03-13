package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentRegistrationBinding
import com.robosoft.virtuallearnproject.dataclass.MobileNumberData
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.fragments.otp.VerifyAccountFragment
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, LoginFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.btnContinue.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login,
                    VerifyAccountFragment()
                )?.addToBackStack(null)
                ?.commit()
            val phoneNo  = binding.editPhoneNo.text.toString()

            if(phoneNo.isNotEmpty() && phoneNo.length == 10 && validatePhoneNumber(phoneNo)){
                val apiService = RestApiService()
                apiService.signup(MobileNumberData(phoneNo.toLong())){
                    Log.d("data", it.toString())
                    if(it != null){
                        if(it.message ==  "Otp sent to number "+ phoneNo){
                            editor?.putLong("phone_no", phoneNo.toLong())
                            editor?.apply()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container_login,
                                    VerifyAccountFragment()
                                )?.addToBackStack(null)
                                ?.commit()
                        }
                        else if(it.message == "You have already registered, Please Sign in"){
                            Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(activity?.applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText(activity?.applicationContext, "Didnt fetch data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(binding.root.context, "Invalid Phone number", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    private fun validatePhoneNumber(phoneNo: String): Boolean {
        val pattern = Pattern.compile("[6-9][0-9]{9}")
        val matcher = pattern.matcher(phoneNo)
        return matcher.matches()
    }

}