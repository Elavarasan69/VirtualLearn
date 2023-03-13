package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentForgotPasswordBinding
import com.robosoft.virtuallearnproject.dataclass.MobileNumberData
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.fragments.otp.VerificationFragment


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        //  binding.mobil.visibility = View.INVISIBLE
        //binding.mobileNoEditText.setOnClickListener{
        //    binding.mblNo.visibility = View.VISIBLE
        //}

//        binding.mblNo.visibility = View.INVISIBLE
//        binding.mobileNoEditText.setOnClickListener{
//            binding.mblNo.visibility = View.VISIBLE
//        }


        binding.forgotPasswordBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, LoginFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.btnSend.setOnClickListener {
            val apiService = RestApiService()

            val number = binding.editPhoneNo.text.toString().toLong()
            // editor?.putLong("phone_no", number)
            //editor?.commit()
            if (getPhoneNumber(number)) {
                val data = MobileNumberData(number)

                apiService.forgotPassword(data) {
                    if (it != null) {
                        if (it.message == "Otp sent to number " + number) {

                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container_login, VerificationFragment())
                                ?.addToBackStack(null)
                                ?.commit()
                        } else {
                            Toast.makeText(
                                activity?.applicationContext,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Invalid mobile number", Toast.LENGTH_SHORT
                ).show()
            }

        }
        return binding.root
    }


    private fun getPhoneNumber(number: Long): Boolean
    {
        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val mobileNo = sharedPreferences?.getLong("phone_no", 0L)
        return true
    }
}

