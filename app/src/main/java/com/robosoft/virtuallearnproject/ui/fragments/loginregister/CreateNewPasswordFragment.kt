package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.databinding.FragmentCreateNewPasswordBinding
import com.robosoft.virtuallearnproject.dataclass.CreateNewPasswordRequest
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService

class CreateNewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    lateinit var bundle: FragmentCreateNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)

        binding.resetPasswordBtn.setOnClickListener {
            val password = binding.passwordEdit.text.toString()
            val rePassword = binding.rePasswordEdit.text.toString()

            if (password == rePassword) {
                val apiService = RestApiService()

                val phoneNumber = getPhoneNumber()
                val data = CreateNewPasswordRequest(phoneNumber,password)
                apiService.createNewPassword(data) {
                    if(it != null){
                        if(it.message == "Your new password is updated. Please log in..."){
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(
                                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                                    PasswordChangeSuccessFragment()
                                )
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                        else {
                            Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(activity?.applicationContext, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Entered Passwords doesnot match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root

        }


    fun getPhoneNumber(): Long {
        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val number = sharedPreferences?.getLong("phone_no", 0L)
        return number!!
    }
}