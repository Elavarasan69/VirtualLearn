package com.robosoft.virtuallearnproject.ui.fragments.loginregister

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.robosoft.virtuallearnproject.databinding.FragmentLoginBinding
import com.robosoft.virtuallearnproject.dataclass.LoginRequestData
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.activities.HomeScreenContainerActivity
import kotlinx.android.synthetic.main.custom_snackbar_invalid_username.view.*
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.userNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.userNameEt.text.isEmpty()) {
                    binding.userName.visibility = View.INVISIBLE
                } else {
                    binding.userName.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.passwordEt.text.isEmpty()) {
                    binding.password.visibility = View.INVISIBLE
                } else {
                    binding.password.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        binding.welcomebackloginBtn.setOnClickListener {
            binding.welcomebackloginBtn.isEnabled = false
            val apiService = RestApiService()

            val userName = binding.userNameEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (validatePassword(password)) {

                val data = LoginRequestData(userName, password)

                apiService.login(data) {
                    if (it == null) {
                        Log.d("message", "hello")
                        loginFailed()
                        Toast.makeText(
                            activity?.applicationContext,
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.welcomebackloginBtn.isEnabled = true
                        //  binding.welcomebackloginBtn.focusable = View.FOCUSABLE
                    } else {
                        if (it.message == "Login successful") {
                            Log.d("message", it.message)
                            loginSuccess()
                            Toast.makeText(
                                activity?.applicationContext,
                                it.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            binding.welcomebackloginBtn.isEnabled = true
                            binding.passwordEt.text?.clear()
                            binding.userNameEt.text?.clear()

                            val sharedPreferences =
                                activity?.applicationContext?.getSharedPreferences(
                                    "sharedPreference",
                                    Context.MODE_PRIVATE
                                )
                            val editor = sharedPreferences?.edit()
                            editor?.putString("accessToken", it.accessToken.toString())
                            Log.d("accesstoken", it.accessToken)
                            editor?.putString("userName", binding.userNameEt.text.toString())
                            editor?.apply()
                            activity?.startActivity(
                                Intent(
                                    activity,
                                    HomeScreenContainerActivity::class.java
                                )
                            )
                        } else {
                            Log.d("login123", "going to else")
                            invalidPassword()
                            customizeSnackBar(it.message)
                            binding.welcomebackloginBtn.isEnabled = true
                            //   binding.welcomebackloginBtn.focusable = View.FOCUSABLE
                        }
                    }
                }

            } else {
                loginFailed()
                Toast.makeText(
                    binding.root.context,
                    "Enter Username and Password", Toast.LENGTH_SHORT
                ).show()
                binding.welcomebackloginBtn.isEnabled = true
            }
        }

        binding.wlcmBackRegisterBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    RegistrationFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.forgotPasswordBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.fragment_container_login,
                    ForgotPasswordFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        return binding.root
    }

    private fun customizeSnackBar(message: String) {
        val snackBar =
            Snackbar.make(binding.constrainLayout, "", Snackbar.LENGTH_LONG)
        val customize: View = layoutInflater.inflate(
            com.robosoft.virtuallearnproject.R.layout.custom_snackbar_invalid_username,
            null
        )
        customize.invalid_tv.text = message

        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
//        snackBar.setText(message)
        val snackBarLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0, 0, 0, 0)
        snackBarLayout.addView(customize, 0)
        snackBar.show()
    }

    private fun validatePassword(password: String): Boolean {
        val pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun loginSuccess() {
        binding.red.visibility = View.INVISIBLE
        binding.redLine.visibility = View.INVISIBLE

        binding.green.visibility = View.VISIBLE
        binding.greenLine.visibility = View.VISIBLE

        binding.redPwd.visibility = View.INVISIBLE
        binding.redLinePwd.visibility = View.INVISIBLE

        binding.greenPwd.visibility = View.VISIBLE
        binding.greenLinePwd.visibility = View.VISIBLE
    }

    private fun loginFailed() {
        binding.green.visibility = View.INVISIBLE
        binding.greenLine.visibility = View.INVISIBLE

        binding.red.visibility = View.VISIBLE
        binding.redLine.visibility = View.VISIBLE

        binding.greenPwd.visibility = View.INVISIBLE
        binding.greenLinePwd.visibility = View.INVISIBLE

        binding.redPwd.visibility = View.VISIBLE
        binding.redLinePwd.visibility = View.VISIBLE

    }

    private fun invalidPassword() {
        binding.red.visibility = View.INVISIBLE
        binding.redLine.visibility = View.INVISIBLE

        binding.green.visibility = View.VISIBLE
        binding.greenLine.visibility = View.VISIBLE

        binding.greenPwd.visibility = View.INVISIBLE
        binding.greenLinePwd.visibility = View.INVISIBLE

        binding.redPwd.visibility = View.VISIBLE
        binding.redLinePwd.visibility = View.VISIBLE
    }
}