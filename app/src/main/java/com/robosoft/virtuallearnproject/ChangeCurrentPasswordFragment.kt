package com.robosoft.virtuallearnproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.robosoft.virtuallearnproject.databinding.FragmentChangeCurrentPasswordBinding
import com.robosoft.virtuallearnproject.databinding.FragmentMyProfileBinding
import com.robosoft.virtuallearnproject.dataclass.UpdatePasswordRequest
import com.robosoft.virtuallearnproject.network.loginregister.RestApiService
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.PasswordChangeSuccessFragment
import com.robosoft.virtuallearnproject.ui.fragments.profile.MyProfileFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ChangeCurrentPasswordFragment : Fragment() {
    private  lateinit var binding : FragmentChangeCurrentPasswordBinding
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeCurrentPasswordBinding.inflate(inflater,container,false)

        binding.changeYourPassIB.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()

//            beginTransaction()
//                ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
//                ?.addToBackStack(null)
//                ?.commit()
        }

        binding.currentPasswordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.currentPasswordEdit.text.isEmpty()) {
                    binding.currentPassword.visibility = View.INVISIBLE
                } else {
                    binding.currentPassword.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.newPasswordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.newPasswordEdit.text.isEmpty()) {
                    binding.newPassword.visibility = View.INVISIBLE
                } else {
                    binding.newPassword.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.confirmPasswordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.confirmPasswordEdit.text.isEmpty()) {
                    binding.passwordConfirm.visibility = View.INVISIBLE
                } else {
                    binding.passwordConfirm.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.apply {
            resetPasswordBtn.setOnClickListener{
                val currentPassword =  currentPasswordEdit.text.toString().trim()
                val newPassword =  newPasswordEdit.text.toString().trim()
                val confrimPassword =  confirmPasswordEdit.text.toString().trim()

                    if(!currentPassword.equals(newPassword)){
                        if(validatePassword(newPassword)){
                            if(newPassword.equals(confrimPassword)){
                                val apiService = RestApiService()
                                val access_token = SharedPreferenceManager(requireContext()).getAccessToken()!!
                                val updatePasswordRequest = UpdatePasswordRequest(currentPassword, newPassword)
                                Log.d("token",access_token)
                                GlobalScope.launch {
                                    apiService.updateNewPassword(
                                        access_token,
                                        updatePasswordRequest
                                    ) {
                                        if (it != null) {
                                            if (it.message == "Password successfully Updated") {
                                                allMatches()
                                                activity?.supportFragmentManager?.beginTransaction()
                                                    ?.replace(
                                                        com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                                                        PasswordChangeSuccessFragment()
                                                    )
                                                    ?.addToBackStack(null)
                                                    ?.commit()
                                            } else {
                                                allWrong()
                                                Toast.makeText(
                                                    activity?.applicationContext,
                                                    it.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            currentNewWrong()
                                            Toast.makeText(
                                                activity?.applicationContext,
                                                "Something Went Wrong",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }else{
                                newConfirmWrong()
                                Toast.makeText(
                                    activity?.applicationContext,
                                    "New Password and Confirm New Password does not match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            allWrong()
                            Toast.makeText(binding.root.context,
                                "Follow password rules", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        currentNewWrong()
                        Toast.makeText(binding.root.context, "Current and New Password cannot be same", Toast.LENGTH_SHORT).show()
                    }


                }
            }

        return binding.root

    }
    private fun validatePassword(password: String): Boolean {

        val pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun allMatches(){

        binding.redLineOldPwd.visibility = View.INVISIBLE
        binding.redLineNewPwd.visibility = View.INVISIBLE
        binding.redLineConfirmPwd.visibility = View.INVISIBLE

        binding.greenLineOldPwd.visibility = View.VISIBLE
        binding.greenLineNewPwd.visibility = View.VISIBLE
        binding.greenLineConfirmPwd.visibility = View.VISIBLE

        binding.redOldPwd.visibility = View.INVISIBLE
        binding.redNewPwd.visibility = View.INVISIBLE
        binding.redConfirmPwd.visibility = View.INVISIBLE

        binding.greenOldPwd.visibility = View.VISIBLE
        binding.greenNewPwd.visibility = View.VISIBLE
        binding.greenConfirmPwd.visibility = View.VISIBLE
    }

    private fun allWrong(){
        binding.redLineOldPwd.visibility = View.VISIBLE
        binding.redLineNewPwd.visibility = View.VISIBLE
        binding.redLineConfirmPwd.visibility = View.VISIBLE

        binding.greenLineOldPwd.visibility = View.INVISIBLE
        binding.greenLineNewPwd.visibility = View.INVISIBLE
        binding.greenLineConfirmPwd.visibility = View.INVISIBLE

        binding.redOldPwd.visibility = View.VISIBLE
        binding.redNewPwd.visibility = View.VISIBLE
        binding.redConfirmPwd.visibility = View.VISIBLE

        binding.greenOldPwd.visibility = View.INVISIBLE
        binding.greenNewPwd.visibility = View.INVISIBLE
        binding.greenConfirmPwd.visibility = View.INVISIBLE
    }

    private fun newConfirmWrong(){
        binding.redLineOldPwd.visibility = View.INVISIBLE
        binding.greenLineNewPwd.visibility = View.INVISIBLE
        binding.greenLineConfirmPwd.visibility = View.INVISIBLE

        binding.greenLineOldPwd.visibility = View.VISIBLE
        binding.redLineNewPwd.visibility = View.VISIBLE
        binding.redLineConfirmPwd.visibility = View.VISIBLE

        binding.redOldPwd.visibility = View.INVISIBLE
        binding.greenNewPwd.visibility = View.INVISIBLE
        binding.greenConfirmPwd.visibility = View.INVISIBLE

        binding.greenOldPwd.visibility = View.VISIBLE
        binding.redNewPwd.visibility = View.VISIBLE
        binding.redConfirmPwd.visibility = View.VISIBLE
    }

    private fun currentNewWrong(){
        binding.greenLineOldPwd.visibility = View.INVISIBLE
        binding.greenLineNewPwd.visibility = View.INVISIBLE

        binding.redLineOldPwd.visibility = View.VISIBLE
        binding.redLineNewPwd.visibility = View.VISIBLE

        binding.greenOldPwd.visibility = View.INVISIBLE
        binding.greenNewPwd.visibility = View.INVISIBLE

        binding.redOldPwd.visibility = View.VISIBLE
        binding.redNewPwd.visibility = View.VISIBLE
    }
}