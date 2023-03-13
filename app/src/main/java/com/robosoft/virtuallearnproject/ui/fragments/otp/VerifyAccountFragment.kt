package com.robosoft.virtuallearnproject.ui.fragments.otp
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentVerifyAccountBinding
import com.robosoft.virtuallearnproject.dataclass.MobileNumberData
import com.robosoft.virtuallearnproject.dataclass.VarifyRequestData
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.RegisterPersonalDetailsFragment
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.RegistrationFragment

class VerifyAccountFragment : Fragment() {

    private lateinit var verifiyAccountBinding: FragmentVerifyAccountBinding

    lateinit var inputCode: EditText
    lateinit var inputCode2: EditText
    lateinit var inputCode3: EditText
    lateinit var inputCode4: EditText
    lateinit var verificationButton: Button
    lateinit var otpEntered: String
    lateinit var otpinValid: LinearLayout
    lateinit var otpValid: LinearLayout
    val otpReceived = "0000"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        verifiyAccountBinding = FragmentVerifyAccountBinding.inflate(inflater, container, false)

        inputCode = verifiyAccountBinding.inputCode
        inputCode2 = verifiyAccountBinding.inputCode2
        inputCode3 = verifiyAccountBinding.inputCode3
        inputCode4 = verifiyAccountBinding.inputCode4
        otpValid = verifiyAccountBinding.otpValidLine
        otpinValid = verifiyAccountBinding.otpInvalidLine

        inputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputCode.text.toString().trim().isNotEmpty()) {
                    inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        inputCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputCode2.text.toString().trim().isNotEmpty()) {
                    inputCode3.requestFocus()
                } else {
                    inputCode.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        inputCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputCode3.text.toString().trim().isNotEmpty()) {
                    inputCode4.requestFocus()
                } else {
                    inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        inputCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputCode4.text.toString().trim().isEmpty()) {
                    inputCode3.requestFocus()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        val backBtn = verifiyAccountBinding.verifyAccountToolbar.getChildAt(0)
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
//            beginTransaction()
//                ?.replace(R.id.fragment_container_login, RegistrationFragment())
//                ?.commit()
        }

        verificationButton = verifiyAccountBinding.verifyBtn
        verificationButton.setOnClickListener {
            otpEntered =
                inputCode.text.toString() + inputCode2.text.toString() + inputCode3.text.toString() + inputCode4.text.toString()
            //otpVerification()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, RegisterPersonalDetailsFragment())?.addToBackStack(null)
                ?.commit()
        }

        verifiyAccountBinding.resendBtn.setOnClickListener {
            resendOtp()
        }

        return verifiyAccountBinding.root
    }

    private fun resendOtp() {
        val apiService = RestApiService()
        val number = getPhoneNumber()
        apiService.resend(MobileNumberData(number)) {
            if (it != null) {
                Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun otpVerification() {
       val apiService = RestApiService()
        Log.d("otp Entered", otpEntered)
        val phoneNum = getPhoneNumber()
        Log.d("phoneNo", phoneNum.toString())
        val requestData = VarifyRequestData(phoneNum, otpEntered)
        apiService.verify(requestData) {
            if (it != null) {
                if (it.message == "true") {
                    Log.d("Varify response", it.toString())
                    otpValid.visibility = View.VISIBLE
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container_login, RegisterPersonalDetailsFragment())?.addToBackStack(null)
                        ?.commit()
                } else {
                    Log.d("Varify response", it.message)
                    otpinValid.visibility = View.VISIBLE
                    customizeSnackBar(it.message)
                }
            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
      }

    }

    private fun customizeSnackBar(message : String) {
        val snackBar =
            Snackbar.make(verifiyAccountBinding.constrainLayout, "", Snackbar.LENGTH_LONG)
        val customize: View = layoutInflater.inflate(R.layout.custom_snackbar_invalid_otp, null)

        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        snackBar.setText(message)
        val snackBarLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0, 0, 0, 0)
        snackBarLayout.addView(customize, 0)
        snackBar.show()
    }

    private fun getPhoneNumber(): Long {
        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val number = sharedPreferences?.getLong("phone_no", 0L)
        return number!!
    }

}