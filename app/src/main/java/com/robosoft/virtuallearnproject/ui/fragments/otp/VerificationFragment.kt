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
import com.robosoft.virtuallearnproject.databinding.FragmentVerificationBinding
import com.robosoft.virtuallearnproject.dataclass.MobileNumberData
import com.robosoft.virtuallearnproject.dataclass.VarifyRequestData
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.CreateNewPasswordFragment
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.ForgotPasswordFragment

class VerificationFragment : Fragment() {

    private lateinit var verificationBinding: FragmentVerificationBinding

    lateinit var inputCode: EditText
    lateinit var inputCode2: EditText
    lateinit var inputCode3: EditText
    lateinit var inputCode4: EditText
    lateinit var otpinValid: LinearLayout
    lateinit var otpValid: LinearLayout

    lateinit var verificationButton: Button
    lateinit var otpEntered: String
    val otpReceived = "0000"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        verificationBinding = FragmentVerificationBinding.inflate(inflater, container, false)

        inputCode = verificationBinding.inputCode
        inputCode2 = verificationBinding.inputCode2
        inputCode3 = verificationBinding.inputCode3
        inputCode4 = verificationBinding.inputCode4
        otpValid = verificationBinding.otpValidLine
        otpinValid = verificationBinding.otpInvalidLine

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


        val backBtn = verificationBinding.verifyAccountToolbar.getChildAt(0)
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_login, ForgotPasswordFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        verificationBinding.resendBtn.setOnClickListener {
            val apiService = RestApiService()
            apiService.resend(MobileNumberData(getPhoneNumber())) {
                if (it != null) {
                    Toast.makeText(context?.applicationContext, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        verificationBinding.verifyBtn.setOnClickListener {
            otpEntered =
                inputCode.text.toString() + inputCode2.text.toString() + inputCode3.text.toString() + inputCode4.text.toString()
            otpVerification()
        }

        return verificationBinding.root
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
                        ?.replace(R.id.fragment_container_login, CreateNewPasswordFragment())
                        ?.addToBackStack(null)
                        ?.commit()
                } else {
                    Log.d("Varify response", it.message)
                    otpinValid.visibility = View.VISIBLE
                    customizeSnackBar(it.message)
                }
            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Couldnot fetch data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun customizeSnackBar(message: String) {
        val snackBar =
            Snackbar.make(verificationBinding.constrainLayout, "", Snackbar.LENGTH_LONG)
        val customize: View = layoutInflater.inflate(R.layout.custom_snackbar_invalid_otp, null)

        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0, 0, 0, 0)

        snackBarLayout.addView(customize, 0)
        snackBar.show()
    }


    fun getPhoneNumber(): Long {
        val sharedPreferences =
            context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val number = sharedPreferences?.getLong("phone_no", 0L)
        return number!!
    }

}
