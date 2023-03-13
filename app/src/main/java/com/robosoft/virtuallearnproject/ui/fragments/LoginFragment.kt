package com.robosoft.virtuallearnproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentLoginBinding
import com.robosoft.virtuallearnproject.dataclass.LoginRequestData
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.activities.HomeScreenContainerActivity
import com.robosoft.virtuallearnproject.ui.fragments.categories.CategoriesFragment
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.ForgotPasswordFragment

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater,container,false)




       // binding.usernameTv.visibility = View.INVISIBLE
      //  binding.passTv.visibility = View.INVISIBLE

       // binding.usernameEt.setOnClickListener{
      //      binding.usernameTv.visibility = View.VISIBLE
     //   }

       // binding.passEt.setOnClickListener{
    //        binding.passTv.visibility = View.VISIBLE
    //    }


        binding.welcomebackloginBtn.setOnClickListener {
            val apiService  = RestApiService()
            val data = LoginRequestData(binding.userNameEt.text.toString(),binding.passwordEt.text.toString())

            apiService.login(data){
                if(it == null){
                    Log.d("message", "hello")
                    Toast.makeText(activity?.applicationContext, it?.accessToken.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("message", it.message)
                    Toast.makeText(activity?.applicationContext, it?.accessToken.toString(), Toast.LENGTH_SHORT).show()

                    activity?.startActivity(Intent(activity, HomeScreenContainerActivity::class.java))
                }
            }
        }

        binding.wlcmBackRegisterBtn.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, CategoriesFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.forgotPasswordBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, ForgotPasswordFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        return binding.root
    }

}